package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.TrainingDir;
import com.sxsram.ssm.entity.TrainingDirQueryVo;

public interface TrainingDirMapper {
	public void insertNewTrainingDir(TrainingDir bsTitle);

	public void delTrainingDir(Integer id);

	public void updateTrainingDir(TrainingDir bsTitle);

	public List<TrainingDir> queryMultiTrainingDirs(TrainingDirQueryVo bsTitleQueryVo);

	public TrainingDir querySingleTrainingDir(TrainingDirQueryVo bsTitleQueryVo);
}