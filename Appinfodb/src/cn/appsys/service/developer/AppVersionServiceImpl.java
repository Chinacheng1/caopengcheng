package cn.appsys.service.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public List<AppVersion> getAppVersionlist(Integer appId) {
		
		return appVersionMapper.getAppVersionlist(appId);
	}

	@Override
	public AppVersion getAppVersion(Integer appId) {
		
		return appVersionMapper.getAppVersion(appId);
	}

	@Override
	public int deleteByid(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.deleteByid(id);
	}

	@Override
	public int updateByidAppVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appVersionMapper.updateByidAppVersion(appVersion);
	}

	@Override
	public int AppVersionInsert(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appVersionMapper.AppVersionInsert(appVersion);
	}

	@Override
	public AppVersion getByAppidAppVersion(Integer appId) {
		// TODO Auto-generated method stub
		return appVersionMapper.getByAppidAppVersion(appId);
	}
	
}
