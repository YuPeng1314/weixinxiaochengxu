package com.huayu.core.wordfilter;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.huayu.core.util.MongoUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

/**
 * 
 * @author Administrator
 *
 */
public class WordUtils {

	private static final String DBNAME = "TPLAT_DB";

	private static final String WORDCOL = "IETL_WORD_C";

	public static List<String> findWord(MongoClient mongoClient, String type) {
		MongoCollection<Document> coll = MongoUtils.getCollection(mongoClient, DBNAME, WORDCOL);
		List<BasicDBObject> condtionList = new ArrayList<>();
		BasicDBObject typeParam = new BasicDBObject("type", type);
		condtionList.add(typeParam);
		BasicDBObject condition = new BasicDBObject();
		condition.put("$and", condtionList);

		MongoCursor<Document> iterator = coll.find(condition).iterator();
		List<String> result = new ArrayList<>();
		while (iterator.hasNext()) {
			// 找出最高的播放时间
			Document docTmp = iterator.next();
			String wd = docTmp.get("content").toString();
			result.add(wd);
		}
		return result;
	}

//	public static void main(String[] args) {
//		MongoClient mongoClient = new MongoClient("119.23.228.148", 27017);
//
//		MongoCollection<Document> coll = MongoUtils.getCollection(mongoClient, DBNAME, WORDCOL);
//
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(
//					new InputStreamReader(WordUtils.class.getClassLoader().getResourceAsStream("word/stopwd.txt")));
//			for (String buf = ""; (buf = br.readLine()) != null;) {
//				if (buf == null || buf.trim().equals("")) {
//					continue;
//				}
//				Document doc = new Document();
//				doc.put("_id", Calendar.getInstance().getTimeInMillis());
//				doc.put("offset", null);
//				doc.put("limit", null);
//				Thread.sleep(300);
//				doc.put("updateTime", new Date());
//				doc.put("type", "stop");
//				doc.put("content", buf);
//				System.out.println(buf);
//				// 获取数据库
//				MongoUtils.insertOne(coll, doc);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			try {
//				if (br != null)
//					br.close();
//			} catch (IOException e) {
//			}
//		}
//	}

}
