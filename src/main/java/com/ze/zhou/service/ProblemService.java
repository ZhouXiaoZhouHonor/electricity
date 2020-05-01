package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.ProblemExecution;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.entity.ProblemImg;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public interface ProblemService {
	//获取问题
	List<Problem> getQueryProblem(Problem problem);
	//添加问题
	ProblemExecution addProblem(Problem problem,List<ProblemImg> problemImgList);
	//更改问题状态
	ProblemExecution changeProblem(Problem problem);
}
