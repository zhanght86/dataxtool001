package com.dao.mapping;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.dao.domain.JsonFile;
import com.json.FileJson;

/**
 * 
 * 访问数据库，并封装数据成为一个JsonFile
 * 跟json打交道的数据都必须使用jsonfile对象来进行交互
 * 
 * @author Johnny
 *
 */
//表示被spirng管理的资源
@Repository(value="jsonMapper")
public interface JsonDao {
	/**
	 * 使用注解的方式插入json文件
	 * 
	 */
	@Insert("insert into t_json(filename,data,type) values(#{filename},#{data},#{type})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int saveJson(JsonFile jsonFile);

	
	/**
	 * 根据id进行删除
	 * 
	 */
	@Delete("delete from t_json where  id=#{id}")
	public int deleteJson(@Param("id") Integer id);
	
	
	/**
	 * 更新json数据
	 * 
	 */
	@Update("update t_json set filename=#{filename} ,type=#{type},data=#{data}  where id=#{id}")
	public void updateJson(JsonFile jsonFile);
	/**
	 * 根据id查询json
	 * @param id
	 * @return
	 */
	@Select("select *  from t_json where id=#{id}")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="filename",property="filename"),
		@Result(column="data",property="data"),
		@Result(column="type",property="type")
	})
	public JsonFile selectJsonById(Integer id);
	/**
	 * 查询所有的json
	 * 
	 * @return
	 */
	@Select("select * from t_json")
	public List<JsonFile> selectAllJson();
}
