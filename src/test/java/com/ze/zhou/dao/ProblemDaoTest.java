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
	public void updateProblemTest() {
		Problem problem=new Problem();
		problem.setProblemId(3);
		problem.setLastEditTime(new Date());
		problem.setProblemEnableStatus(0);
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
		problem.setProblemDesc("问题2");
		problem.setProblemEnableStatus(1);
		problem.setProblemTitle("问题2");
		problem.setUser(phoneUser);
		int effectNum=problemDao.insertProblem(problem);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void queryProblemByUserTest() {
		PhoneUser phoneUser=new PhoneUser();
		phoneUser.setUserId(1);
		Problem problem=new Problem();
		problem.setUser(phoneUser);
		List<Problem> problemList=problemDao.queryProblemByUser(problem);
		System.out.println(problemList.size());
	}
	
}
