package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsTaskAdmin;
import com.sxsram.ssm.entity.BsTaskAdminQueryVo;

public interface BsTaskAdminMapper {
	
	public void insertNewBsTaskAdmin(BsTaskAdmin bsTaskAdmin)throws Exception;
	
	public void deleteBsTaskAdmin(Integer id) throws Exception;
	
	public void updateBsTaskAdmin(BsTaskAdmin bsTaskAdmin)throws Exception;
	
	public BsTaskAdmin querySingleBsTask(BsTaskAdminQueryVo bsTaskAdminQueryVo) throws Exception;

	public List<BsTaskAdmin> queryMultiBsTasks(BsTaskAdminQueryVo bsTaskAdminQueryVo) throws Exception;

	public Integer queryMultiBsTaskCount(BsTaskAdminQueryVo bsTaskAdminQueryVo) throws Exception;
}