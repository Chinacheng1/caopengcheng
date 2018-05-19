package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppcategoryMapper {
	/**
	 * 查询一、二，三级方法
	 */
	List<AppCategory> getAppCategory(@Param("parentid")Integer parentid);
}
