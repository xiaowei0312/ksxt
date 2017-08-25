package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.CollageMajor;
import com.sxsram.ssm.entity.CollageMajorQueryVo;

public interface CollageMajorMapper {
	public void insertNewCollageMajor(CollageMajor bsTitle);

	public void delCollageMajor(Integer id);

	public void updateCollageMajor(CollageMajor bsTitle);

	public List<CollageMajor> queryMultiCollageMajors(CollageMajorQueryVo bsTitleQueryVo);

	public CollageMajor querySingleCollageMajor(CollageMajorQueryVo bsTitleQueryVo);
}