package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsCommitFormat;
import com.sxsram.ssm.entity.BsCommitFormatQueryVo;

public interface BsCommitFormatMapper {

	public void insertNewBsCommitFormat(BsCommitFormat bsCommitFormat) throws Exception;

	public void deleteBsCommitFormat(Integer id) throws Exception;

	public void updateBsCommitFormat(BsCommitFormat bsCommitFormat) throws Exception;

	public BsCommitFormat querySingleBsCommitFormat(BsCommitFormatQueryVo bsCommitFormatQueryVo) throws Exception;

	public List<BsCommitFormat> queryMultiBsCommitFormats(BsCommitFormatQueryVo bsCommitFormatQueryVo) throws Exception;

}