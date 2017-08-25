package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxsram.ssm.entity.BsTaskCommitRecord;
import com.sxsram.ssm.entity.BsTaskCommitRecordQueryVo;
import com.sxsram.ssm.entity.BsTaskStu;
import com.sxsram.ssm.mapper.BsTaskCommitRecordMapper;
import com.sxsram.ssm.mapper.BsTaskStuMapper;

@Service("commitRecordService")
public class CommitRecordService {
	@Autowired
	private BsTaskCommitRecordMapper bsTaskCommitRecordMapper;
	@Autowired
	private BsTaskStuMapper bsTaskStuMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void addNewCommitRecord(BsTaskCommitRecord bsTaskCommitRecord) throws Exception {
		bsTaskCommitRecordMapper.insertNewBsTaskCommitRecord(bsTaskCommitRecord);

		BsTaskStu bsTaskStu = new BsTaskStu();
		bsTaskStu.setId(bsTaskCommitRecord.getStuTaskId());
		switch (bsTaskCommitRecord.getStatus()) {
		case -1:
			bsTaskStu.setStatus(-1);
			bsTaskStu.setStatusPhase("未完成");
			break;
		case 0:
			bsTaskStu.setStatus(0);
			bsTaskStu.setStatusPhase("已完成");
			break;
		case 1:
			bsTaskStu.setStatus(1);
			bsTaskStu.setStatusPhase("已完成,逾期");
			break;
		}
		bsTaskStuMapper.updateBsTaskStu(bsTaskStu);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void updateCommitRecord(BsTaskCommitRecord bsTaskCommitRecord) throws Exception {
		bsTaskCommitRecordMapper.updateBsTaskCommitRecord(bsTaskCommitRecord);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void deleteCommitRecord(BsTaskCommitRecord bsTaskCommitRecord) throws Exception {
		bsTaskCommitRecordMapper.deleteBsTaskCommitRecord(bsTaskCommitRecord.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public List<BsTaskCommitRecord> findCommitRecords(BsTaskCommitRecordQueryVo bsTaskCommitRecordQueryVo)
			throws Exception {
		return bsTaskCommitRecordMapper.queryMultiBsTaskCommitRecords(bsTaskCommitRecordQueryVo);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public BsTaskCommitRecord findCommitRecord(BsTaskCommitRecordQueryVo bsTaskCommitRecordQueryVo) throws Exception {
		return bsTaskCommitRecordMapper.querySingleBsTaskCommitRecord(bsTaskCommitRecordQueryVo);
	}
}
