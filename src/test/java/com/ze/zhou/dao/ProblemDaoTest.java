package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.entity.Problem;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class ProblemDaoTest extends BaseTest{
	@Autowired
	private ProblemDao problemDao;
	
	@Test
	@Ignore
	public void getQueryProblem() {
		List<Problem> list=problemDao.queryProblem(0, 20);
		System.out.println(list.size());
		Problem problem=new Problem();
		int count=problemDao.queryProblemByUserCount(problem);
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void updateProblemTest() {
		Problem problem=new Problem();
		problem.setProblemId(3);
		problem.setLastEditTime(new Date());
		problem.setProblemEnableStatus(0);
		problem.setProblemSalve("问题出现在这个地方");
		int effectNum=problemDao.updateProblem(problem);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void insertProblemTest() {
		PhoneUser phoneUser=new PhoneUser();
		/*phoneUser.setCreateTime(new Date());
		phoneUser.setLastEditTime(new Date());
		phoneUser.setUserAccountNumber("15256038842");
		phoneUser.setUserAccountPassword("123456");
		phoneUser.setUserName("用户1");*/
		phoneUser.setUserId(1);
		Problem problem=new Problem();
		problem.setCreateTime(new Date());
		problem.setLastEditTime(new Date());
		problem.setProblemDesc("问题2-1");
		problem.setProblemEnableStatus(1);
		problem.setProblemTitle("问题2-1");
		problem.setUser(phoneUser);
		int effectNum=problemDao.insertProblem(problem);
		System.out.println(effectNum);
		System.out.println(problem.getProblemId());
	}
	
	@Test
	@Ignore
	public void queryProblemByUserTest() {
		PhoneUser phoneUser=new PhoneUser();
		phoneUser.setUserId(1);
		Problem problem=new Problem();
		problem.setUser(phoneUser);
		List<Problem> problemList=problemDao.queryProblemByUser(problem,0,5);
		System.out.println(problemList.get(0).getLastEditTime());
		System.out.println(problemList.get(0).getProblemDesc());
	}
}
