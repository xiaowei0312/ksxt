package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.BsCommitFormat;
import com.sxsram.ssm.entity.BsCommitFormatQueryVo;
import com.sxsram.ssm.mapper.BsCommitFormatMapper;
import com.sxsram.ssm.mapper.BsCommitFormatMapper;

@Service("bsCommitFormatService")
public class BsCommitFormatService {
	@Autowired
	private BsCommitFormatMapper bsCommitFormatMapper;

	public void addNewBsCommitFormat(BsCommitFormat bsTitle) throws Exception {
		bsCommitFormatMapper.insertNewBsCommitFormat(bsTitle);
	}

	public void delBsCommitFormat(BsCommitFormat bsTitle) throws Exception {
		bsCommitFormatMapper.deleteBsCommitFormat(bsTitle.getId());
	}

	public void updateBsCommitFormat(BsCommitFormat bsTitle) throws Exception {
		bsCommitFormatMapper.updateBsCommitFormat(bsTitle);
	}

	public List<BsCommitFormat> findBsCommitFormats(BsCommitFormatQueryVo bsTitleQueryVo) throws Exception {
		return bsCommitFormatMapper.queryMultiBsCommitFormats(bsTitleQueryVo);
	}

	public BsCommitFormat findBsCommitFormat(BsCommitFormatQueryVo bsTitleQueryVo) throws Exception {
		return bsCommitFormatMapper.querySingleBsCommitFormat(bsTitleQueryVo);
	}
}
