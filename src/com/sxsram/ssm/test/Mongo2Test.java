package com.sxsram.ssm.test;

import java.util.Set;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class Mongo2Test {
	@Test
	public void testMongodb() {
		try {
			// 连接到 mongodb 服务
			Mongo mongo = new Mongo("127.0.0.1", 27017);
			// 根据mongodb数据库的名称获取mongodb对象 ,
			DB db = mongo.getDB("bsxt");
			Set<String> collectionNames = db.getCollectionNames();
			// 打印出test中的集合
			for (String name : collectionNames) {
				System.out.println("collectionName===" + name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
