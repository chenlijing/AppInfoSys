package cn.appsys.service.appInfo;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	/*模糊查询*/
	public List<AppInfo> getAppInfoList(
			String softwareName,
			Integer status,
			Integer flatformId,
			Integer categoryLevel1,
			Integer categoryLevel2,
			Integer categoryLevel3,
			Integer pageNo,
			Integer pageSize
			);
	public int getCount();

	/*通过APKName得到AppInfo对象*/
	public AppInfo byAPKName(String APKName );
	
	public int byAPKNameget(String APKName );
	public boolean addAppInfo(AppInfo appInfo);
	/*通过id获取对象*/
	public AppInfo getById(Integer id);
	
	/*修改AppInfo信息*/
	public boolean updateId(AppInfo appInfo);
	
	/*通过id将审核未通过的修改为待审核*/
	public boolean  getStuts(Integer id);
	/*通过id删除路径*/
	public boolean getIdUrl(Integer id);
	
	/*通过id保存Url*/
	public boolean getIdUrlSave(String logoPicPath,String logoLocPath,Integer id);
	
	
	
	/*后台管理系统*/
	/*模糊查询*/
	public List<AppInfo>getAppInfoListSH(
			String softwareName,
			Integer flatformId,
			Integer categoryLevel1,
			Integer categoryLevel2,
			Integer categoryLevel3,
			Integer currentPageNo,
			Integer pageSize
			);
	
	/*显示的条数*/
	public int getCountSH(
			String softwareName,
			Integer flatformId,
			Integer categoryLevel1,
			Integer categoryLevel2,
			Integer categoryLevel3);
}
