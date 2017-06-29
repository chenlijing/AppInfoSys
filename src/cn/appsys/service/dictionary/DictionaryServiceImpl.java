package cn.appsys.service.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.dictionary.DictionaryMapper;
import cn.appsys.pojo.Dictionary;
@Service
public class DictionaryServiceImpl implements DictionaryService{
	@Resource
	private DictionaryMapper dictionaryMapper;

	public List<Dictionary> getState(String typeCode) {
		List<Dictionary> dictionary=new ArrayList<Dictionary>();
		dictionary=dictionaryMapper.getState(typeCode);
		return dictionary;
	}
	

	
	

}
