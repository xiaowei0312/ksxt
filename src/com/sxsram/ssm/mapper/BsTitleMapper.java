package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsTitle;
import com.sxsram.ssm.entity.BsTitleQueryVo;

public interface BsTitleMapper {
	public void insertNewBsTitle(BsTitle bsTitle);

	public void delBsTitle(Integer id);

	public void updateBsTitle(BsTitle bsTitle);

	public List<BsTitle> queryMultiBsTitles(BsTitleQueryVo bsTitleQueryVo);

	public BsTitle querySingleBsTitle(BsTitleQueryVo bsTitleQueryVo);
}