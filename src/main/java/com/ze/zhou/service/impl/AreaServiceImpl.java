package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.AreaDao;
import com.ze.zhou.entity.Area;
import com.ze.zhou.service.AreaService;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaDao areaDao;
	//返回所有区域信息
	@Override
	public List<Area> getQueryArea() {
		// TODO Auto-generated method stub
		return areaDao.queryArea();
	}

}
