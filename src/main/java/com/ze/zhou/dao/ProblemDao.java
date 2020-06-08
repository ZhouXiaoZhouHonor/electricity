package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Problem;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public interface ProblemDao {
	//获取对应user的problem,按照时间进行排序
	List<Problem> queryProblemByUser(@Param("problem")Problem problem,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	int queryProblemByUserCount(@Param("problem")Problem problem);
	//添加problem
	int insertProblem(Problem problem);
	
	//更新problem
	int updateProblem(Problem problem);
	
	//获取所有分页
	List<Problem> queryProblem(@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
}
