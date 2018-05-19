package cn.appsys.service.developer;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.appsys.dao.appinfo.AppinfoMapper;
import cn.appsys.pojo.AppInfo;
@Service("appinfoService")
public class AppinfoServiceImpl implements AppinfoService {
	@Resource
	private AppinfoMapper appinfoMapper;
	/**
	 * 查询所有App
	 * @return
	 */
	@Override
	public List<AppInfo> getlistAppinfo(String softwareName,Integer status,
			Integer queryFlatformId,Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,Integer queryCategoryLevel3,
			int pageNo,int pageSize) {
		//softwareName,status,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,pageNo,pageSize
		return appinfoMapper.getlistAppinfo(softwareName,status,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,pageNo,pageSize);
	}
	/**
	 * 查询总页数
	 * @return
	 */
	@Override
	public int getCountAppinfo(String softwareName,Integer status,Integer queryFlatformId,Integer queryCategoryLevel1, Integer queryCategoryLevel2,Integer queryCategoryLevel3) {
		return	appinfoMapper.getCountAppinfo(softwareName,status,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3);
	}
	@Override
	public AppInfo getAppInfo(Integer id) {
		return appinfoMapper.getAppInfo(id);
	}
	/**
	 * 根据ID删除图片
	 * @param id
	 * @return
	 */
	@Override
	public int deleteLogo(Integer id) {
		return appinfoMapper.deleteLogo(id);
	}
	/**
	 * 更新
	 */
	@Override
	public int upadteAppinfo(AppInfo appinfo) {
		
		return appinfoMapper.upadteAppinfo(appinfo);
	}
	/**
	 * 删除app
	 */
	@Override
	public int deleteApp(Integer id) {
		
		return appinfoMapper.deleteApp(id);
	}
	/**
	 * ajax异步验证apkName是否存在
	 */
	@Override
	public int apkexist(String APKName) {
		
		return appinfoMapper.apkexist(APKName);
	}
	/**
	 * 新增app
	 */
	@Override
	public int inserAppInfo(AppInfo appInfo) {
		
		return appinfoMapper.inserAppInfo(appInfo);
	}
	@Override
	public int getByAPKName(String APKName) {
		// TODO Auto-generated method stub
		return appinfoMapper.getByAPKName(APKName);
	}
	@Override
	public int getByIdUpdateVersion(Integer id, Integer Versionid) {
		// TODO Auto-generated method stub
		return appinfoMapper.getByIdUpdateVersion(id, Versionid);
	}
	@Override
	public int getByIdUpdaatestatus(Integer id, Integer status) {
		// TODO Auto-generated method stub
		return appinfoMapper.getByIdUpdaatestatus(id, status);
	}

	
}
