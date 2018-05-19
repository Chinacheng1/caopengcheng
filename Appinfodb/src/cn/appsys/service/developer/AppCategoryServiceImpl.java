package cn.appsys.service.developer;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.appsys.dao.appcategory.AppcategoryMapper;
import cn.appsys.pojo.AppCategory;
@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {
	@Resource
	private AppcategoryMapper appcategoryMapper;
	/**
	 * 查询一、二，三级方法
	 */
	@Override
	public List<AppCategory> getAppCategory(Integer parentid) {
		return appcategoryMapper.getAppCategory(parentid);
	}
}
