package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.BsTaskStu;
import com.sxsram.ssm.entity.BsTaskStuQueryVo;
import com.sxsram.ssm.mapper.BsTaskStuMapper;

@Service("studentTaskService")
public class StudentTaskService {
	@Autowired
	private BsTaskStuMapper bsTaskStuMapper;

	public BsTaskStu findBsTaskStu(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception {
		return bsTaskStuMapper.querySingleBsTask(bsTaskStuQueryVo);
	}

	public List<BsTaskStu> findBsTaskStus(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception {
		return bsTaskStuMapper.queryMultiBsTask(bsTaskStuQueryVo);
	}

	public Integer findBsTaskStusCount(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception {
		return bsTaskStuMapper.queryMultiBsTaskCount(bsTaskStuQueryVo);
	}

	public void insertNewBsTaskStu(BsTaskStu bsTaskStu) throws Exception {
		bsTaskStuMapper.insertNewBsTaskStu(bsTaskStu);
	}

	public void updateNewBsTaskStu(BsTaskStu bsTaskStu) throws Exception {
		bsTaskStuMapper.updateBsTaskStu(bsTaskStu);
	}

	public void deleteBsTaskStu(BsTaskStu bsTaskStu) throws Exception {
		bsTaskStuMapper.deleteBsTaskStu(bsTaskStu.getId());
	}
}