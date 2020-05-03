package com.ze.zhou.web.phoneadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ze.zhou.dto.ProblemExecution;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.enums.ProblemStateEnum;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.NoticeService;
import com.ze.zhou.service.PileService;
import com.ze.zhou.service.ProblemService;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.web.superadmin.AreaSuperadminController;

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

	//获取问题列表，根据userId
	@RequestMapping(value="/getproblemlist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProblemList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//TODO 从session中获取userId
		//先写死
		PhoneUser pu=new PhoneUser();
		pu.setUserId(1);
		//获取分页用的数值
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "Size");
		Problem problem=new Problem();
		problem.setUser(pu);
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
			/*TODO 从session中获取userId*/
			//目前用手动设置代替
			PhoneUser pu=new PhoneUser();
			pu.setUserId(1);
			problem.setUser(pu);
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
		List<Notice> noticeList=noticeService.getQueryNotice();
		if(noticeList.size()>0&&noticeList!=null) {
			modelMap.put("noticeList", noticeList);
			modelMap.put("success", true);
		}else {
			modelMap.put("errMsg", "empty noticeList");
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
