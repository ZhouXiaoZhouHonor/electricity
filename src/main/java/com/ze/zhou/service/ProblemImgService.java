package com.ze.zhou.service;

import com.ze.zhou.dto.ProblemImgExecution;
import com.ze.zhou.entity.Problem;

/*
	author:zhouze
	@createTime:2020年6月8日
	@goal:
*/
public interface ProblemImgService {
	ProblemImgExecution getProblemImgByProblem(Problem problem);
}
