package cn.appsys.dao.backendUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface  BackendUserMapper {
	public BackendUser login(@Param("userCode")String userCode);

}
