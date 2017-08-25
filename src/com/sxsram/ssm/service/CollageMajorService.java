package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxsram.ssm.entity.CollageMajor;
import com.sxsram.ssm.entity.CollageMajorQueryVo;
import com.sxsram.ssm.mapper.CollageMajorMapper;

@Service("collageMajorService")
public class CollageMajorService {
	@Autowired
	private CollageMajorMapper collageMajorMapper;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false, timeout = 3)
	public void addNewCollageMajor(CollageMajor bsTitle) throws Exception {
		// if (bsTitle.getParent() != null) {
		// if (bsTitle.getParent().getParent() != null) {
		// collageMajorMapper.insertNewCollageMajor(bsTitle.getParent().getParent());
		// }
		// collageMajorMapper.insertNewCollageMajor(bsTitle.getParent());
		// }
		collageMajorMapper.insertNewCollageMajor(bsTitle);
	}

	public void delCollageMajor(CollageMajor bsTitle) throws Exception {
		collageMajorMapper.delCollageMajor(bsTitle.getId());
	}

	public void updateCollageMajor(CollageMajor bsTitle) throws Exception {
		collageMajorMapper.updateCollageMajor(bsTitle);
	}

	public List<CollageMajor> findCollageMajors(CollageMajorQueryVo bsTitleQueryVo) throws Exception {
		return collageMajorMapper.queryMultiCollageMajors(bsTitleQueryVo);
	}

	public CollageMajor findCollageMajor(CollageMajorQueryVo bsTitleQueryVo) throws Exception {
		return collageMajorMapper.querySingleCollageMajor(bsTitleQueryVo);
	}
}
