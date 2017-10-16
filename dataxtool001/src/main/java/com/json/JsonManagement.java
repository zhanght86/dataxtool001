package com.json;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.domain.JsonFile;
import com.dao.mapping.JsonFileMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 该对象是用来处理json，对于json的增删改查
 * 同时可以保存json格式对象到文件中
 * 文件的格式有规范的，不是原生的datax的json格式，而是自己定义的，如下
 * 文件名必须在保存的时候就需要给定，如果没有，默认为""
 * {
 * 	filename:   指定文件的文件名
 * 	description:  文件的描述
 * 	data:  文件的数据
 * }
 * 
 * 
 * @author Johnny
 *
 */
@Service
public class JsonManagement {
	@Resource(name="jsonFileMapper")
	private JsonFileMapper jsonFileMapper;
	//解析一个文件
	public JSONObject parseJsonFileToJsonObject(String uri) {
		if(uri==null||"".equals(uri)) {
			uri="d://job.json";
		}
		File f=new File(uri);
		StringBuffer json=new StringBuffer();
		try {
			InputStream in=new FileInputStream(f);
			BufferedReader read=new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line="";
			while((line=read.readLine())!=null) {
				json.append(line);
				System.out.println(line);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO寮傚父");
			e.printStackTrace();
		}
		return  JSONObject.fromObject(json.toString());
		
	}
	
	//从指定的url中加载所有的json,url为目录
/*	public List<JSONObject> parseJsonFileToJsonObjects(String url){
		List<JSONObject> jsons=new LinkedList<JSONObject>();
		List<String> names=findAllFileNameFromUrl(url);
		for(int i=0;i<names.size();i++) {
			String qualifiedName=url+names.get(i);
			JSONObject jsonObject=parseJsonFileToJsonObject(qualifiedName);
			jsons.add(jsonObject);
			
		}
		return jsons;
		
	}*/
	//得到目录下面的所有文件的名字，忽略子目录，只得到文件的名字
	public List<String> findAllFileNameFromUrl(String  url){
		List<String> names=new LinkedList<String>();
		//得到所有的文件名
		File file=new File(url);//目录对象
		if(!file.exists()) {
			System.out.println("该目录不存在，无法加载文件");
			return names;
		}else {
			File[] files=file.listFiles();
			for(int i=0;i<files.length;i++) {
				File f=files[i];
				if(!f.isDirectory()) { //判断是文件还是目录
					names.add(f.getName());
				}
			}
		}
		return names;
	}
	/**
	 *	找到指定名字的节点 
	 * 	第四个参数用来判断是要知道该节点，还是该节点的父亲节点
	 */
	public Object findJSONByKey(String key,Object obj,boolean isParent) {
		Context c=new Context();
		anzlize(key,c,obj);
		if(isParent) {
			return c.getJoParent();
		}
		return c.getJo();
	}

	/**
	 * 
	 * 
	 * 遍历并且修改
	 * @param key
	 * @param object
	 */
	public void anzlizeAndUpdate(String key, Object value,Object o) {
		if(o instanceof JSONObject) { 
			//当前json对象
			JSONObject jsonObj=(JSONObject) o;
			//将json对象的key遍历
			Iterator it=jsonObj.keys();
			while(it.hasNext()) {
				//每次遍历得到的是属性的名字
				String subKey=it.next().toString();
				if(key.equals(subKey)) {//找到对指定的key
					jsonObj.put(key, value);
					
				}else {//没有找到，遍历子对象
					anzlizeAndUpdate(key,value,jsonObj.get(subKey));
				}
			}
			
		}else if(o instanceof JSONArray) {
			JSONArray arr=(JSONArray) o;
			
			for(int i=0;i<arr.size();i++) {
				anzlizeAndUpdate(key,value,arr.get(i));
			}
		}else { //涓�鑸殑鍊� do nothing
			
		}
		
	}
	/**
	 * 
	 * 
	 * @param key 需要查找的键值对
	 * @param c 该参数是遍历时候的环境对象
	 * @param o 需要查找的json对象
	 * 
	 * 这里的查找会返回三种对象
	 * 	String对象
	 *  object对象
	 *  array对象
	 */
	private void anzlize(String key, Context c,Object o) {
		if(o instanceof JSONObject) { 
			//当前json对象
			JSONObject jsonObj=(JSONObject) o;
			//将json对象的key遍历
			Iterator it=jsonObj.keys();
			while(it.hasNext()) {
				//每次遍历得到的是属性的名字
				String subKey=it.next().toString();
				if(key.equals(subKey)) {//找到对指定的key
					//这里得到的对象可能是string，array,或者是object
					Object subObject=jsonObj.get(subKey);
					
					//这里一并放入找到节点的父节点
					c.setJoParent(o);
					c.setJo(subObject);
				}else {//没有找到，遍历子对象
					anzlize(key, c,jsonObj.get(subKey) );
				}
			}
			
		}else if(o instanceof JSONArray) {
			JSONArray arr=(JSONArray) o;
			
			for(int i=0;i<arr.size();i++) {
				anzlize(key, c,arr.get(i));
			}
		}else { //涓�鑸殑鍊� do nothing
			
		}
		
	}
	
	
	
	
	
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);

           //閬囧埌{ [鎹㈣锛屼笖涓嬩竴琛岀缉杩�
           switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;

             //閬囧埌} ]鎹㈣锛屽綋鍓嶈缂╄繘
               case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;

              //閬囧埌,鎹㈣
               case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
 
        return sb.toString();
    }
    /**
     * 娣诲姞space
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
    public static void StringToFile(String json,String url) {
    	  // TODO Auto-generated method stub
        File f=new File(url);//
        if(!f.exists()) {
        	try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        FileWriter fw;
        try {
        	fw=new FileWriter(f);//鏂板缓涓�涓狥ileWriter
        	//String str="chenliang\r\nrrrrrrttttttt";
        	fw.write(json);//灏嗗瓧绗︿覆鍐欏叆鍒版寚瀹氱殑璺緞涓嬬殑鏂囦欢涓�
        	fw.close();
  
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
    }
    
    public static void JSONToFile(JSONObject json,String url) {
    	String jsonString=json.toString();
    	StringToFile(jsonString, url);
    }
    /**
     * 
     * 将json格式的数据转化为表格
     * 这里需要注意的是如果是column则会进行特殊处理
     * @return
     */
	public JSONObject translateJsonObjToTable(Object json) {
		JSONObject table=null;
		Context context=new Context();
		//遍历json找出各种键值对
		anazlizeTable(context, json);
		//前面的遍历忽略了column，这里要对cloumn处理
		JSONArray column=(JSONArray) findJSONByKey("column", json, false);
		if(column!=null) {
			processColumn(context,column);
			
		}
		table=JSONObject.fromObject(context.getTables());
		return table;
	}
	/**
	 * 
	 * 专门用来处理column
	 * @param context 
	 * @param column
	 */
	private void processColumn(Context context, JSONArray column) {
		
		for(int i=0;i<column.size();i++) {
			//得到的子元素任然为一个Json对象
			JSONObject c=column.getJSONObject(i);
			String value=(String) c.get("value");
			String type=(String) c.get("type");
			String vaulename="value"+i;
			String typename="type"+i;
			context.getTables().put(vaulename, value);
			context.getTables().put(typename, type);
		}
		
	}
	/**
	 * 
	 * 遍历并生成一张表格
	 * @param c
	 * @param o
	 */
	private void anazlizeTable(Context c,Object o) {
		if(o instanceof JSONObject) { 
			//当前json对象
			JSONObject jsonObj=(JSONObject) o;
			//将json对象的key遍历
			Iterator it=jsonObj.keys();
			while(it.hasNext()) {
				//每次遍历得到的是属性的名字
				String subKey=it.next().toString();
				if(isString(jsonObj,subKey)) { //是字符
					if(subKey.equals("type")||subKey.equals("value")) {
						//do nothing
					}else {
						
						c.getTables().put(subKey, jsonObj.getString(subKey));
					}
				}else {
					anazlizeTable( c,jsonObj.get(subKey) );
				}
				
			

			}
			
		}else if(o instanceof JSONArray) {
			JSONArray arr=(JSONArray) o;
			for(int i=0;i<arr.size();i++) {
				anazlizeTable( c,arr.get(i));
			}
		}else { //为字符串的时候
	
			
			
		}
		
	}
	//判断该json对象的属性是否是key-value的样子
	private boolean isString(JSONObject jsonObj, String subKey) {
		Object subJson=jsonObj.get(subKey);
		if(subJson instanceof JSONObject){
			return false;
		}else if(subJson instanceof JSONArray) {
			return false;
		}
		return true;
	
	}
	/**
	 * 
	 * 遍历的过程中删除指定的key-value
	 *  
	 * @param reader
	 * @param key
	 */
	public void anzlizeAndDelete(String key,Object o) {
		if(o instanceof JSONObject) { 
			//当前json对象
			JSONObject jsonObj=(JSONObject) o;
			//将json对象的key遍历
			Iterator it=jsonObj.keys();
			while(it.hasNext()) {
				//每次遍历得到的是属性的名字
				String subKey=it.next().toString();
				if(key.equals(subKey)) {//找到对指定的key
					jsonObj.remove(subKey);
					return ;
				}else {//没有找到，遍历子对象
					anzlizeAndDelete(key, jsonObj.get(subKey));
				}
			}
			
		}else if(o instanceof JSONArray) {
			JSONArray arr=(JSONArray) o;
			
			for(int i=0;i<arr.size();i++) {
				anzlizeAndDelete(key,arr.get(i));
			}
		}else { //涓�鑸殑鍊� do nothing
			
		}
		
	}
	
	
    /** 
     *  根据路径删除指定的目录或文件，无论存在与否 
     *@param sPath  要删除的目录或文件 
     *@return 删除成功返回 true，否则返回 false。 
     */  
    public boolean DeleteFolder(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) {  // 不存在返回 false  
            return flag;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) {  // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else {  // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
            }  
        }  
    } 
    
    /** 
     * 删除单个文件 
     * @param   sPath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
    public boolean deleteFile(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) { 
        	System.out.println( file.delete());
           
            flag = true;  
            
        }  
        return flag;  
    }  
    
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
    /**
     * 
     * 
     * @param url 指定文件的路径
     * @param filename 指定文件名，必须带有后缀
     * @param data 文件的json格式的数据
     * @param description 对文件的描述
     */
	public void saveFile(String url,String filename,String data,String description ) {
		//封装数据
		FileJson fileJson=new FileJson();
		fileJson.setFilename(filename);
		fileJson.setDescription(description);
		fileJson.setData(data);
		String qualitifyurl=url+filename;//全路径名
		//将对象转化为json格式
		JSONObject jsonObject=JSONObject.fromObject(fileJson);
		StringToFile(jsonObject.toString(), qualitifyurl);
	} 
	/**
	 * 
	 * 重载的方法
	 * @param url  目录的名字
	 * @param fileJson
	 */
	public boolean saveFile(String url,FileJson fileJson) {
		String filename=fileJson.getFilename();
		String qualitifyurl=url+fileJson.getFilename();//全路径名
		//将对象转化为json格式
		JSONObject jsonObject=JSONObject.fromObject(fileJson);
		//判断不能重名
		List<String> names=findAllFileNameFromUrl(url);
		for(int i=0;i<names.size();i++) {
			String name=names.get(i);
			if(filename.equals("")||filename.equals(name)) {
				System.out.println("文件重名或者为空");
				return false;
			}
		}
		StringToFile(jsonObject.toString(), qualitifyurl);
		return true;
	}
	
	/**
	 * 
	 * 从指定的路径中查找所有的文件
	 * @param url
	 * @return
	 */
	public List<JSONObject> findAllFileByUrl(String url) {
		//根据文件名得到所有json格式的对象
		//List<JSONObject> jsonObjects=parseJsonFileToJsonObjects(url);
		return null;
		
	}
	
	/**
	 * 
	 * 从指定的路径中查找所有的文件
	 * @param url
	 * @return
	 */
	public List<JSONObject> findAllFileByUrl_001(String url) {
		//根据文件名得到所有json格式的对象
		List<JSONObject> jsonObjects=parseJsonFileToJsonObjects_001(url);
		return jsonObjects;
		
	}
	
	
	private List<JSONObject> parseJsonFileToJsonObjects_001(String url) {
		List<JSONObject> jsons=new LinkedList<JSONObject>();
		List<String> names=findAllFileNameFromUrl(url);
		//processNames(names);//处理掉.json后缀
		for(int i=0;i<names.size();i++) {
			String qualifiedName=url+names.get(i);
			JSONObject jsonObject=parseJsonFileToJsonObject(qualifiedName);
			jsons.add(jsonObject);
			
		}
		return jsons;
	}
	/**
	 * 
	 * 将两个文件拼接起来
	 * @param newFilename 新的文件的名字
	 * @param type   新的文件的类型(jsonobj还是jsonarray)
	 * @param names  要拼接的子文件的名字
	 */
	public void connection(String newFilename, String type, List<String> names) {
		if("jsonarray".equals(type)) {
			JSONArray jsonArray=new JSONArray();
			for(int i=0;i<names.size();i++) {
				JSONObject file=findFileByName("d://json/",names.get(i));
				if(file!=null) {
					jsonArray.add(file.get("data"));
				}
			}
			FileJson fileJson=new FileJson();
			fileJson.setFilename(newFilename);
			fileJson.setData(jsonArray.toString());
			fileJson.setDescription("");
			saveFile("d://json/", fileJson);
		}else if("jsonobj".equals(type)) {//组装为一个对象
			JSONObject jsonObject=new JSONObject();
			for(int i=0;i<names.size();i++) {
				JSONObject file=findFileByName("d://json/",names.get(i));
				if(file!=null) {
					
					String name=names.get(i);
					name=name.substring(0, name.indexOf("."));
					jsonObject.put(name, file.get("data"));
					
				}
			}
			FileJson fileJson=new FileJson();
			fileJson.setFilename(newFilename);
			fileJson.setData(jsonObject.toString());
			fileJson.setDescription("");
			saveFile("d://json/", fileJson);
		}
		
	}
	/**
	 * 
	 * 根据文件名查找文件
	 * @param url 文件的目录
	 * @param name 文件名
	 * @return  如果没有找到则为null，返回的为一个json格式的，预定义的格式的文件对象
	 */
	public JSONObject findFileByName(String url,String name) {	
		List<JSONObject> jsonObjects=findAllFileByUrl_001(url);
		JSONObject result=null;
		for(int i=0;i<jsonObjects.size();i++) {
			JSONObject jsonObject=jsonObjects.get(i);
			if(name.equals(jsonObject.getString("filename"))) {
				result=jsonObject;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 因为是标存储的是标准的格式，所以要先进行转化再展示
	 * @param url 文件的目录
	 * @param filename 文件名
	 */
	public void showfile(String url,String filename) {
		JSONObject file=findFileByName(url, filename);
		if(file!=null) {
			String data=file.getString("data");
			System.out.println(JsonManagement.formatJson(data));
		}
	}
	

	/**
	 * 获得文件的数据
	 * @param url 文件的目录
	 * @param filename 文件名
	 * @return
	 */
	public String getData(String url,String filename) {
		JSONObject file=findFileByName(url, filename);
		String data=file.getString("data");
		return data;
	}
	/**
	 * 
	 * 将指定文件的数据保存为另外一个文件
	 * 用来将文件转化为datax需要的json
	 * @param fileurl 指定的文件路径
	 * @param filename  文件的名字
	 * @param dataurl   要保存的数据的路径
	 * @param datafilename  数据文件的名字
	 */
	public void saveFileData(String fileurl,String filename,String dataurl,String datafilename) {
		String data=getData(fileurl, filename);
		StringToFile(data, dataurl+datafilename);
	}
	/**
	 * 从指定的路径显示json对象
	 * 
	 */
	public void showJson(String url,String filename) {
		findFileByName(url, filename);
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.12
	 *@description
	 *	该方法是用来保存一个json类型文件的
	 *	保存结果会返回success或者是fail
	 * 
	 */
	public String save(String filename, String data, String type) {
		JsonFile jsonFile=new JsonFile();
		jsonFile.setFilename(filename);
		jsonFile.setData(data);
		jsonFile.setType(type);
		jsonFileMapper.saveJson(jsonFile);
		return "success";
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 获得数据库中所有的json
 		返回的json对象的格式
	 		{ 
			"name":"streamreader",
			"filename":"reader1",
			"type":"reader",
			data:""
			}
	 *
	 */
	public List<JSONObject> findAllJsonFiles() {
		List<JsonFile> jsonFiles=jsonFileMapper.selectAllJson();
		List<JSONObject> jsonObjects=new LinkedList<JSONObject>();
		for(int i=0;i<jsonFiles.size();i++) {
			JsonFile jsonFile=jsonFiles.get(i);
			JSONObject jsonObject=JSONObject.fromObject(jsonFile);
			jsonObjects.add(jsonObject);
		}
		return jsonObjects;
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 得到的是所有的原生的jsonFile，没有被处理
	 *
	 */
	public List<JsonFile> findAllPrimaryJsonFiles(){
		List<JsonFile> jsonFiles=jsonFileMapper.selectAllJson();
		return jsonFiles;
	}
	
	
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据jsonfile的id来删除
	 *
	 */
	public void deleteJsonFileById(int id) {
		jsonFileMapper.deleteJson(id);
	}
	/**
	 *@ahthor wang
	 *@date  2017.10.16
	 *@description 获得指定类型的jsonfile
	 *
	 */
	public List<JsonFile> findJsonFilesByType(String type){
		List<JsonFile> jsonFiles=jsonFileMapper.getJsonFilesByType(type);
		return jsonFiles;
	}

	public void save(JsonFile jsonFile) {
		this.save(jsonFile.getFilename(), jsonFile.getData(), jsonFile.getData());
		
	}
	
}
