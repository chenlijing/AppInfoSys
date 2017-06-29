package cn.appsys.dao.appVersion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	/* 通过appId 获取版本集合 */
	public List<AppVersion> byId_verlist(@Param("appId")Integer appId);
	
	/*增加版本*/
	public int addVer(AppVersion appVersion);
	
	/*<!-- 通过APPId和versionNo得到版本对象 -->*/
	public AppVersion getAppVer(@Param("appId")Integer appId,@Param("versionNo")String versionNo);
	
	/*<!-- 通过id获取版本对象 -->*/
	public AppVersion idgetVer(@Param("id")Integer id)throws Exception;
	
	/*修改版本*/
	public int updateVer(AppVersion appVersion);
	
	/*通过id删除路径*/
	public int deleUrl(@Param("id")Integer id);
			}
