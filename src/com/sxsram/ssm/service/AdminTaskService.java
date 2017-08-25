package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxsram.ssm.entity.BsCommitFormat;
import com.sxsram.ssm.entity.BsTaskAdmin;
import com.sxsram.ssm.entity.BsTaskAdminQueryVo;
import com.sxsram.ssm.entity.BsTaskStu;
import com.sxsram.ssm.entity.Student;
import com.sxsram.ssm.mapper.BsCommitFormatMapper;
import com.sxsram.ssm.mapper.BsTaskAdminMapper;
import com.sxsram.ssm.util.StringUtil;

@Service("adminTaskService")
public class AdminTaskService {
	@Autowired
	private BsTaskAdminMapper bsTaskAdminMapper;
	@Autowired
	private BsCommitFormatMapper bsCommitFormatMapper;
	@Autowired
	private StudentTaskService bsTaskStuService;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void addNewBsTaskAdmin(BsTaskAdmin bsTaskAdmin, List<Student> students) throws Exception {
		if (bsTaskAdmin.getBsCommitFormat() != null
				&& !StringUtil.isEmpty(bsTaskAdmin.getBsCommitFormat().getFormat())) {
			bsCommitFormatMapper.insertNewBsCommitFormat(bsTaskAdmin.getBsCommitFormat());
			// bsTaskAdmin.setBsFormatId(bsCommitFormat.getId());
		}
		bsTaskAdminMapper.insertNewBsTaskAdmin(bsTaskAdmin);
		for (Student stu : students) {
			BsTaskStu bsTaskStu = new BsTaskStu();
			bsTaskStu.setStudentId(stu.getId());
			bsTaskStu.setTaskId(bsTaskAdmin.getId());
			bsTaskStu.setStatus(-1);
			bsTaskStu.setStatusPhase("未完成");
			bsTaskStuService.insertNewBsTaskStu(bsTaskStu);
		}
	}

	public void delBsTaskAdmin(BsTaskAdmin bsTaskAdmin) throws Exception {
		bsTaskAdminMapper.deleteBsTaskAdmin(bsTaskAdmin.getId());
	}

	public void updateBsTaskAdmin(BsTaskAdmin bsTaskAdmin) throws Exception {
		if (bsTaskAdmin.getBsCommitFormat() != null) {
			if (bsTaskAdmin.getBsCommitFormat().getId() != null) {
				bsCommitFormatMapper.updateBsCommitFormat(bsTaskAdmin.getBsCommitFormat());
			} else {
				bsCommitFormatMapper.insertNewBsCommitFormat(bsTaskAdmin.getBsCommitFormat());
			}
		}
		bsTaskAdminMapper.updateBsTaskAdmin(bsTaskAdmin);
	}

	public BsTaskAdmin findBsTaskAdmin(BsTaskAdminQueryVo bsTaskStuQueryVo) throws Exception {
		return bsTaskAdminMapper.querySingleBsTask(bsTaskStuQueryVo);
	}

	public List<BsTaskAdmin> findBsTaskAdmins(BsTaskAdminQueryVo bsTaskStuQueryVo) throws Exception {
		return bsTaskAdminMapper.queryMultiBsTasks(bsTaskStuQueryVo);
	}
}