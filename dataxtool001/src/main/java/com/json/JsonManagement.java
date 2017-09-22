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

import javax.json.Json;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class JsonManagement {
	public JSONObject translateStringToJSONObiect(String jsonOfString) {
		JSONObject jsonObject=null;
		jsonObject=JSONObject.fromObject(jsonOfString);
		return jsonObject;
	}
	public String parseJsonFileToString(String uri) {
		if(uri==null||"".equals(uri)) {//浣跨敤榛樿鐨勮矾寰�
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO寮傚父");
			e.printStackTrace();
		}
		return json.toString();
	}
	//解析一个文件
	public JSONObject parseJsonFileToJsonObject(String uri) {
		if(uri==null||"".equals(uri)) {//浣跨敤榛樿鐨勮矾寰�
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
	public List<JSONObject> parseJsonFileToJsonObjects(String url){
		List<JSONObject> jsons=new LinkedList<JSONObject>();
		List<String> names=findAllFileNameFromUrl(url);
		for(int i=0;i<names.size();i++) {
			String qualifiedName=url+names.get(i);
			JSONObject jsonObject=parseJsonFileToJsonObject(qualifiedName);
			JSONObject newJson=new JSONObject();
			newJson.put("filename", names.get(i));
			newJson.put("data", jsonObject);
			jsons.add(newJson);
			
		}
		return jsons;
		
	}
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
	
	public JSONObject createNewJsonObject() {
		JSONObject json=new JSONObject();
		return json;
	}
	public void addJsonObject(String key,String value,JSONObject json) {
		json.put(key, value);
	}
	public Object stringToJavaBean(String s) {
		return null;
	}
	
	public JSONArray listToJsonArray(List l) {
		JSONArray json=JSONArray.fromObject(l);
		return json;
	}
	
	public JSONObject mapToJsonObject(Map map) {
		JSONObject json=JSONObject.fromObject(map);
		return json;	
	}
	public JSONArray mapToJsonArray(Map map) {
		JSONArray json=JSONArray.fromObject(map);
		return json;
		
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
	 * 在遍历的过程中便进行修改
	 * value可以有三种对象String，josnArray，jsonObject
	 * 
	 */
	public void updateJSONByKey(String key,Object value,JSONObject obj) {
		Context c=new Context();
		anzlize(key,c,obj);
		anzlizeAndUpdate(key,value,obj);
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
    
    //鏍煎紡鍖栬緭鍑簀son
    public static void showJson(Object json) {
    	String s=json.toString();
    	System.out.println(JsonManagement.formatJson(s));
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



	
	

 
    
}
