package cn.appsys.service.dev;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.dao.devUser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceImpl implements DevUserService{
@Resource
private DevUserMapper devUserMapper;

	public  DevUser login(String devCode, String devPassword){
		DevUser dUser=new DevUser();
		dUser=devUserMapper.login(devCode);
		if(dUser!=null){
			if(!dUser.getDevPassword().equals(devPassword)){
				dUser=null;
			}
		}
		return dUser;
	}

}
