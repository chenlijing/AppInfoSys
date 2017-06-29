package cn.appsys.service.appVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	/* 通过appId 获取版本集合 */
	public List<AppVersion> byId_verlist(Integer appId);
	
	/*增加版本*/
	public boolean addVer(AppVersion appVersion);
	
	public AppVersion getAppVer(Integer appId,String versionNo);
	
	/*<!-- 通过id获取版本对象 -->*/
	public AppVersion idgetVer(Integer id)throws Exception;
	
	/*修改版本*/
	public boolean updateVer(AppVersion appVersion);
	
	/*通过id删除路径*/
	public boolean deleUrl(Integer id)throws Exception;

}
