package com.ze.zhou.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Problem;
import com.ze.zhou.entity.ProblemImg;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class ProblemImgDaoTest extends BaseTest{
	@Autowired
	private ProblemImgDao problemImgDao;
	
	@Test
	@Ignore
	public void insertProblemTest() {
		ProblemImg problemImg=new ProblemImg();
		problemImg.setProblemImgLink("qwe");
		problemImg.setProblemImgPriority(1);
		Problem problem=new Problem();
		problem.setProblemId(3);
		problemImg.setProblem(problem);
		int effectNum=problemImgDao.insertProblemImg(problemImg);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void queryProblemImgByProblemTest() {
		ProblemImg problemImg=new ProblemImg();
		Problem problem=new Problem();
		problem.setProblemId(1);
		problemImg.setProblem(problem);
		List<ProblemImg> problemImgList=problemImgDao.queryProblemImgByProblem(problemImg);
		System.out.println(problemImgList.size());
	}
}
