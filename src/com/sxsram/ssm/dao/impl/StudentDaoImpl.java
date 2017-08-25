package com.sxsram.ssm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoDbUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sxsram.ssm.dao.StudentDao;
import com.sxsram.ssm.entity.ExcelStudent;
import com.sxsram.ssm.util.MongoUtil;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {

	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public void insert(ExcelStudent stu, String collectionName) {
		try {
			// mongoTemplate.insert(object, collectionName);
			// DBObject obj = new BasicDBObject();
			// obj.put("userName", stu.getName());
			// obj.put("password", stu.getPassword());
			mongoTemplate.insert(MongoUtil.bean2DBObject(stu), collectionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ExcelStudent findOne(Map<String, Object> params, String collectionName) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(params.get("id"))), ExcelStudent.class,
				collectionName);
	}

	@Override
	public List<ExcelStudent> findAll(Map<String, Object> params, String collectionName) {
		List<ExcelStudent> result = mongoTemplate.find(new Query(Criteria.where("age").lt(params.get("maxAge"))),
				ExcelStudent.class, collectionName);
		return result;
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {
		mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))),
				new Update().set("name", params.get("name")), ExcelStudent.class, collectionName);
	}

	@Override
	public void createCollection(String name) {
		mongoTemplate.createCollection(name);
	}

	@Override
	public void remove(Map<String, Object> params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))), ExcelStudent.class, collectionName);
	}
}
