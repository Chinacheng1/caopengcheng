package cn.appsys.controller.backend;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.AppinfoService;
import cn.appsys.service.developer.DatadictionaryService;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/backend/app")
public class BackAppInfoController {
	
	@Resource
	private AppinfoService appinfoService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private DatadictionaryService datadictionaryService;
	
	@Resource
	private AppVersionService appVersionService;
	
	@RequestMapping(value="/list")
	public String list(Model model,@RequestParam(value="querySoftwareName",required=false) String _querySoftwareName,//软件名称
			@RequestParam(value="queryFlatformId",required=false) Integer queryFlatformId,  //所属平台id
			@RequestParam(value="queryCategoryLevel1",required=false) Integer queryCategoryLevel1, //一级列表id
			@RequestParam(value="queryCategoryLevel2",required=false) Integer queryCategoryLevel2, //二级列表id
			@RequestParam(value="queryCategoryLevel3",required=false) Integer queryCategoryLevel3, //二级列表id
			@RequestParam(value="pageIndex",required=false)Integer pageIndex                       //分页页码
			) throws UnsupportedEncodingException {
		String querySoftwareName = null;
		if(_querySoftwareName != null && _querySoftwareName != "") {
			querySoftwareName = new String(_querySoftwareName.getBytes("ISO-8859-1"),"UTF-8");
			model.addAttribute("querySoftwareName", querySoftwareName);
		}
		PageSupport pages = new PageSupport();
		Integer IndexNo = 1;
		Integer Count = appinfoService.getCountAppinfo(querySoftwareName, null, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
		if(pageIndex != null ) {
			if(pageIndex > Count) {
				IndexNo = Count;
			}else if(pageIndex < 0) {
				IndexNo = 1;
			}else {
				IndexNo = pageIndex;
			}
		}
		pages.setCurrentPageNo(IndexNo);
		pages.setPageSize(5);
		pages.setTotalCount(Count);
		List<AppInfo> appinfolist = appinfoService.getlistAppinfo(querySoftwareName, null, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, (pages.getCurrentPageNo()-1)*pages.getPageSize(), pages.getPageSize());
		model.addAttribute("appInfoList", appinfolist);
		model.addAttribute("categoryLevel1List", appCategoryService.getAppCategory(null)); //加载一级列表
		model.addAttribute("flatFormList", datadictionaryService.getlistDataDictionary("APP_FLATFORM"));	//加载平台
		model.addAttribute("pages", pages);
		model.addAttribute("queryCategoryLevel1",queryCategoryLevel1 );
		model.addAttribute("queryCategoryLevel2",queryCategoryLevel2 );
		model.addAttribute("queryCategoryLevel3",queryCategoryLevel3 );
		if(queryFlatformId != null && queryFlatformId != 0) model.addAttribute("queryFlatformId", queryFlatformId);
		if(queryCategoryLevel1 != null && queryCategoryLevel1 !=0) model.addAttribute("categoryLevel2List", categoryLevelList(queryCategoryLevel1));
		if(queryCategoryLevel2 != null && queryCategoryLevel2 !=0) model.addAttribute("categoryLevel3List", categoryLevelList(queryCategoryLevel2));
		return "backend/applist";
	}
	
	
	public List<AppCategory>  categoryLevelList(Integer level){
		return appCategoryService.getAppCategory(level);
	}
	@RequestMapping(value="categorylevellist.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object categoryLevelJson(@RequestParam(value="pid") Integer level) {
		return 	JSONArray.toJSONString(categoryLevelList(level));
	}
	
	/**
	 * 跳转审核页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/check")
	public String appcheck(Model model,@RequestParam(value="aid",required=false) Integer aid,@RequestParam(value="vid",required=false) Integer vid) {
		model.addAttribute("appInfo", appinfoService.getAppInfo(aid));
		model.addAttribute("appVersion", appVersionService.getAppVersion(vid)); 
		return "backend/appcheck";
	}
	@RequestMapping(value="/checksave")
	public String checksave(AppInfo appinfo) {
		appinfoService.getByIdUpdaatestatus(appinfo.getId(), appinfo.getStatus());
		return "redirect:/backend/app/list";
	}
	
}
