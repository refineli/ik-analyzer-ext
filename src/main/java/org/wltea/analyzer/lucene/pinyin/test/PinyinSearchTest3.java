package org.wltea.analyzer.lucene.pinyin.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.wltea.analyzer.lucene.pinyin.PinyinAnalyzer;

/**
 * 拼音搜索测试
 * @author Lanxiaowei
 *
 */
public class PinyinSearchTest3 {
	public static void main(String[] args) throws Exception {
		String fieldName = "content";
		String queryString = "tam";
		
		Directory directory = new RAMDirectory();
		Analyzer analyzer = new PinyinAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, config);
		
		/****************创建测试索引begin********************/
		Document doc1 = new Document();
		doc1.add(new TextField(fieldName, "中国北京天安门", Store.YES));
		writer.addDocument(doc1);

		//强制合并为1个段
		writer.forceMerge(1);
		writer.close();
		/****************创建测试索引end********************/
		
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term(fieldName,queryString));
		TopDocs topDocs = searcher.search(query, Integer.MAX_VALUE);
		ScoreDoc[] docs = topDocs.scoreDocs;
		if(null == docs || docs.length <= 0) {
			System.out.println("No results.");
			return;
		}
		
		//打印查询结果
		System.out.println("ID[Score]\tcontent");
		for (ScoreDoc scoreDoc : docs) {
			int docID = scoreDoc.doc;
			Document document = searcher.doc(docID);
		    String content = document.get(fieldName);
		    float score = scoreDoc.score;
		    System.out.println(docID + "[" + score + "]\t" + content);
		}
	}
}
