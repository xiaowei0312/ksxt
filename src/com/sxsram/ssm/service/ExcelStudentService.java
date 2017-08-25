package com.sxsram.ssm.service;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxsram.ssm.dao.StudentDao;
import com.sxsram.ssm.entity.ExcelStudent;
import com.sxsram.ssm.util.MD5Util;

@Service("excelStudentService")
public class ExcelStudentService {
	@Autowired
	private StudentDao studentDao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void batchAddStudent(List<List<Object>> listob) throws Exception {
		for (int i = 0; i < listob.size(); i++) {
			List<Object> lo = listob.get(i);
			if (String.valueOf(lo.get(0)).startsWith("##")) {
				continue;
			}

			ExcelStudent vo = new ExcelStudent(String.valueOf(lo.get(1)),
					"",
					String.valueOf(""),
					String.valueOf(lo.get(0)));
			vo.setStudentPwd(MD5Util.MD5Encode(String.valueOf(lo.get(3)), null));
			studentDao.insert(vo, "ligongda");
		}
	}
}