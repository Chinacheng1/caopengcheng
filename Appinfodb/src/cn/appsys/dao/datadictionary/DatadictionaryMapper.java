package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DatadictionaryMapper {
	List<DataDictionary> getlistDataDictionary(@Param("typeCode")String typeCode);
}
