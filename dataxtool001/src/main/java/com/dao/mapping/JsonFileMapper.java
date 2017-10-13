package com.dao.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.dao.domain.JsonFile;
@Repository(value="jsonFileMapper")
public interface JsonFileMapper {
	@Select("select *  from t_json where id=#{id}")
	public JsonFile getJsonFile(@Param("id")Integer id);

	@Insert("insert into t_json(filename,data,type) values(#{filename},#{data},#{type})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int saveJson(JsonFile jsonFile);
	/**
	 * 查询所有的json
	 * 
	 * @return
	 */
	@Select("select * from t_json")
	public List<JsonFile> selectAllJson();
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据id删除json文件
	 *
	 */
	@Delete("delete from t_json where  id=#{id}")
	public int deleteJson(@Param("id") Integer id);

}
