package com.sxsram.ssm.mapper;

import java.util.List;

import com.sxsram.ssm.entity.TrainingClass;
import com.sxsram.ssm.entity.TrainingClassQueryVo;

public interface TrainingClassMapper {
	public void insertNewTrainingClass(TrainingClass bsTitle);

	public void delTrainingClass(Integer id);

	public void updateTrainingClass(TrainingClass bsTitle);

	public List<TrainingClass> queryMultiTrainingClasss(TrainingClassQueryVo bsTitleQueryVo);

	public TrainingClass querySingleTrainingClass(TrainingClassQueryVo bsTitleQueryVo);
}