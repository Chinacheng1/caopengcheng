package cn.appsys.controller.developer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.AppinfoServiceImpl;
import cn.appsys.service.developer.DatadictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/devappinfo")
public class AppinfoController {

	@Resource
	private AppinfoServiceImpl appinfoService;

	@Resource
	private AppCategoryService appCategoryService;

	@Resource
	private AppVersionService appVersionService;

	@Resource
	private DatadictionaryService  	datadictionaryService;


	@RequestMapping(value="/list")
	public String list(Model model,@RequestParam(value="pageIndex",required=false) String pageIndex,
			@RequestParam(value="queryStatus",required=false)Integer queryStatus,
			@RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,@RequestParam(value="queryCategoryLevel1",required=false)String _queryCategoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false)String _queryCategoryLevel2,@RequestParam(value="queryCategoryLevel3",required=false)String _queryCategoryLevel3,
			@RequestParam(value="querySoftwareName",required=false)String querySoftwareName) {
		Integer queryCategoryLevel1 = null;
		Integer queryCategoryLevel2 = null;
		Integer queryCategoryLevel3 = null;
		model.addAttribute("categoryLevel1List", appCategoryService.getAppCategory(null));
		if(_queryCategoryLevel1 != null && _queryCategoryLevel1 != "")
			queryCategoryLevel1 = Integer.parseInt(_queryCategoryLevel1);
		if(_queryCategoryLevel2 != null && _queryCategoryLevel2 != "")
			queryCategoryLevel2 = Integer.parseInt(_queryCategoryLevel2);
		if(_queryCategoryLevel3 != null && _queryCategoryLevel3 != "")
			queryCategoryLevel3 = Integer.parseInt(_queryCategoryLevel3); 
		model.addAttribute("statusList", datadictionaryService.getlistDataDictionary("APP_STATUS"));
		model.addAttribute("flatFormList",datadictionaryService.getlistDataDictionary("APP_FLATFORM"));
		PageSupport page = new PageSupport();
		int  PageCount = appinfoService.getCountAppinfo(querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3);
		int pageNo = 1;
		if(pageIndex != null) {
			pageNo = Integer.parseInt(pageIndex);
			if(pageNo <= 0) {
				pageNo = 1;
			}else if(pageNo > PageCount) {
				pageNo  = PageCount;
			}
		}
		page.setCurrentPageNo(pageNo);
		page.setPageSize(Constants.pageSize);
		page.setTotalCount(PageCount);
		//softwareName,status,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,pageNo,pageSize
		//软件名称querySoftwareName，APP状态queryStatus，所属平台queryFlatformId，
		model.addAttribute("appInfoList",appinfoService.getlistAppinfo(querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,(page.getCurrentPageNo()-1)*page.getPageSize(),page.getPageSize())); 
		model.addAttribute("pages",page);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
		if(querySoftwareName != null && querySoftwareName != "")
			model.addAttribute("querySoftwareName", querySoftwareName);
		if(queryStatus != null && queryStatus != 0)
			model.addAttribute("queryStatus",queryStatus);
		if(queryFlatformId != null && queryFlatformId != 0)
			model.addAttribute("queryFlatformId", queryFlatformId);
		if(queryCategoryLevel2 != null){
			model.addAttribute("categoryLevel2List", getCategoryList(queryCategoryLevel1));
		}
		if(queryCategoryLevel3 != null){
			model.addAttribute("categoryLevel3List", getCategoryList(queryCategoryLevel2));
		}
		return "developer/appinfolist";
	}

	public List<AppCategory> getCategoryList(Integer pid) {
		return  appCategoryService.getAppCategory(pid);
	}

	@RequestMapping(value="categorylevellist.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object lodaqueryCategoryLevel2(@RequestParam(value="pid",required=false)Integer pid) {
		List<AppCategory> list =getCategoryList(pid);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 根据app id查看app详细信息
	 * 根据app id 查询app历史版本
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appview/{id}")
	public String LoadView(Model model,@PathVariable(value="id") Integer id) {
		model.addAttribute("appInfo",appinfoService.getAppInfo(id));
		model.addAttribute("appVersionList", appVersionService.getAppVersionlist(id));
		return "developer/appinfoview";
	}
	/**
	 * 根据 app id 查询app，转发到修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appinfomodify")
	public String Appmodify(Model model,@RequestParam(value="id",required=false) Integer id) {
		model.addAttribute("appInfo",appinfoService.getAppInfo(id));
		return "developer/appinfomodify";
	}

	/**
	 * 根据 一级列表加载2、3级列表
	 * @param tcode
	 * @return
	 */
	@RequestMapping(value="datadictionarylist.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object datadictionarylist(@RequestParam(value="tcode",required=false)String tcode) {
		return JSONArray.toJSONString(datadictionaryService.getlistDataDictionary(tcode));
	}
	/**
	 * 更新数据
	 * @param res
	 * @param appinfo
	 * @param attach
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String appinfomodifysave(HttpServletRequest res,AppInfo appinfo,@RequestParam(value="attach",required=false) MultipartFile attach) {
		String idPath = null;
		if(attach != null) {
			String path = res.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//获取原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 5000000;
			if(attach.getSize() > filesize) {
				return "developer/appinfomodify";
			}else if(prefix.equals("jpg") || prefix.equals("png") || prefix.equals("jpeg") || prefix.equals("pneg")){
				String 	fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.jpg";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();  
				}
				try {
					attach.transferTo(targetFile);
				}catch(Exception e) {
					e.printStackTrace();
					return "developer/appinfomodify";
				}
				idPath = path+File.separator+fileName;
				appinfo.setLogoPicPath("/Appinfodb/statics/uploadfiles/"+fileName);
				appinfo.setLogoLocPath(idPath);
			}
			int result = appinfoService.upadteAppinfo(appinfo); 
			if(result == 1) {
				System.out.println("成功");	
			}else {
				System.out.println("失败");
			}
		}
		return "redirect:/devappinfo/list";
	}
	/**
	 * 根据id 删除图片，并删除磁盘文件
	 * @param id
	 * @param flag
	 * @return
	 */
	@RequestMapping(value="delfile.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object delfile(@RequestParam(value="id",required=false) Integer id ,@RequestParam(value="flag",required=false) String flag) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result","failed");
		if(flag.equals("logo")) {
			String path = appinfoService.getAppInfo(id).getLogoLocPath();
			if(path != null && path != "") {
				File file = new File(path);
				if(file.delete()) {
					if(appinfoService.deleteLogo(id) > 0) {
						resultMap.put("result","success");
					}
				}
			}
		}else {
			String path = appVersionService.getAppVersion(id).getApkLocPath();
			if(path != null && path != "") {
				File file = new File(path);
				if(file.delete()) {
					if(appVersionService.deleteByid(id) > 0) {
						resultMap.put("result","success");
					}
				}
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * 根据id删除app
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delapp.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object deleteApp(@RequestParam(value="id",required=false)Integer id) {
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("delResult","false");
		if(appinfoService.deleteApp(id) > 0) {
			result.put("delResult","true");
		}
		return JSONArray.toJSONString(result);
	}
	/**
	 * 跳转  修改版本页面
	 * 根据app aID 查询历史版本
	 * 根据版本 vID 查询当前版本
	 * @param vid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appversionmodify")
	public String appversionmodify(@RequestParam(value="vid",required=false) Integer vid,@RequestParam(value="aid",required=false) Integer aid,Model model) {
		model.addAttribute("appVersionList", appVersionService.getAppVersionlist(aid));
		model.addAttribute("appVersion", appVersionService.getAppVersion(vid)); 
		return "developer/appversionmodify";
	}
	/**
	 * 修改版本方法
	 * @return
	 */
	@RequestMapping(value="appversionmodifysave")
	public String  appversionmodifysave(HttpServletRequest res,AppVersion appVersion,@RequestParam(value="attach",required=false) MultipartFile attach) {
		String idPath = null;
		if(attach != null) {
			String path = res.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();//获取原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000000;
			if(attach.getSize() > filesize) {
				return "developer/appversionmodify";
			}else if(prefix.equals("apk")){
				String 	fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();  
				}
				try {
					attach.transferTo(targetFile);
				}catch(Exception e) {
					e.printStackTrace();
					return "developer/appversionmodify";
				}
				idPath = path+File.separator+fileName;
				appVersion.setDownloadLink("/Appinfodb/statics/uploadfiles/"+fileName);
				appVersion.setApkLocPath(idPath);
				appVersion.setApkFileName(fileName);
			}
		}
		if(appVersionService.updateByidAppVersion(appVersion) > 0) {
			System.out.println("成功");	
		}else {
			System.out.println("失败");
		}
		return "redirect:/devappinfo/list";
	}

	/**
	 * 跳转appinfoadd页面
	 * @return
	 */
	@RequestMapping(value="appinfoadd")
	public String appinsert() {
		return "developer/appinfoadd";
	}

	/**
	 * ajax异步验证apk是否存在
	 */
	@RequestMapping(value="apkexist.json",produces="application/json;charset=utf-8")
	@ResponseBody
	public Object apkexist(@RequestParam(value="APKName",required=false) String apkName) {
		HashMap<String,String> resultMap = new HashMap<String,String>();
		if(apkName == null  || apkName == "") {
			resultMap.put("APKName", "empty");
		}else if(appinfoService.apkexist(apkName) > 0){
			resultMap.put("APKName", "exist");
		}else {
			resultMap.put("APKName", "noexist");
		}

		return JSONArray.toJSONString(resultMap);
	}
	/**
	 * 新增app
	 * @param res
	 * @param appinfo
	 * @param model
	 * @param a_logoPicPath
	 * @return
	 */
	@RequestMapping(value="appinfoaddsave")
	public String appinfoaddsave(HttpServletRequest res,AppInfo appinfo,Model model,@RequestParam(value="a_logoPicPath",required=false)MultipartFile a_logoPicPath) {
		String idPath = null;
		if(a_logoPicPath != null) {
			String path = res.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = a_logoPicPath.getOriginalFilename();//获取原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000000;
			if(a_logoPicPath.getSize() > filesize) {
				return "developer/appinfoadd";
			}else if(prefix.equals("jpg") || prefix.equals("png") || prefix.equals("jpeg") || prefix.equals("pneg")){
				String 	fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();  
				}
				try {
					a_logoPicPath.transferTo(targetFile);
				}catch(Exception e) {
					e.printStackTrace();
					return "developer/appinfoadd";
				}
				idPath = path+File.separator+fileName;
				appinfo.setLogoPicPath("/Appinfodb/statics/uploadfiles/"+fileName);
				appinfo.setLogoLocPath(idPath);
				DevUser user = ((DevUser)res.getSession().getAttribute(Constants.DEV_USER_SESSION));
				appinfo.setCreatedBy(user.getId());
			}
		}
		if(appinfoService.inserAppInfo(appinfo) > 0) {
			System.out.println("成功");
		}else {
			System.out.println("失败");
		}
		int id = appinfoService.getByAPKName(appinfo.getAPKName());
		return "redirect:/devappinfo/appversionadd?id="+id;
	}
	/**
	 * 跳转到新增版本页面
	 * @param appVersion
	 * @param model
	 * @param id
	 * @param vid
	 * @return
	 */
	@RequestMapping(value="appversionadd")
	public String appVersionadd(AppVersion appVersion,Model model,@RequestParam(value="id",required=false)Integer id) {
		if(id != null && id != 0) {
			model.addAttribute("appVersionList", appVersionService.getAppVersionlist(id));
			appVersion.setAppId(id);
			model.addAttribute("appVersion",appVersion); 
		}
		return "developer/appversionadd";
	}
	/**
	 * 新增版本信息方法
	 * @param res
	 * @param appVersion
	 * @param a_downloadLink
	 * @return
	 */
	@RequestMapping(value="addversionsave")
	public String AppVersionInsert(HttpServletRequest res,AppVersion appVersion,@RequestParam(value="a_downloadLink",required=false)MultipartFile a_downloadLink) {
		String idPath = null;
		Integer appId = appVersion.getAppId();
		if(a_downloadLink != null) {
			String path = res.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = a_downloadLink.getOriginalFilename();//获取原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 500000000;
			if(a_downloadLink.getSize() > filesize) {
				return "developer/appversionmodify";
			}else if(prefix.equals("apk")){
				String 	fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_personal.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();  
				}
				try {
					a_downloadLink.transferTo(targetFile);
				}catch(Exception e) {
					e.printStackTrace();
					return "developer/appversionmodify";
				}
				idPath = path+File.separator+fileName;
				appVersion.setDownloadLink("/Appinfodb/statics/uploadfiles/"+fileName);
				appVersion.setApkLocPath(idPath);
				appVersion.setApkFileName(fileName);
			}
		}
		if(appVersionService.AppVersionInsert(appVersion) > 0) {
			System.out.println("成功");	
		}else {
			System.out.println("失败");
		}
		AppVersion versio =  appVersionService.getByAppidAppVersion(appId);
		appinfoService.getByIdUpdateVersion(appId, versio.getId());
		return "redirect:/devappinfo/list";
	}
	/**
	 * 上架或下架操作
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="{appid}/sale.json",method=RequestMethod.PUT,produces="application/json;charset=utf-8")
	@ResponseBody
	public Object sale(@PathVariable Integer appid) {
		HashMap<String,String> resultMap = new HashMap<String,String>();
		AppInfo appInfo  =null;
		resultMap.put("errorCode","0");
		try {
			appInfo = appinfoService.getAppInfo(appid);
			if(appInfo.getStatus() == 2 || appInfo.getStatus() == 5) {
				appInfo.setStatus(4);
			}else if(appInfo.getStatus() == 4) {
				appInfo.setStatus(5);
			}
		}catch(Exception e) {
			e.printStackTrace();
			resultMap.put("errorCode","failed");
		}
		if(appinfoService.getByIdUpdaatestatus(appInfo.getId(), appInfo.getStatus()) > 0) {
			resultMap.put("resultMsg","success");
		}
		return JSONArray.toJSONString(resultMap);
	}

}
