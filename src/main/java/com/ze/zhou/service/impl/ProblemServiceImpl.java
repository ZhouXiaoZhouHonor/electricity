package com.ze.zhou.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.ProblemDao;
import com.ze.zhou.dao.ProblemImgDao;
import com.ze.zhou.dto.ProblemExecution;
import com.ze.zhou.dto.ProblemImgExecution;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.entity.ProblemImg;
import com.ze.zhou.enums.ProblemImgStateEnum;
import com.ze.zhou.enums.ProblemStateEnum;
import com.ze.zhou.service.ProblemService;
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.util.ImageSize;
import com.ze.zhou.util.ImageUtil;
import com.ze.zhou.util.PageCalculator;
import com.ze.zhou.util.PathUtil;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
@Service
public class ProblemServiceImpl implements ProblemService{
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ProblemImgDao problemImgDao;
	
	@Override
	public ProblemExecution getQueryProblem(Problem problem,
			int pageIndex,int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Problem> problemList=problemDao.queryProblemByUser(problem,
				rowIndex,pageSize);
		int count=problemDao.queryProblemByUserCount(problem);
		ProblemExecution pec=new ProblemExecution();
		if(problemList!=null&&problemList.size()>0&&count>0) {
			pec.setState(ProblemStateEnum.SUCCESS.getState());
			pec.setProblemList(problemList);
			pec.setCount(count);
		}else {
			pec.setState(ProblemStateEnum.NULL_PROBLEM.getState());
		}
		return pec;
	}
	
	@Override
	public ProblemExecution addProblem(Problem problem,List<ImageHolder> problemImgList) {
		ProblemExecution peu=new ProblemExecution();
		/*1、将problem插入；
		 *2、若第一步成功，则将图片进行插入
		 **/
		if(problem!=null&&problem.getUser()!=null) {
			problem.setCreateTime(new Date());
			problem.setLastEditTime(new Date());
			problem.setProblemEnableStatus(0);
			problem.setProblemTitle("问题");
			int effectNum=problemDao.insertProblem(problem);
			if(effectNum>0) {
				peu.setState(ProblemStateEnum.SUCCESS.getState());
				//TODO 添加图片，使用批量插入的方法
				/*
				 * 1、将List<ImageHolder> problemImgList真正的数据存储起来
				 * 2、执行添加操作*/
				//详情图不为空
				ProblemImgExecution pie=new ProblemImgExecution();
				if(problemImgList!=null&&problemImgList.size()>0) {
					pie=addProblemImgList(problem,problemImgList);
				}
				if(pie.getState()!=ProblemImgStateEnum.SUCCESS.getState()) {//插入详情图成功
					peu.setState(ProblemStateEnum.INNER_ERROR.getState());
					peu.setStateInfo("insert problemImg failure");
				}
			}else {
				peu.setState(ProblemStateEnum.INNER_ERROR.getState());
				peu.setStateInfo("insert failure");
			}
		}else {
			peu.setState(ProblemStateEnum.NULL_PROBLEM.getState());
			peu.setStateInfo("empty problem");
		}
		return peu;
	}
	
	//添加图片集合
	private ProblemImgExecution addProblemImgList(Problem problem,
			List<ImageHolder> problemImgList) {
		//获取图片存储路径
		String dest=PathUtil.getProblemImagePath(problem.getProblemId());
		List<ProblemImg> imgList=new ArrayList<>();
		int priority=1;
		for(ImageHolder imageholder:problemImgList) {
			String imgAddr=ImageUtil.generateThumbnail(imageholder, dest,ImageSize.IMAGE_PROBLEM);
			ProblemImg problemImg=new ProblemImg();
			problemImg.setProblemImgLink(imgAddr);
			problemImg.setProblem(problem);
			problemImg.setProblemImgPriority(priority++);
			imgList.add(problemImg);
		}
		//执行添加操作
		ProblemImgExecution pie=new ProblemImgExecution();
		if(imgList!=null&&imgList.size()>0) {
			int effectNum=problemImgDao.insertProblemImg(imgList);
			if(effectNum<0) {//插入不成功
				pie.setState(ProblemImgStateEnum.INNER_ERROR.getState());
				pie.setStateInfo("插入问题反馈图片失败");
			}else {
				pie.setState(ProblemImgStateEnum.SUCCESS.getState());
			}
		}
		return pie;
	}
	
	@Override
	public ProblemExecution changeProblem(Problem problem) {
		ProblemExecution peu=new ProblemExecution();
		if(problem!=null) {
			problem.setLastEditTime(new Date());
			int effectNum=problemDao.insertProblem(problem);
			if(effectNum>0) {
				peu.setState(ProblemStateEnum.SUCCESS.getState());
			}else {
				peu.setState(ProblemStateEnum.INNER_ERROR.getState());
				peu.setStateInfo("update failure");
			}
		}else {
			peu.setState(ProblemStateEnum.NULL_PROBLEM.getState());
			peu.setStateInfo("empty problem");
		}
		return peu;
	}

}
