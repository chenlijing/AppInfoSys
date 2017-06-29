package cn.appsys.service.appInfo;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper appInfoMapper;

	public List<AppInfo> getAppInfoList(String softwareName, Integer status,
			Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer pageNo, Integer pageSize) {

		List<AppInfo> appList = appInfoMapper.getAppInfoList(softwareName,
				status, flatformId, categoryLevel1, categoryLevel2,
				categoryLevel3, pageNo, pageSize);
		return appList;
	}

	public int getCount() {

		return appInfoMapper.getCount();
	}

	public AppInfo byAPKName(String APKName) {

		return appInfoMapper.byAPKName(APKName);
	}

	public int byAPKNameget(String APKName) {

		return appInfoMapper.byAPKNameget(APKName);
	}

	public boolean addAppInfo(AppInfo appInfo) {
		boolean falg = false;

		int a = appInfoMapper.addAppInfo(appInfo);

		if (a > 0) {
			falg = true;
		}
		return falg;
	}

	public AppInfo getById(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.getById(id);
	}

	public boolean updateId(AppInfo appInfo) {
		boolean falg = false;
		int a = appInfoMapper.updateId(appInfo);
		if (a > 0) {
			falg = true;
		}
		return falg;
	}

	public boolean getStuts(Integer id) {
		boolean falg = false;
		if (appInfoMapper.getStuts(id) > 0) {
			falg = true;
		}
		return falg;
	}

	public boolean getIdUrl(Integer id) {
		boolean falg = true;

		if (appInfoMapper.getById(id).getLogoLocPath() != null
				&& !appInfoMapper.getById(id).getLogoLocPath().equals("")) {
			// 删除服务器上个人工作证照片
			File file = new File(appInfoMapper.getById(id).getLogoLocPath());
			if (file.exists()) {
				falg = file.delete();
			}
			int a = appInfoMapper.getIdUrl(id);
			if (a > 0) {
				falg = true;
			} else {
				falg = false;
			}

		}
		return falg;
	}

	public boolean getIdUrlSave(String logoPicPath, String logoLocPath,
			Integer id) {
		boolean falg = false;
		int a = appInfoMapper.getIdUrlSave(logoPicPath, logoLocPath, id);
		if (a > 0) {
			falg = true;
		}
		return falg;
	}

	public List<AppInfo> getAppInfoListSH(String softwareName,
			Integer flatformId, Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3, Integer currentPageNo, Integer pageSize) {
		
		return appInfoMapper.getAppInfoListSH(softwareName, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, currentPageNo, pageSize);
	}

	public int getCountSH(String softwareName, Integer flatformId,
			Integer categoryLevel1, Integer categoryLevel2,
			Integer categoryLevel3) {
		// TODO Auto-generated method stub
		return appInfoMapper.getCountSH(softwareName, flatformId, categoryLevel1, categoryLevel2, categoryLevel3);
	}

}
