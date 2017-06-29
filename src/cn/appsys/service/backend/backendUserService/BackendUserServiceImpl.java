package cn.appsys.service.backend.backendUserService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.backendUser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;

@Service
public class BackendUserServiceImpl implements  BackendUserService{
	@Resource
	private BackendUserMapper backendUserMapper;
	public BackendUser login(String userCode,String userPassword) {
		System.out.println("从页面获取的参数是："+userCode+"密码：");
		
		BackendUser bUser =new BackendUser();
		bUser=backendUserMapper.login(userCode);
		if(null!=bUser){
			if(!bUser.getUserPassword().equals(userPassword)){
				bUser=null;
			}
		}
		return bUser;
	}

}
