/**
 * 
 */
package com.huayu.core.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

/**
 * 对mongo的基本操作
 * 
 * @author luozehua
 * @date 2017年9月15日 - 上午11:35:12
 * 
 */
public class MongoUtils {

	public MongoUtils() {

	}

	// ------------------------------------共用方法---------------------------------------------------
	/**
	 * 获取DB实例 - 指定DB
	 * 
	 * @param dbName
	 * @return
	 */
	public static MongoDatabase getDB(MongoClient mongoClient, String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			return database;
		}
		return null;
	}

	/**
	 * 获取collection对象 - 指定Collection
	 * 
	 * @param collName
	 * @return
	 */
	public static MongoCollection<Document> getCollection(MongoClient mongoClient, String dbName, String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
		return collection;
	}

	/**
	 * 查询DB下的所有表名
	 */
	public static List<String> getAllCollections(MongoClient mongoClient, String dbName) {
		MongoIterable<String> colls = getDB(mongoClient, dbName).listCollectionNames();
		List<String> _list = new ArrayList<String>();
		for (String s : colls) {
			_list.add(s);
		}
		return _list;
	}

	/**
	 * 获取所有数据库名称列表
	 * 
	 * @return
	 */
	public static MongoIterable<String> getAllDBNames(MongoClient mongoClient) {
		MongoIterable<String> s = mongoClient.listDatabaseNames();
		return s;
	}

	/**
	 * 删除一个数据库
	 */
	public static void dropDB(MongoClient mongoClient, String dbName) {
		getDB(mongoClient, dbName).drop();
	}

	/**
	 * 查找对象 - 根据主键_id
	 * 
	 * @param collection
	 * @param id
	 * @return
	 */
	public static Document findById(MongoCollection<Document> coll, String id) {
		ObjectId _idobj = null;
		try {
			_idobj = new ObjectId(id);
		} catch (Exception e) {
			return null;
		}
		Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
		return myDoc;
	}

	/** 统计数 */
	public static int getCount(MongoCollection<Document> coll) {
		int count = (int) coll.count();
		return count;
	}

	public static int getCountByCondition(MongoCollection<Document> coll, Bson filter) {
		int count = (int) coll.count(filter);
		return count;
	}

	/** 条件查询 */
	public static MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
		return coll.find(filter).iterator();
	}

	/** 分页查询 */
	public static MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo,
			int pageSize) {
		Bson orderBy = new BasicDBObject("_id", 1);
		return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
	}

	/**
	 * 通过ID删除
	 * 
	 * @param coll
	 * @param id
	 * @return
	 */
	public static int deleteById(MongoCollection<Document> coll, Long id) {
		int count = 0;
		Bson filter = Filters.eq("_id", id);
		DeleteResult deleteResult = coll.deleteOne(filter);
		count = (int) deleteResult.getDeletedCount();
		return count;
	}

	/**
	 * @param coll
	 * @param id
	 * @param newdoc
	 * @return
	 */
	public static Document updateById(MongoCollection<Document> coll, Long id, Document newdoc) {
		Bson filter = Filters.eq("_id", id);
		// coll.replaceOne(filter, newdoc); // 完全替代
		coll.updateOne(filter, new Document("$set", newdoc));
		return newdoc;
	}

	public static void dropCollection(MongoClient mongoClient, String dbName, String collName) {
		getDB(mongoClient, dbName).getCollection(collName).drop();
	}

	/**
	 * 关闭Mongodb
	 */
	public static void close(MongoClient mongoClient) {
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
	}

	public static void insertOne(MongoCollection<Document> coll, Document doc) {
		coll.insertOne(doc);
	}

	/**
	 * 测试入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// String dbName = "TPLAT_DB";
		// String collName = "IETL_VIDEO_RECODE_C";
		// MongoCollection<Document> coll = MongoUtils.instance.getCollection(dbName,
		// collName);
		//
		// // // 插入多条
		// for (int i = 1; i <= 4; i++) {
		// Document doc = new Document();
		// doc.put("user_code", "amdin" + i);
		// doc.put("res_code", "RES" + i);
		// doc.put("paly_time", "zhoulf");
		// doc.put("create_time", new Date());
		// coll.insertOne(doc);
		// }

		// 根据ID查询
		// String id = "59bb8dd7ec259e6ffce72b8f";
		// Document doc = MongoUtils.instance.findById(coll, id);
		// System.out.println(doc);

		// 查询多个
		// MongoCursor<Document> cursor1 = coll.find(Filters.eq("paly_time",
		// "zhoulf")).iterator();
		// while (cursor1.hasNext()) {
		// org.bson.Document _doc = (Document) cursor1.next();
		// System.out.println(_doc.toString());
		// }
		// cursor1.close();

		// 查询多个
		// MongoCursor<Person> cursor2 = coll.find(Person.class).iterator();

		// 删除数据库
		// MongoDBUtil2.instance.dropDB("testdb");

		// 删除表
		// MongoDBUtil2.instance.dropCollection(dbName, collName);

		// 修改数据
		// String id = "556949504711371c60601b5a";
		// Document newdoc = new Document();
		// newdoc.put("name", "时候");
		// MongoDBUtil.instance.updateById(coll, id, newdoc);

		// 统计表
		// System.out.println(MongoDBUtil.instance.getCount(coll));

		// 查询所有
		// Bson filter = Filters.eq("count", 0);
		// MongoCursor<Document> temp = MongoUtils.instance.find(coll, filter);
	}

}
