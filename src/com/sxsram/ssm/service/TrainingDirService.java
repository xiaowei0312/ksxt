package com.sxsram.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxsram.ssm.entity.TrainingDir;
import com.sxsram.ssm.entity.TrainingDirQueryVo;
import com.sxsram.ssm.mapper.TrainingDirMapper;

@Service("trainingDirService")
public class TrainingDirService {
	@Autowired
	private TrainingDirMapper trainingDirMapper;

	public void addNewTrainingDir(TrainingDir bsTitle) throws Exception {
		trainingDirMapper.insertNewTrainingDir(bsTitle);
	}

	public void delTrainingDir(TrainingDir bsTitle) throws Exception {
		trainingDirMapper.delTrainingDir(bsTitle.getId());
	}

	public void updateTrainingDir(TrainingDir bsTitle) throws Exception {
		trainingDirMapper.updateTrainingDir(bsTitle);
	}

	public List<TrainingDir> findTrainingDirs(TrainingDirQueryVo bsTitleQueryVo) throws Exception {
		return trainingDirMapper.queryMultiTrainingDirs(bsTitleQueryVo);
	}

	public TrainingDir findTrainingDir(TrainingDirQueryVo bsTitleQueryVo) throws Exception {
		return trainingDirMapper.querySingleTrainingDir(bsTitleQueryVo);
	}

	public void deleteTraininggDirCascadeTrainingClasses(TrainingDir trainingDir) {
		
	}
}
