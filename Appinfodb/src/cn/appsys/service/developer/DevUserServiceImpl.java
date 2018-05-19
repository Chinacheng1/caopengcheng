package cn.appsys.service.developer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService{
	@Resource
	private DevUserMapper devUserMapper;
	/**
	 * 登录方法
	 */
	@Override
	public DevUser login(String userCode, String pwd) {
		DevUser devUser = devUserMapper.login(userCode);
		if(devUser != null) {
			if(!devUser.getDevPassword().equals(pwd)) {
				devUser = null;
			}
		}
		return devUser;
	}
}
