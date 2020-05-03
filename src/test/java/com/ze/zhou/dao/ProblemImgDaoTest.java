package com.ze.zhou.dao;

import java.util.ArrayList;
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
		List<ProblemImg> list=new ArrayList<>();
		ProblemImg problemImg1=new ProblemImg();
		problemImg1.setProblemImgLink("qwe1");
		problemImg1.setProblemImgPriority(1);
		Problem problem=new Problem();
		problem.setProblemId(3);
		problemImg1.setProblem(problem);
		list.add(problemImg1);
		ProblemImg problemImg2=new ProblemImg();
		problemImg2.setProblemImgLink("qwe2");
		problemImg2.setProblemImgPriority(1);
		problemImg2.setProblem(problem);
		list.add(problemImg2);
		System.out.println(list.size());
		int effectNum=problemImgDao.insertProblemImg(list);
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
