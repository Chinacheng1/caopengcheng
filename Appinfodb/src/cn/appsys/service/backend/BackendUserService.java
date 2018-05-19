package cn.appsys.service.backend;

import cn.appsys.pojo.BackendUser;

public interface BackendUserService {
	/**
	 * 后台登录方法
	 * @param name
	 * @param pwd
	 * @return BackendUser
	 */
	BackendUser backLogin(String name,String pwd);
}
