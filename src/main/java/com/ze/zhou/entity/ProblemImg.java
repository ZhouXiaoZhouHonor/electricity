package com.ze.zhou.entity;
/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class ProblemImg {
	private Integer problemImgId;
	private String problemImgLink;
	private Integer problemImgPriority;
	private Problem problem;
	public Integer getProblemImgId() {
		return problemImgId;
	}
	public void setProblemImgId(Integer problemImgId) {
		this.problemImgId = problemImgId;
	}
	public String getProblemImgLink() {
		return problemImgLink;
	}
	public void setProblemImgLink(String problemImgLink) {
		this.problemImgLink = problemImgLink;
	}
	public Integer getProblemImgPriority() {
		return problemImgPriority;
	}
	public void setProblemImgPriority(Integer problemImgPriority) {
		this.problemImgPriority = problemImgPriority;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
}
