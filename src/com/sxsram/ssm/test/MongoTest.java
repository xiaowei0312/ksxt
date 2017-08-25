package com.sxsram.ssm.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoTest {
	public static void main(String[] args) throws UnknownHostException {
		// 1.获取连接
		MongoClient mongoClient = new MongoClient("localhost", 27017);

		// 2.获取数据库实例
		MongoDatabase mongoDatabase = mongoClient.getDatabase("ksxt");
		System.out.println("Connect to database successfully");

		// 3.获取集合(表)
		MongoCollection<Document> collection = mongoDatabase.getCollection("ksxt.col1");
		System.out.println("集合 col1 选择成功");

		// 4. 插入文档
		Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100)
				.append("by", "Fly");
		List<Document> documents = new ArrayList<Document>();
		documents.add(document);
		collection.insertMany(documents);
		System.out.println("文档插入成功");

		// 5.检索所有文档
		// 检索所有文档
		/**
		 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
		 * 通过游标遍历检索出的文档集合
		 */
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			//System.out.println(mongoCursor.next());
			Document doc = mongoCursor.next();
			Set keys = doc.entrySet();
			for (Object key : keys) {
				System.out.println(key.toString());
			}
		}
	}
}
