package test;

import java.io.File;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Test1 {
	String[] a = {
			"3, ��Ϊ - ��Ϊ����, ����",
			"4, ��Ϊ�ֻ�, �콢",
			"5, ���� - Thinkpad, ����",
			"6, �����ֻ�, ��������"
	};
	
	@Test
	public void test1() throws Exception {
		//�洢�����ļ���·��
		File path = new File("d:/abc/");
		FSDirectory d = FSDirectory.open(path.toPath());
		//lucene�ṩ�����ķִ���
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		//ͨ�����ö�����ָ���ִ���
		IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
		//�����������
		IndexWriter writer = new IndexWriter(d, cfg);
		
		for (int i = 0; i < a.length; i++) {
			String[] strs = a[i].split(",");
			
			//�����ĵ�,�ĵ��а�������Ҫ�������ֶ�
			Document doc = new Document();
			doc.add(new LongPoint("id", Long.parseLong(strs[0])));
			doc.add(new StoredField("id", Long.parseLong(strs[0])));
			doc.add(new TextField("title", strs[1], Store.YES));
			doc.add(new TextField("sellPoint", strs[2], Store.YES));
			
			//���ĵ�д����������ļ�
			writer.addDocument(doc);
		}
		writer.close();
		
	}
	@Test
	public void test2() throws Exception {
		//�������ݵı���Ŀ¼
		File path = new File("d:/abc");
		FSDirectory d = FSDirectory.open(path.toPath());
		//�����������߶���
		DirectoryReader reader = DirectoryReader.open(d);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		//�ؼ���������,�������� "title:��Ϊ"
		TermQuery q = new TermQuery(new Term("title", "��Ϊ"));
		//ִ�в�ѯ,������ǰ20������
		TopDocs docs = searcher.search(q, 20);

		//������ѯ���Ľ���ĵ�����ʾ
		for (ScoreDoc scoreDoc : docs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println(doc.get("id"));
			System.out.println(doc.get("title"));
			System.out.println(doc.get("sellPoint"));
			System.out.println("--------------");
		}
	}
	
}