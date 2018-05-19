package cn.appsys.dao.backend;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface BackendUserMapper {
	/**
	 * 后台登录方法
	 * @param name
	 * @param pwd
	 * @return BackendUser
	 */
	BackendUser backLogin(@Param("name")String name,@Param("pwd")String pwd);
}
