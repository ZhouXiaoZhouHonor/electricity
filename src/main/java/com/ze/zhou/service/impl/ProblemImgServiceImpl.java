package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.ProblemImgDao;
import com.ze.zhou.dto.ProblemImgExecution;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.entity.ProblemImg;
import com.ze.zhou.enums.ProblemImgStateEnum;
import com.ze.zhou.enums.ProblemStateEnum;
import com.ze.zhou.service.ProblemImgService;

/*
	author:zhouze
	@createTime:2020年6月8日
	@goal:
*/
@Service
public class ProblemImgServiceImpl implements ProblemImgService{

	@Autowired
	private ProblemImgDao problemImgDao;
	
	//获取problem对应的图片
	@Override
	public ProblemImgExecution getProblemImgByProblem(Problem problem) {
		ProblemImgExecution pie=new ProblemImgExecution();
		if(problem!=null&&problem.getProblemId()>0) {
			List<ProblemImg> problemImgList=problemImgDao.queryProblemImgByProblem(problem);
			if(problemImgList!=null&&problemImgList.size()>0) {
				pie.setProblemImgList(problemImgList);
				pie.setState(ProblemImgStateEnum.SUCCESS.getState());
			}else {
				pie.setState(ProblemImgStateEnum.NULL_PROBLEMImg.getState());
			}
		}else {
			pie.setState(ProblemStateEnum.NULL_PROBLEM.getState());
		}
		return pie;
	}
	
}
