package com.sxsram.ssm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.sxsram.ssm.dao.TitleDao;
import com.sxsram.ssm.entity.ExcelTitle;
import com.sxsram.ssm.util.MongoUtil;

@Repository("titleDao")
public class TitleDaoImpl implements TitleDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public void insert(ExcelTitle object, String collectionName) {
		try {
			mongoTemplate.insert(MongoUtil.bean2DBObject(object), collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ExcelTitle findOne(Map<String, Object> params, String collectionName) {
		return null;
	}

	@Override
	public List<ExcelTitle> findAll(Map<String, Object> params, String collectionName) {
		return null;
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {

	}

	@Override
	public void createCollection(String collectionName) {

	}

	@Override
	public void remove(Map<String, Object> params, String collectionName) {

	}
}
