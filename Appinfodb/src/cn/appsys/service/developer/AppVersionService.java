package cn.appsys.service.developer;

import java.util.List;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	/***
	 * 根据app id 查询历史版本
	 * @param appId
	 * @return
	 */
	List<AppVersion> getAppVersionlist(Integer appId);
	
	/**
	 * 根据id查询版本
	 * @param appId
	 * @return
	 */
	AppVersion getAppVersion(Integer appId);
	
	/**
	 * 根据id情空路径
	 * @param id
	 * @return
	 */
	int deleteByid(Integer id);
	
	/**
	 * 更新appVersion
	 * @param appVersion
	 * @return
	 */
	int updateByidAppVersion(AppVersion appVersion);
	
	/**
	 * 新增版本
	 * @param appVersion
	 * @return
	 */
	int AppVersionInsert(AppVersion appVersion);
	
	/**
	 * 根据appid 查询最新版本
	 * 
	 */
	AppVersion getByAppidAppVersion( Integer appId);
}
