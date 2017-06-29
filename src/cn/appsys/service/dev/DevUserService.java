package cn.appsys.service.dev;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	public DevUser login(String devCode,String devPassword);
	
}
