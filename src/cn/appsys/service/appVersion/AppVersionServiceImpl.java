package cn.appsys.service.appVersion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.appVersion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;

@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Resource
	private AppVersionMapper appVersionMapper;

	public List<AppVersion> byId_verlist(Integer appId) {
		List<AppVersion> verList = new ArrayList<AppVersion>();
		verList = appVersionMapper.byId_verlist(appId);
		return verList;
	}

	public boolean addVer(AppVersion appVersion) {
		boolean falg = false;
		int a = appVersionMapper.addVer(appVersion);
		if (a > 0) {
			falg = true;
		}
		return falg;
	}

	public AppVersion getAppVer(Integer appId, String versionNo) {
		AppVersion appVersion = appVersionMapper.getAppVer(appId, versionNo);

		return appVersion;
	}

	public AppVersion idgetVer(Integer id)throws Exception {

		return appVersionMapper.idgetVer(id);
	}

	public boolean updateVer(AppVersion appVersion) {
		boolean falg = false;
		int a = appVersionMapper.updateVer(appVersion);

		if (a > 0) {
			falg = true;
		}
		return falg;
	}

	public boolean deleUrl(Integer id)throws Exception {
		boolean falg = false;

		if (appVersionMapper.idgetVer(id).getApkLocPath() != null
				&& !appVersionMapper.idgetVer(id).getApkLocPath().equals("")) {
			// 删除服务器上个人证件照
			File file = new File(appVersionMapper.idgetVer(id).getApkLocPath());
			if (file.exists()) {
				falg = file.delete();
			}
		}

		int a = appVersionMapper.deleUrl(id);

		return falg;
	}

}
