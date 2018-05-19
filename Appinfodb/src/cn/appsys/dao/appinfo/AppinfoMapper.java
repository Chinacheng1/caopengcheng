package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

public interface AppinfoMapper {
	/**
	 * 查询所有App
	 * @return
	 */
	List<AppInfo> getlistAppinfo(@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("queryFlatformId")Integer queryFlatformId,@Param("queryCategoryLevel1") Integer queryCategoryLevel1,
			@Param("queryCategoryLevel2") Integer queryCategoryLevel2,@Param("queryCategoryLevel3") Integer queryCategoryLevel3,
			@Param("pageNo")int pageNo,@Param("pageSize") int pageSize);
	/**
	 * 查询总页数
	 */
	int getCountAppinfo(@Param("softwareName")String softwareName,@Param("status")Integer status,
			@Param("queryFlatformId")Integer queryFlatformId,@Param("queryCategoryLevel1") Integer queryCategoryLevel1,
			@Param("queryCategoryLevel2") Integer queryCategoryLevel2,@Param("queryCategoryLevel3") Integer queryCategoryLevel3);
	
	AppInfo getAppInfo(@Param("id") Integer id);
	
	
	int deleteLogo(@Param("id") Integer id);
	
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
	int deleteApp(@Param("id")Integer id);
	
	/**
	 * ajax异步验证apkName是否存在
	 */
	int apkexist(@Param("APKName") String APKName);
	
	/**
	 * 增加app
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
	int getByIdUpdateVersion(@Param("id")Integer id,@Param("versionid")Integer Versionid);
	
	/**
	 * 根据id更新审核信息
	 * @return
	 */
	int getByIdUpdaatestatus(@Param("id") Integer id,@Param("status") Integer status);
}
