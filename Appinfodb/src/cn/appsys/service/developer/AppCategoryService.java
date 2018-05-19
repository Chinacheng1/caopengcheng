package cn.appsys.service.developer;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	/**
	 * 查询一、二，三级方法
	 */
	List<AppCategory> getAppCategory(Integer parentid);
}
