package cn.appsys.service.developer;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DatadictionaryService {
	List<DataDictionary> getlistDataDictionary(String typeCode);
}
