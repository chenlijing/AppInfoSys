package cn.appsys.dao.dictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Dictionary;

public interface DictionaryMapper {
	public List<Dictionary> getState(@Param("typeCode")String typeCode);
}
