package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.ProblemDao;
import com.ze.zhou.dto.ProblemExecution;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.enums.ProblemStateEnum;
import com.ze.zhou.service.ProblemService;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
@Service
public class ProblemServiceImpl implements ProblemService{
	@Autowired
	private ProblemDao problemDao;
	@Override
	public List<Problem> getQueryProblem(Problem problem) {
		return problemDao.queryProblemByUser(problem);
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
			int effectNum=problemDao.insertProblem(problem);
			if(effectNum>0) {
				peu.setState(ProblemStateEnum.SUCCESS.getState());
				//TODO 添加图片，使用批量插入的方法
				//ProblemImg pi=new ProblemImg();
				
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
