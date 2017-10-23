package com.dao.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.dao.domain.Linux;

@Repository(value="linuxMapper")
public interface LinuxMapper {
	@Select("select *  from t_linux where id=#{id}")
	public Linux getJsonFile(@Param("id")Integer id);
	
	@Select("select *  from t_linux where hostname=#{hostname}")
	public Linux selectLinuxByHostname(@Param("hostname")String hostname);
	
	@Insert("insert into t_linux(hostname,username,password) values(#{hostname},#{username},#{password})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int saveLinux(Linux linux);
	/**
	 * 查询所有的json
	 * 
	 * @return
	 */
	@Select("select * from t_linux")
	public List<Linux> selectAllLinux();
	/**
	 *@ahthor wang
	 *@date  2017.10.13
	 *@description 根据id删除json文件
	 *
	 */
	@Delete("delete from t_linux where  id=#{id}")
	public int deleteLinux(@Param("id") Integer id);
}
