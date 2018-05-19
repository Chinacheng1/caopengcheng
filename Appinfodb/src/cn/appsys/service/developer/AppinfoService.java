package cn.appsys.service.developer;

import java.util.List;
import cn.appsys.pojo.AppInfo;

public interface AppinfoService {
	/**
	 * 分页查询所有App
	 * @return
	 */
	List<AppInfo> getlistAppinfo(String softwareName,Integer status,
			Integer queryFlatformId,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,
			int pageNo,int pageSize);
	/**
	 * 查询总页数
	 * @return
	 */
	int getCountAppinfo(String softwareName,Integer status,Integer queryFlatformId,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,Integer queryCategoryLevel3);
	/**
	 * 根据id查询App信息
	 * @param id
	 * @return
	 */
	AppInfo getAppInfo(Integer id);
	/**
	 * 根据ID删除图片
	 * @param id
	 * @return
	 */
	int deleteLogo(Integer id);
	
	/**
	 * 更新
	 * @param appinfo
	 * @return
	 */
	int upadteAppinfo(AppInfo appinfo);

	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	int deleteApp(Integer id);

	/**
	 * ajax异步验证apkName是否存在
	 */
	int apkexist(String APKName);
	
	/**
	 * 新增app
	 * @param appInfo
	 * @return
	 */
	int inserAppInfo(AppInfo appInfo);
	
	/**
	 * 根据APKName查询AppId
	 * @param APKName
	 * @return
	 */
	int getByAPKName(String APKName);
	
	/**
	 * 根据id新增版本
	 * @param id
	 * @return
	 */
	int getByIdUpdateVersion(Integer id,Integer Versionid);
	
	/**
	 * 根据id更新审核信息
	 * @return
	 */
	int getByIdUpdaatestatus(Integer id, Integer status);
}
