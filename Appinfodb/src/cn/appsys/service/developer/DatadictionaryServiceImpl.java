package cn.appsys.service.developer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.datadictionary.DatadictionaryMapper;
import cn.appsys.pojo.DataDictionary;
@Service("datadictionaryService")
public class DatadictionaryServiceImpl implements DatadictionaryService {
	@Resource
	private DatadictionaryMapper  datadictionaryMapper;
	
	@Override
	public List<DataDictionary> getlistDataDictionary(String typeCode) {
		return datadictionaryMapper.getlistDataDictionary(typeCode);
	}

}
