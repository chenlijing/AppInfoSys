package cn.appsys.service.appCategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	public  List<AppCategory> getGoryChild(Integer parentId); 
}
