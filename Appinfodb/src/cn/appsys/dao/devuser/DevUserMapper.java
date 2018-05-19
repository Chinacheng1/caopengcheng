package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
	/**
	 * 前台登录方法
	 * @param name
	 * @return
	 */
	DevUser login(String name);
}
