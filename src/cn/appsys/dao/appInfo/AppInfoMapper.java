package cn.appsys.dao.appInfo;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	/*模糊查询*/
	public List<AppInfo>getAppInfoList(
			
			@Param("softwareName")String softwareName,
			@Param("status")Integer status,
			@Param("flatformId")Integer flatformId,
			@Param("categoryLevel1")Integer categoryLevel1,
			@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,
			@Param("currentPageNo")Integer currentPageNo,
			@Param("pageSize")Integer pageSize
			);
	
	
	public int getCount();
	
	/*通过APKName得到AppInfo对象*/
	public AppInfo byAPKName(@Param("APKName")String APKName );
	
	/*通过APKName得到AppInfo对象*/
	public int byAPKNameget(@Param("APKName")String APKName );

/*添加功能*/

	public int addAppInfo(AppInfo appInfo );
	
	/*通过id获得对象*/
	public AppInfo getById(@Param("id")Integer id);
	
	/*修改AppInfo信息*/
	public int updateId(AppInfo appInfo);
	
	public int getStuts(@Param("id")Integer id);
	
	/*通过id删除Url*/
	public int getIdUrl(@Param("id")Integer id);
	/*通过id保存Url*/
	public int getIdUrlSave(@Param("logoPicPath")String logoPicPath,@Param("logoLocPath")String logoLocPath,@Param("id")Integer id);

	
	/*后台操作 开始*/
	/*模糊查询*/
	public List<AppInfo>getAppInfoListSH(
			@Param("softwareName")String softwareName,
			@Param("flatformId")Integer flatformId,
			@Param("categoryLevel1")Integer categoryLevel1,
			@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3,
			@Param("currentPageNo")Integer currentPageNo,
			@Param("pageSize")Integer pageSize
			);
	
	/*显示的条数*/
	public int getCountSH(
			@Param("softwareName")String softwareName,
			@Param("flatformId")Integer flatformId,
			@Param("categoryLevel1")Integer categoryLevel1,
			@Param("categoryLevel2")Integer categoryLevel2,
			@Param("categoryLevel3")Integer categoryLevel3);
			
		
	/*后台操作 结束*/
	
}
