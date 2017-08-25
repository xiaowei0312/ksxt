package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsDbType;
import com.sxsram.ssm.entity.BsDbTypeQueryVo;

public interface BsDbTypeMapper {

	public void insertNewBsDbType(BsDbType bsDbType) throws Exception;

	public void deleteBsDbType(Integer id) throws Exception;

	public void updateBsDbType(BsDbType bsDbType) throws Exception;

	public BsDbType querySingleBsDbType(BsDbTypeQueryVo bsDbTypeQueryVo) throws Exception;

	public List<BsDbType> queryMultiBsDbTypes(BsDbTypeQueryVo bsDbTypeQueryVo) throws Exception;

}