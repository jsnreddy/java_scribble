package com.jsn.nlpTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.abzooba.xpresso.engine.config.XpConfig;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class MentionsTest {

	static String review = null;
	static Annotation document = null;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Properties props = PropertiesUtils.asProperties("annotators", "tokenize,ssplit,pos,lemma,depparse,ner,parse,entitymentions,mention,coref,natlog,openie"
		// , "depparse.model", "edu/stanford/nlp/models/parser/nndep/english_SD.gz"
		// "annotators", "tokenize,ssplit,pos,lemma,parse,natlog,openie"
		// , "parse.originalDependencies", "true"
		);

		XpConfig.init();
		props.setProperty("openie.resolve_coref", "true");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//		SubjectObjectIdentification soiObj;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while (true) {
			System.out.println("Enter review:");
			review = reader.nextLine();

			//		System.out.println("Review : " + review);
			document = new Annotation(review);
			//		System.out.println("Document : " + document.toString());
			pipeline.annotate(document);
			//		soiObj = new SubjectObjectIdentification(review);
			//		CoreNLPController.getPipeline(language).annotate(document);
			//				List<CoreMap> sentencesList = 
			//			processReview(document);
			List<CoreMap> allMentions = new ArrayList<CoreMap>();
			List<CoreMap> sentencesList = document.get(SentencesAnnotation.class);
			for (CoreMap sentence : sentencesList) {
				System.out.println("Sentence : " + sentence.toString());
				List<CoreMap> mentions = sentence.get(CoreAnnotations.MentionsAnnotation.class);

				if (mentions != null) {
					allMentions.addAll(mentions);
				}
			}
			for (CoreMap mention : allMentions) {
				System.out.println(mention.toString());
			}
		}
	}

}
