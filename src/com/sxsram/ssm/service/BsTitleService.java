package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.BsTitleQueryVo;
import com.sxsram.ssm.mapper.BsTitleMapper;

@Service("bsTitleService")
public class BsTitleService {
	@Autowired
	private BsTitleMapper bsTitleMapper;

	public void addNewBsTitle(BsTitle bsTitle) throws Exception {
		bsTitleMapper.insertNewBsTitle(bsTitle);
	}

	public void delBsTitle(BsTitle bsTitle) throws Exception {
		bsTitleMapper.delBsTitle(bsTitle.getId());
	}

	public void updateBsTitle(BsTitle bsTitle) throws Exception {
		bsTitleMapper.updateBsTitle(bsTitle);
	}

	public List<BsTitle> findBsTitles(BsTitleQueryVo bsTitleQueryVo) throws Exception {
		return bsTitleMapper.queryMultiBsTitles(bsTitleQueryVo);
	}

	public BsTitle findBsTitle(BsTitleQueryVo bsTitleQueryVo) throws Exception {
		return bsTitleMapper.querySingleBsTitle(bsTitleQueryVo);
	}
}
