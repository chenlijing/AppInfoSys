package cn.appsys.controller.banckend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.Dictionary;
import cn.appsys.service.appCategory.AppCategoryService;
import cn.appsys.service.appInfo.AppInfoService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.backend.backendUserService.BackendUserService;
import cn.appsys.service.dictionary.DictionaryService;

@Controller
@RequestMapping(value = "/back")
public class BackendUserController {
	@Resource
	private BackendUserService backendUserService;
	@Resource
	private AppCategoryService appCategoryService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private AppInfoService appInfoService;
	@Resource
	private AppVersionService appVersionService;

	/* 进入欢迎页面 */
	@RequestMapping(value = "/backlogin")
	public String dologin() {
		return "backend/bwelcome";
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

	/* App审核list界面 */
	@RequestMapping(value = "/applist")
	public String shenHeList(
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "flatformId", required = false) Integer flatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) Integer queryCategoryLevel3,
			@RequestParam(value = "pageIndex", required = false) Integer pageIndex,
			Model model, HttpSession session) {
		/*
		 * List<AppCategory> listcategory1 = null; List<AppCategory>
		 * listcategory2 = null; List<AppCategory> listcategory3 = null;
		 * model.addAttribute("listcategory1", listcategory1);
		 * model.addAttribute("listcategory2", listcategory2);
		 * model.addAttribute("listcategory3", listcategory3);
		 */
		if (pageIndex != null && pageIndex != 0) {

		} else {
			pageIndex = 1;
		}
		List<AppInfo> list = new ArrayList<AppInfo>();
		PageSupport pages = new PageSupport();
		/* 1.获取页容量 */
		int pageSize = Constants.pageSize;
		/* 2.获取总条数 */
		int totalCount = appInfoService.getCountSH(querySoftwareName,
				flatformId, queryCategoryLevel1, queryCategoryLevel2,
				queryCategoryLevel3);
		/* 3.获取页码转换为起始位置 */
		int pageIndex1 = (pageIndex - 1) * pageSize;
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		pages.setCurrentPageNo(pageIndex);
		model.addAttribute("pages", pages);
		/* 4.获取页数 */
		list = appInfoService.getAppInfoListSH(querySoftwareName, flatformId,
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3,
				pageIndex1, pageSize);
		model.addAttribute("appInfoList", list);
		System.out.println("进入。。。。。。。。。。。。。审核list界面");
		return "backend/applist";
	}

	/* 进入审核页面 */
	@RequestMapping(value = "/check")
	public String SH(Integer appinfoid,Integer status,Integer vid, Model model){
		AppInfo appInfo = new AppInfo();
		appInfo = appInfoService.getById(appinfoid);
		/* 显示appInfo的详细信息 */
		model.addAttribute("appInfo", appInfo);
		/* 显示appInfo的最新版本信息 */
		AppVersion appVersion = new AppVersion();
		
	
			try {
				appVersion = appVersionService.idgetVer(vid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(status==2){
				System.out.println("审核通过。。。。。");
			}else if(status==3){
				System.out.println("审核不通过。。。。");
			}
			appInfo.setStatus(2);
		
		model.addAttribute("appVersion", appVersion);
		System.out.println("进入审核页面。。。。。。。。。。");
		return "backend/appcheck";
	}
	
	public String checksave(){
		return "redirect:/back/applist";
	}

}
