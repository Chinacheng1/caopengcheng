package cn.appsys.service.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backend.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {
	@Resource
	private BackendUserMapper backendUserMapper;
	@Override
	public BackendUser backLogin(String name, String pwd) {
		// TODO Auto-generated method stub
		return backendUserMapper.backLogin(name, pwd);
	}
		
}
