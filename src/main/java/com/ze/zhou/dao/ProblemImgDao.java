package com.ze.zhou.dao;

import java.util.List;

import com.ze.zhou.entity.ProblemImg;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public interface ProblemImgDao {
	//获取img
	List<ProblemImg> queryProblemImgByProblem(ProblemImg problemImg);
	//批量添加img
	int insertProblemImg(List<ProblemImg> problemImg);
}
