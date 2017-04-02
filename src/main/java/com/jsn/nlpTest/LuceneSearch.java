/**
 * 
 */
package com.jsn.nlpTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Surendranath Reddy
 *
 */
public class LuceneSearch {

	public static void createIndex() throws IOException {
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		Path path = FileSystems.getDefault().getPath("index-dir");
		Directory indexDirectory = FSDirectory.open(path);
		IndexWriter indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);

		File inputDir = new File("luceneFiles");

		for (File file : inputDir.listFiles()) {
			Document doc = new Document();
			String filePath = file.getCanonicalPath();
			doc.add(new Field("path", filePath, new FieldType()));
			//			doc.add(new Field("path", filePath, Field.Store.YES, Field.));
			Reader reader = new FileReader(file);

			doc.add(new Field("contents", reader, new FieldType()));
			//			doc.add(new Field("contents", reader));

			indexWriter.addDocument(doc);

		}
		if (indexWriter.isOpen()) {
			indexWriter.close();
		}
	}

	public static void searchIndex(String searchTerm) throws IOException {

		System.out.println("Searching for : " + searchTerm);
		Path path = FileSystems.getDefault().getPath("index-dir");
		Directory dir = FSDirectory.open(path);

		IndexReader indexReader = DirectoryReader.open(dir);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Query query = new TermQuery(new Term(searchTerm));

		TopDocs hits = indexSearcher.search(query, 5);

		for (ScoreDoc t : hits.scoreDocs) {
			int score = t.doc;
			System.out.println("Hit : " + score);
		}
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		createIndex();
		searchIndex("apples");
		searchIndex("salad");
		searchIndex("bacon");
	}

}
