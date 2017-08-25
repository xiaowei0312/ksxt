package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.BsTaskCommitRecord;
import com.sxsram.ssm.entity.BsTaskCommitRecordQueryVo;

public interface BsTaskCommitRecordMapper {

	public void insertNewBsTaskCommitRecord(BsTaskCommitRecord bsTaskCommitRecord) throws Exception;

	public void deleteBsTaskCommitRecord(Integer id) throws Exception;

	public void updateBsTaskCommitRecord(BsTaskCommitRecord bsTaskCommitRecord) throws Exception;

	public BsTaskCommitRecord querySingleBsTaskCommitRecord(BsTaskCommitRecordQueryVo bsTaskStuQueryVo)
			throws Exception;

	public List<BsTaskCommitRecord> queryMultiBsTaskCommitRecords(BsTaskCommitRecordQueryVo bsTaskStuQueryVo)
			throws Exception;
}