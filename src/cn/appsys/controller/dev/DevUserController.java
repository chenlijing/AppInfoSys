package cn.appsys.controller.dev;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.asm.commons.TryCatchBlockSorter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.Dictionary;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appInfo.AppInfoService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.backend.backendUserService.BackendUserService;
import cn.appsys.service.dev.DevUserService;
import cn.appsys.service.dictionary.DictionaryService;

@Controller
@RequestMapping(value = "/dev")
public class DevUserController {
	@Resource
	private DevUserService devUserService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private AppVersionService appVersionService;

	/* 欢迎界面 */
	@RequestMapping(value = "/dwelcome")
	public String dologin() {

		return "developer/dwelcome";
	}

	/* 显示一二三级菜单 */
	@RequestMapping(value = "/categorylevellist.json", method = RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getGoryChild(Integer pid) {
		List<AppCategory> listcategory = new ArrayList<AppCategory>();
		listcategory = appCategoryService.getGoryChild(pid);
		return listcategory;
	}

	/* 显示所属平台 */
	@RequestMapping(value = "/datadictionarylist.json", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getTerrace(String tcode) {
		List<Dictionary> SSPTdictionary = new ArrayList<Dictionary>();
		try {
			SSPTdictionary = dictionaryService.getState(tcode);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SSPTdictionary;
	}
	
	/*异步删除图片*/
	@RequestMapping(value = "/delfile.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getIdUrl(Integer id,String flag)throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		if(flag.equals("logo")){
			if(appInfoService.getIdUrl(id)){
				map.put("result", "success");
			}else{
				map.put("result", "failed");
			}
		}else if(flag.equals("apk")){
			if(appVersionService.deleUrl(id)){
				map.put("result", "success");
			}else{
				map.put("result", "failed");
			}
			
		}
		
		return map;
	}
	/*版本上架或下架*/
	@RequestMapping(value = "/{appId}/sale.json", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, String> getStuts(@PathVariable Integer appId) {
		Map<String, String> map=new HashMap<String, String>();
		boolean falg=false;
		AppInfo appInfo=new AppInfo();
		AppVersion appver=new AppVersion();
		try {
			
			appInfo=appInfoService.getById(appId);
			appver=appVersionService.idgetVer(appInfo.getVersionId());
		} catch (Exception e) {
			e.getStackTrace();
		}
				int a=appInfo.getStatus();
		if(a==2||a==5){
			appInfo.setStatus(4);
			appInfo.setOnSaleDate(new Date());
			appver.setPublishStatus(2);
		System.out.println("*****"+appInfo.getStatus());
		}else if(a==4){
			appInfo.setStatus(5);
			appInfo.setOffSaleDate(new Date());
			appver.setPublishStatus(1);
		}else{
			
		}
	
		
	
		if(appInfoService.updateId(appInfo)&&appVersionService.updateVer(appver)){
			map.put("resultMsg", "success");
		}else{
			map.put("resultMsg", "failed");
		}
		
		
		
		return map;
	}
	
	
	
	/* 异步验证APKName的唯一性 */
	@RequestMapping(value = "/apkexist.json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getAPKName(String APKName) {
		HashMap<String, String> map = new HashMap<String, String>();
		if (APKName != null && !"".equals(APKName)) {

			int a = appInfoService.byAPKNameget(APKName);
			System.out.println("a的知是：" + a);
			if (a > 0) {
				map.put("APKName", "exist");
			} else if (a == 0) {
				map.put("APKName", "noexist");
			}

		} else {
			map.put("APKName", "empty");
		}
		return map;
	}

	/* 显示list界面 */
	@RequestMapping(value = "/userList")
	public String devList(
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "APPqueryStatus", required = false) Integer APPqueryStatus,
			@RequestParam(value = "queryStatus", required = false) Integer queryStatus,
			@RequestParam(value = "queryCategoryLevel1", required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) Integer queryCategoryLevel3,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
			Model model, HttpSession session) {
		List<AppInfo> appInfoList = new ArrayList<AppInfo>();
		/* 显示App状态,所属平台 */
		System.out.println("页面得到的值是：===================");
		List<Dictionary> appdictionary = new ArrayList<Dictionary>();
		appdictionary = dictionaryService.getState("APP_STATUS");

		model.addAttribute("APPstatusList", appdictionary);

		List<Dictionary> SSPTdictionary = new ArrayList<Dictionary>();
		SSPTdictionary = dictionaryService.getState("APP_FLATFORM");
		model.addAttribute("SSPTstatusList", SSPTdictionary);

		/* 显示第一级的菜单 */
		List<AppCategory> listcategory1 = null;
		List<AppCategory> listcategory2 = null;
		List<AppCategory> listcategory3 = null;

		listcategory1 = appCategoryService.getGoryChild(null);
		model.addAttribute("listcategory1", listcategory1);

		if (queryCategoryLevel1 != null) {
			listcategory2 = appCategoryService
					.getGoryChild(queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null) {
			listcategory3 = appCategoryService
					.getGoryChild(queryCategoryLevel2);
		}

		model.addAttribute("listcategory2", listcategory2);
		model.addAttribute("listcategory3", listcategory3);
		/* 分页的处理开始------------ */

		PageSupport pages = new PageSupport();
		// 当前页码
		int currentPageNo = 1;
		if (pageIndex != null && pageIndex != 0) {
			currentPageNo = pageIndex;
		}

		/* 获得一页的容量 */
		int pageSize = Constants.pageSize;

		/* 总共有的页数 */
		/* 分页的处理结束------------ */

		int totalCount = appInfoService.getCount();

		System.out.println("页面总数：" + totalCount);
		int dataIndex = (currentPageNo - 1) * pageSize;
		appInfoList = appInfoService.getAppInfoList(querySoftwareName,
				APPqueryStatus, queryStatus, queryCategoryLevel1,
				queryCategoryLevel2, queryCategoryLevel3, dataIndex, pageSize);
		/* 总条数记录 */
		System.out.println("pageSize" + pageSize);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		pages.setCurrentPageNo(currentPageNo);
		model.addAttribute("pages", pages);

		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("APPqueryStatus", APPqueryStatus);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);

		model.addAttribute("appInfoList", appInfoList);
		return "developer/appinfolist";
	}

	/* 新增功能界面 */
	@RequestMapping(value = "/appinfoadd")
	public String addAppInfo() {
		return "developer/appinfoadd";
	}

	/* 新增功能保存 */
	@RequestMapping(value = "/appinfoaddsave", method = RequestMethod.POST)
	public String addAppInfoSave(
			AppInfo appInfo,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach) {
		String logoLocPath = null;
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
			int filesize = 500000;
			/* 判断大小 */
			if (attach.getSize() > filesize) {
				request.setAttribute("uploadFileError", "上传文件不得超过500KB");
				return "developer/appinfoadd";
				/* 判断格式 */
			} else if (prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("png")
					|| prefix.equalsIgnoreCase("jpeg")
					|| prefix.equalsIgnoreCase("pneg")) {
				String fileName =System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.jpg";
				System.out.println("生成后的文件名为：" + fileName);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()){
					targetFile.mkdirs();
				}

				// 保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "上传失败！");
					return "developer/appinfoadd";
				}
				logoLocPath = path + File.separator + fileName;
				String logoPicPath="/AppInfoSystem/statics/uploadfiles/"+fileName;
				System.out.println("logoLocPath数据库字段：" + logoLocPath);
				System.out.println("logoPicPath数据库字段：" + logoPicPath);
				appInfo.setLogoLocPath(logoLocPath);
				appInfo.setLogoPicPath(logoPicPath);
				
				
			} else {
				request.setAttribute("uploadFileError", "上传图片格式不正确失败！");
				return "developer/appinfoadd";
			}
		}
		appInfo.setCreationDate(new Date());

		
		
		if (appInfoService.addAppInfo(appInfo)) {
			return "redirect:/dev/userList";
		}

		return "developer/appinfoadd";
	}

	/* 修改界面 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateAppInfo(@PathVariable Integer id, Model model) {
		AppInfo appInfo = appInfoService.getById(id);
		model.addAttribute("appInfo", appInfo);
		return "developer/appinfomodify";
	}

	/* 修改界面保存 */
	@RequestMapping(value = "/updateSave", method = RequestMethod.POST)
	public String updateAppInfo(Integer id,
			AppInfo appInfo,
			HttpServletRequest request,
			@RequestParam(value = "attach", required = false) MultipartFile attach) {

		System.out.println("进入修改保存==============");
		System.out.println("页面获得id的值是：" + id);
		
		/*将审核未通过的修改为待审核*/
		System.out.println("当状态为3时："+appInfo.getStatus());
		if(appInfo.getStatus()==3){
			appInfoService.getStuts(id);
			System.out.println("难道没进里面修改吗？，，，，，，，，，，，，");
		}
		
		/*修改图片时的方法*/
		String logoLocPath = null;
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
			int filesize = 500000;
			/* 判断大小 */
			if (attach.getSize() > filesize) {
				request.setAttribute("uploadFileError", "上传文件不得超过500KB");
				return "developer/appinfoadd";
				/* 判断格式 */
			} else if (prefix.equalsIgnoreCase("jpg")
					|| prefix.equalsIgnoreCase("png")
					|| prefix.equalsIgnoreCase("jpeg")
					|| prefix.equalsIgnoreCase("pneg")) {
				String fileName =System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.jpg";
				System.out.println("生成后的文件名为：" + fileName);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()){
					targetFile.mkdirs();
				}

				// 保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", "上传失败！");
					return "developer/appinfoadd";
				}
				logoLocPath = path + File.separator + fileName;
				String logoPicPath="/AppInfoSystem/statics/uploadfiles/"+fileName;
				System.out.println("logoLocPath数据库字段：" + logoLocPath);
				System.out.println("logoPicPath数据库字段：" + logoPicPath);
				boolean falg=appInfoService.getIdUrlSave(logoPicPath, logoLocPath, id);
				
				
			} else {
				request.setAttribute("uploadFileError", "上传图片格式不正确失败！");
				return "developer/appinfoadd";
			}
		}
		
		boolean falg = appInfoService.updateId(appInfo);
		if (falg) {
			return "redirect:/dev/userList";
		}
		return "developer/appinfomodify";
	}

	
	
}
