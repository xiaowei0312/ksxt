package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.BsDbType;
import com.sxsram.ssm.entity.BsDbTypeQueryVo;
import com.sxsram.ssm.mapper.BsDbTypeMapper;

@Service("bsDbTypeService")
public class BsDbTypeService {
	@Autowired
	private BsDbTypeMapper bsDbTypeMapper;

	public void addNewBsDbType(BsDbType dbType) throws Exception {
		bsDbTypeMapper.insertNewBsDbType(dbType);
	}

	public void delBsDbType(BsDbType dbType) throws Exception {
		bsDbTypeMapper.deleteBsDbType(dbType.getId());
	}

	public void updateBsDbType(BsDbType dbType) throws Exception {
		bsDbTypeMapper.updateBsDbType(dbType);
	}

	public List<BsDbType> findBsDbTypes(BsDbTypeQueryVo dbTypeQueryVo) throws Exception {
		return bsDbTypeMapper.queryMultiBsDbTypes(dbTypeQueryVo);
	}

	public BsDbType findBsDbType(BsDbTypeQueryVo dbTypeQueryVo) throws Exception {
		return bsDbTypeMapper.querySingleBsDbType(dbTypeQueryVo);
	}
}
