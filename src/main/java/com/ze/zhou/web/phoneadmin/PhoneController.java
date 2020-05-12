package com.ze.zhou.web.phoneadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.dto.ProblemExecution;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.enums.NoticeStateEnum;
import com.ze.zhou.enums.PhoneUserStateEnum;
import com.ze.zhou.enums.ProblemStateEnum;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.NoticeService;
import com.ze.zhou.service.PhoneUserService;
import com.ze.zhou.service.ProblemService;
import com.ze.zhou.util.CodeUtil;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.util.ImageHolder;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
@Controller
@RequestMapping("/phone")
public class PhoneController {
	Logger logger=(Logger) LoggerFactory.getLogger(PhoneController.class);
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private CoordinateService coordinateService;
	@Autowired
	private ProblemService problemService;
	@Autowired
	private PhoneUserService phoneUserService;
	
	//更新账号信息
	@RequestMapping(value="/modifyuser",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyUser(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		logger.debug("进入更新controller");
		String userAccountNumber=HttpServletRequestUtil.getString(request, "userAccountNumber");
		String userName=HttpServletRequestUtil.getString(request, "userName");
		String userAccountPassword=HttpServletRequestUtil.getString(request, "userAccountPassword");
		PhoneUser phoneUser=new PhoneUser();
		phoneUser.setUserAccountNumber(userAccountNumber);
		phoneUser.setUserName(userName);
		phoneUser.setUserAccountPassword(userAccountPassword);
		//获取头像数据流
		logger.debug("即将开始图片的获取");
		CommonsMultipartFile userImg=null;
		ImageHolder imageHolder=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=
					(MultipartHttpServletRequest)request;
			userImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("userImg");
			logger.debug("获取图片成功");
		}else {
			logger.debug("获取图片失败");
			modelMap.put("success", false);
			modelMap.put("errMsg","上传图片不能为空");
			return modelMap;
		}
		if(userImg!=null) {
			try {
				imageHolder=new ImageHolder(userImg.getOriginalFilename(),userImg.getInputStream());
				logger.debug("生成imageHolder对象");
			} catch (IOException e) {
				logger.debug("imgholder失败");
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}
		logger.debug("imgholder成功");
		PhoneUserExecution pue=phoneUserService.changePhoneUser(phoneUser, imageHolder);
		if(pue.getState()==PhoneUserStateEnum.SUCCESS.getState()) {
			//将更新后的内容查出来并返回到前端显示
			logger.debug("更新成功");
			modelMap.put("success", true);
			modelMap.put("phoneUserChange", pue.getPhoneUser());
		}else {
			logger.debug("更新失败");
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	//账号注册，添加账号
	@RequestMapping(value="/registeruser",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerUser(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//获取json字符串
		String phoneUserStr=HttpServletRequestUtil.getString(request, "phoneUserStr");
		logger.debug("json字符串为:"+phoneUserStr);
		ObjectMapper mapper=new ObjectMapper();
		PhoneUser phoneUser=null;
		try {
			phoneUser=mapper.readValue(phoneUserStr, PhoneUser.class);
		} catch (Exception e) {
			logger.debug("捕捉到了异常");
			modelMap.put("success", true);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		PhoneUserExecution pue=phoneUserService.addPhoneUser(phoneUser);
		if(pue.getState()==PhoneUserStateEnum.SUCCESS.getState()) {
			logger.debug("添加账号成功");
			modelMap.put("success", true);
		}else {
			logger.debug("添加账号失败");
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	//检查将要注册的账号是否存在
	@RequestMapping(value="/checkaccountexist",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> checkAccountExist(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		String phoneUserAccount=HttpServletRequestUtil.getString(request, "phoneUserAccount");
		logger.debug("phoneUserAccount:"+phoneUserAccount);
		if(phoneUserAccount!=null&&!("".equals(phoneUserAccount))) {
			logger.debug("进来了");
			PhoneUserExecution pue=phoneUserService.checkPhoneUserAccount(phoneUserAccount);
			logger.debug(pue.getState()+"");
			if(pue.getState()==PhoneUserStateEnum.FAILURE.getState()) {
				modelMap.put("success", true);
			}else if(pue.getState()==PhoneUserStateEnum.SUCCESS.getState()) {
				modelMap.put("success", false);
			}
			logger.debug("result："+pue.getState());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty phoneUserAccount");
		}
		return modelMap;
	}
	
	//登录验证
	@RequestMapping(value="/checkphonelogin",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> checkPhoneLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		logger.debug("验证码通过");
		String phoneUserStr=HttpServletRequestUtil.getString(request, "phoneUser");
		logger.debug("对象字符串:"+phoneUserStr);
		ObjectMapper mapper=new ObjectMapper();
		PhoneUser phoneUser=null;
		try {
			phoneUser=mapper.readValue(phoneUserStr, PhoneUser.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		logger.debug("获取的账号以及密码:"+phoneUser.getUserAccountNumber()+"/"+phoneUser.getUserAccountPassword());
		PhoneUserExecution pue=phoneUserService.checkPhoneUser(phoneUser);
		if(pue.getState()==PhoneUserStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("phoneUserLogin", pue.getPhoneUser());
		}else {
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	//获取问题列表，根据userId
	@RequestMapping(value="/getproblemlist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProblemList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//TODO 从前端中获取账号，通过账号获取对应的userId
		String userAccountNumber=HttpServletRequestUtil.getString(request, "userAccountNumber");
		logger.debug("获取的账号为:"+userAccountNumber);
		if(userAccountNumber==null) {
			modelMap.put("success", false);
			return modelMap;
		}
		PhoneUserExecution pue=phoneUserService.getPhoneUser(userAccountNumber);
		if(pue.getState()==PhoneUserStateEnum.FAILURE.getState()||
				pue.getState()==PhoneUserStateEnum.NULL_PHONEUSERID.getState()) {
			modelMap.put("success", false);
			return modelMap;
		}
		//先写死
		/*
		 * PhoneUser pu=new PhoneUser(); pu.setUserId(1);
		 */
		//获取分页用的数值
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		Problem problem=new Problem();
		problem.setUser(pue.getPhoneUser());
		ProblemExecution pec=problemService.getQueryProblem(problem, pageIndex, pageSize);
		if(pec.getState()==ProblemStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("problemList", pec.getProblemList());
			modelMap.put("count", pec.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty probleList");
		}
		return modelMap;
	}
	
	//上传的图片不超过6张
	private static final int IMAGEMAXCOUNT=6;
	
	//添加客户反馈问题模块
	@RequestMapping(value="/registerproblem",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerProblem(HttpServletRequest request) throws IOException{
		Map<String,Object> modelMap=new HashMap<>();
		/*获取前端传过来的json字符串*/
		String problemStr=HttpServletRequestUtil.getString(request, "problemStr");
		String userAccountNumber=HttpServletRequestUtil.getString(request, "userAccountNumber");
		if(userAccountNumber==null) {
			modelMap.put("success", false);
			return modelMap;
		}
		PhoneUserExecution pue=phoneUserService.getPhoneUser(userAccountNumber);
		if(pue.getState()==PhoneUserStateEnum.FAILURE.getState()||
				pue.getState()==PhoneUserStateEnum.NULL_PHONEUSERID.getState()) {
			modelMap.put("success", false);
			return modelMap;
		}
		/*将json字符串转换成对象*/
		ObjectMapper mapper=new ObjectMapper();
		Problem problem=null;
		try {
			problem=mapper.readValue(problemStr, Problem.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//处理图片
		CommonsMultipartFile problemImg=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		List<ImageHolder> problemImgList=new ArrayList<>();
		try {
			if(commonsMultipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartHttpServletRequest=
						(MultipartHttpServletRequest)request;
				for(int i=0;i<PhoneController.IMAGEMAXCOUNT;i++) {
					CommonsMultipartFile problemImgFile=(CommonsMultipartFile)multipartHttpServletRequest.getFile("thumbnail"+i);
					if(problemImgFile!=null) {
						//第i个详情图片流不为空，则将其加入详情图列表
						ImageHolder productImg=new ImageHolder(problemImgFile.getOriginalFilename(),problemImgFile.getInputStream());
						problemImgList.add(productImg);
					}else {
						break;//终止循环
					}
					logger.debug("图片数量:"+problemImgList.size());
					logger.debug("图片名称:"+problemImgList.get(0).getImageName());
					//logger.debug("图片大小"+problemImgList.get(0).getImage());
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg","上传图片不能为空");
				return modelMap;
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.getMessage());
			return modelMap;
		}
		//不为空，有图片，则执行添加操作
		if(problem!=null&&problemImgList!=null&&problemImgList.size()>0) {
			//目前用手动设置代替
			/*
			 * PhoneUser pu=new PhoneUser(); pu.setUserId(1);
			 */
			problem.setUser(pue.getPhoneUser());
			ProblemExecution pec=problemService.addProblem(problem, problemImgList);
			if(pec.getState()==ProblemStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
			}	
		}
		return modelMap;
	}
	
	//获取充电桩
	@RequestMapping(value="/getcoordinatelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getCoordinateList(){
		Map<String,Object> modelMap=new HashMap<>();
		List<Coordinate> coordinateList=coordinateService.getAllCoordinateList();
		if(coordinateList!=null&&coordinateList.size()>0) {
			modelMap.put("success", true);
			modelMap.put("coordinateList", coordinateList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty coordinateList");
		}
		return modelMap;
	}
	
	//获取轮播图
	@RequestMapping(value="/getnoticelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getNoticeList(){
		Map<String,Object> modelMap=new HashMap<>();
		NoticeExecution ne=noticeService.getQueryNoticeEnable();
		
		if(ne.getState()==NoticeStateEnum.SUCCESS.getState()) {
			modelMap.put("noticeList", ne.getNoticeList());
			modelMap.put("success", true);
		}else {
			modelMap.put("errMsg", "empty noticeList");
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
