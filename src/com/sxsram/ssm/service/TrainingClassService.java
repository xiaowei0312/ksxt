package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.TrainingClass;
import com.sxsram.ssm.entity.TrainingClassQueryVo;
import com.sxsram.ssm.mapper.TrainingClassMapper;

@Service("trainingClassService")
public class TrainingClassService {
	@Autowired
	private TrainingClassMapper traingingClassMapper;

	public void addNewTrainingClass(TrainingClass bsTitle) throws Exception {
		traingingClassMapper.insertNewTrainingClass(bsTitle);
	}

	public void delTrainingClass(TrainingClass bsTitle) throws Exception {
		traingingClassMapper.delTrainingClass(bsTitle.getId());
	}

	public void updateTrainingClass(TrainingClass bsTitle) throws Exception {
		traingingClassMapper.updateTrainingClass(bsTitle);
	}

	public List<TrainingClass> findTrainingClasss(TrainingClassQueryVo bsTitleQueryVo) throws Exception {
		return traingingClassMapper.queryMultiTrainingClasss(bsTitleQueryVo);
	}

	public TrainingClass findTrainingClass(TrainingClassQueryVo bsTitleQueryVo) throws Exception {
		return traingingClassMapper.querySingleTrainingClass(bsTitleQueryVo);
	}
}
