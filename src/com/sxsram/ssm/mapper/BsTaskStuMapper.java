package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsTaskStu;
import com.sxsram.ssm.entity.BsTaskStuQueryVo;

public interface BsTaskStuMapper {
	
	public void insertNewBsTaskStu(BsTaskStu bsTaskStu)throws Exception;
	
	public void deleteBsTaskStu(Integer id) throws Exception;
	
	public void updateBsTaskStu(BsTaskStu bsTaskStu)throws Exception;
	
	public BsTaskStu querySingleBsTask(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception;

	public List<BsTaskStu> queryMultiBsTask(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception;

	public Integer queryMultiBsTaskCount(BsTaskStuQueryVo bsTaskStuQueryVo) throws Exception;
}