package cn.appsys.controller.dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.appInfo.AppInfoService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.dev.DevUserService;
import cn.appsys.tools.Constants;

/*版本Controller*/
@Controller
@RequestMapping(value = "/ver")
public class AppVerController {
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private AppInfoService appInfoService;


	/* 新增版本界面 */
	@RequestMapping(value = "/appversionadd", method = RequestMethod.GET)
	public String addVer(Integer id, Model model) {
		System.out.println("从页面获得的值是：" + id);
		List<AppVersion> appVersions = new ArrayList<AppVersion>();
		appVersions = appVersionService.byId_verlist(id);
		
		AppVersion appVersion=new AppVersion();
		appVersion.setAppId(id);
		
		AppInfo appInfo=appInfoService.getById(id);
		appVersion.setAPKName(appInfo.getAPKName());
		
		model.addAttribute("appVersionList", appVersions);
		model.addAttribute("appVersion", appVersion);
		return "developer/appversionadd";
	}

	/* 新增版本保存 */
	@RequestMapping(value = "/addVerSave", method = RequestMethod.POST)
	public String addVerSave(
			AppVersion appVersion,
			HttpServletRequest request,
			@RequestParam(value = "a_downloadLink", required = false) MultipartFile attach,
			Model model) {
		System.out.println("生成后的文件绝对路径：");
		String downloadLink = null;
		
		// 判断文件是否为空
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			System.out.println("path值是：" + path);
			// 原文件名
			String oldFileName = attach.getOriginalFilename();
			System.out.println("原文件名：" + oldFileName);
			// 原文件后缀
			String prefix = FilenameUtils.getExtension(oldFileName);
			System.out.println("原文件后缀" + prefix);
			int filesize = 500000000;
			/* 判断大小 */
			if (attach.getSize() > filesize) {
				request.setAttribute("uploadFileError", "上传文件不得超过500KB");
				return "developer/appversionadd";
				/* 判断格式 */
			} else if (prefix.equalsIgnoreCase("apk")) {
				String APKName = appVersion.getAPKName();
				System.out.println("apk名称："+APKName);
				String versionNo = appVersion.getVersionNo();
				System.out.println("版本名称："+versionNo);
				
				String zuhe=APKName+ "-" + versionNo + ".apk";
				String fileName = path +"\\"+zuhe;
						
				
				System.out.println("生成后的文件绝对路径：" + fileName);
				System.out.println("生成后的文件名称：" +zuhe );
				 downloadLink="\\AppInfoSystem\\statics\\uploadfiles\\"+zuhe;
				System.out.println("生成后的文件相对：" +downloadLink );

				File targetFile = new File(path, zuhe);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				// 保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "上传失败！");
					return "developer/appversionadd";
				}
				
				  appVersion.setApkFileName(zuhe);
				  appVersion.setApkLocPath(fileName);
				  appVersion.setDownloadLink(downloadLink);
				  appVersion.setModifyDate(new Date());

			} else {
				request.setAttribute("uploadFileError", "上传图片格式不正确失败！");
				return "developer/appversionadd";
			}
		}
		appVersion.setCreationDate(new Date());

		if (appVersionService.addVer(appVersion)) {
			 /*让list页面显示最新的版本号*/
			  AppVersion appVer=new AppVersion();
			  appVer=appVersionService.getAppVer(appVersion.getAppId(), appVersion.getVersionNo());
			  System.out.println("新增版本的名称是："+appVer.getApkFileName());
			  AppInfo appInfo=appInfoService.getById(appVersion.getAppId());
			  appInfo.setVersionId(appVer.getId());
			  appInfoService.updateId(appInfo);
			return "redirect:/dev/userList";
		}
		System.out.println("进入版本保存￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
		return "developer/appversionadd";
	}
	
	
	/*修改版本*/
	@RequestMapping(value = "/appversionmodify", method = RequestMethod.GET)
	public String updateVer(Integer vid,Integer aid, Model model)throws Exception {
		System.out.println("从页面获得的值是：aid" + aid);
		System.out.println("从页面获得的值是：vid" + vid);
		List<AppVersion> appVersions = new ArrayList<AppVersion>();
		appVersions = appVersionService.byId_verlist(aid);
		
		AppVersion appVersion=new AppVersion();
		model.addAttribute("appVersionList", appVersions);
	
		
		/*显示要修改最新版本的界面*/
		appVersion=appVersionService.idgetVer(vid);
		
		appVersionService.updateVer(appVersion);
		
		model.addAttribute("appVersion", appVersion);
		
		
		return "developer/appversionmodify";
	}
	
	
	@RequestMapping(value = "/appversionmodifysave", method = RequestMethod.POST)
	public String updateVerSave(AppVersion appVersion,
			@RequestParam(value = "attach", required = false) MultipartFile attach,
			HttpServletRequest request,
			Model model) {
		String downloadLink = null;
		
		// 判断文件是否为空
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			System.out.println("path值是：" + path);
			// 原文件名
			String oldFileName = attach.getOriginalFilename();
			System.out.println("原文件名：" + oldFileName);
			// 原文件后缀
			String prefix = FilenameUtils.getExtension(oldFileName);
			System.out.println("原文件后缀" + prefix);
			int filesize = 500000000;
			/* 判断大小 */
			if (attach.getSize() > filesize) {
				request.setAttribute("uploadFileError", "上传文件不得超过500KB");
				return "developer/appversionadd";
				/* 判断格式 */
			} else if (prefix.equalsIgnoreCase("apk")) {
				String APKName = appVersion.getAPKName();
				System.out.println("apk名称："+APKName);
				String versionNo = appVersion.getVersionNo();
				System.out.println("版本名称："+versionNo);
				
				String zuhe=APKName+ "-" + versionNo + ".apk";
				String fileName = path +"\\"+zuhe;
						
				
				System.out.println("生成后的文件绝对路径：" + fileName);
				System.out.println("生成后的文件名称：" +zuhe );
				 downloadLink="\\AppInfoSystem\\statics\\uploadfiles\\"+zuhe;
				System.out.println("生成后的文件相对：" +downloadLink );

				File targetFile = new File(path, zuhe);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				// 保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "上传失败！");
					return "developer/appversionadd";
				}
				
				  appVersion.setApkFileName(zuhe);
				  appVersion.setApkLocPath(fileName);
				  appVersion.setDownloadLink(downloadLink);
				  appVersion.setModifyDate(new Date());

			} else {
				request.setAttribute("uploadFileError", "上传图片格式不正确失败！");
				return "developer/appversionadd";
			}
		}
		appVersion.setCreationDate(new Date());
		
		
		/*显示要修改最新版本的界面*/
		
		
		appVersionService.updateVer(appVersion);
		
		model.addAttribute("appVersion", appVersion);
		
		
		return "redirect:/dev/userList";
	}
	
	

	

}
