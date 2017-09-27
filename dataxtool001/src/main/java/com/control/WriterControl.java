package com.control;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dataxmanagement.DataxManagement;
import net.sf.json.JSONObject;
@Controller
public class WriterControl {
	@Autowired
	private DataxManagement dataxManagement;
	/**
	 * 
	 * addStreamReader
	 * @return
	 */
	@RequestMapping("/datax/job/writer/addstreamreader.do")
	public @ResponseBody String addWriter(@RequestParam("reader") String reader) {
		JSONObject writerJson=JSONObject.fromObject(reader);
		//保存一个json对象
		dataxManagement.addWriter(writerJson);
		return "";
	}
	/**
	 * 前台用来加载数据
	 * 
	 * 
	 */
	@RequestMapping("/datax/job/writer/findallreader.do")
	public @ResponseBody String findAllWriters(HttpServletRequest request) {
		//第几页
		int page=Integer.parseInt(request.getParameter("page"));
		//一页多少数据
		int rows=Integer.parseInt(request.getParameter("rows"));
		JSONObject result=dataxManagement.findAllWriter(page,rows);
		return result.toString();
	}
	
	/**
	 * 前台根据文件名字取得前台格式的jsonwriter
	 * 
	 */
	@RequestMapping("/datax/job/writer/findwriterbyfilename.do")
	public @ResponseBody String findReaderByFilename(HttpServletRequest request) {
		String filename= request.getParameter("filename");
		JSONObject jsonObject=dataxManagement.findWriterByFilename(filename);
		return jsonObject.getJSONArray("rows").toString();
	}

	@RequestMapping("/datax/job/writer/deletefilebyfilename.do")
	public @ResponseBody String deleteReaderByFilename(HttpServletRequest request) {
		String filename=request.getParameter("filename");
		dataxManagement.deleteWriterFileByFilename(filename);
		return "";
	}
}
