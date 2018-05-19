package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	/**
	 * 登录方法
	 * @param userCode
	 * @param pwd
	 * @return
	 */
	DevUser login(String userCode,String pwd);
}
