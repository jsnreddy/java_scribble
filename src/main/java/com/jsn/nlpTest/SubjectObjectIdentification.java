package com.jsn.nlpTest;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.naturalli.SentenceFragment;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class SubjectObjectIdentification {
	protected static final Class<?> c = SubjectObjectIdentification.class;
	static String review = null;
	static Annotation document = null;

	//	public SubjectObjectIdentification(String review) {
	//		SubjectObjectIdentification.review = review;
	//		SubjectObjectIdentification.document = this.annotate();
	//	}

	protected static void getSubjectCoreLabelList(RelationTriple triple) {
		List<CoreLabel> subjectCoreLabelList = triple.subject;
		for (CoreLabel subjectCoreLabel : subjectCoreLabelList) {
			String subjectCoreLabelLemma = subjectCoreLabel.lemma();
			System.out.println("subjectCoreLabelLemma : " + subjectCoreLabelLemma);
		}
	}

	protected static void getObjectCoreLabelList(RelationTriple triple) {
		List<CoreLabel> objectCoreLabelList = triple.object;
		for (CoreLabel objectCoreLabel : objectCoreLabelList) {
			String objectCoreLabelLemma = objectCoreLabel.lemma();
			System.out.println("objectCoreLabelLemma : " + objectCoreLabelLemma);
		}
	}

	protected static void getSubjectGloss(RelationTriple triple) {
		String subjectGloss = triple.subjectGloss();
		System.out.println("subjectGloss : " + subjectGloss);
	}

	protected static void getObjectGloss(RelationTriple triple) {
		String objectGloss = triple.objectGloss();
		System.out.println("objectGloss : " + objectGloss);
	}

	protected static void getSubjectLemmaGloss(RelationTriple triple) {
		String subjectLemmaGloss = triple.subjectGloss();
		System.out.println("subjectLemmaGloss : " + subjectLemmaGloss);
	}

	protected static void getObjectLemmaGloss(RelationTriple triple) {
		String objectLemmaGloss = triple.objectLemmaGloss();
		System.out.println("objectLemmaGloss : " + objectLemmaGloss);
	}

	protected static Annotation annotate(String review) {
		return new Annotation(review);
	}

	protected static List<CoreMap> getSentences(Annotation document) {
		//		System.out.println("in getSentences");
		return document.get(SentencesAnnotation.class);
	}

	protected Collection<RelationTriple> getTriples(CoreMap sentence) {
		return sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
	}

	protected static void processReview(Annotation document) {
		List<CoreMap> sentencesList = getSentences(document);
		System.out.println("Number of sentences : " + sentencesList.size());
		for (CoreMap sentence : sentencesList) {
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
			Collection<SentenceFragment> entailedSentences = sentence.get(NaturalLogicAnnotations.EntailedSentencesAnnotation.class);
			for (RelationTriple triple : triples) {
				System.out.println(triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t" + triple.objectLemmaGloss());

				//				getSubjectCoreLabelList(triple);
				//				getSubjectGloss(triple);
				//				getSubjectLemmaGloss(triple);
				//
				//				getObjectCoreLabelList(triple);
				//				getObjectGloss(triple);
				//				getObjectLemmaGloss(triple);

			}
			System.out.println("----");
			System.out.println("entailed sentences");
			//			for (SentenceFragment fragment : entailedSentences) {
			//				System.out.println(fragment.toString());
			//			}
		}
		//		System.out.println("---");
		//		System.out.println("coref chains");
		//		for (CorefChain cc : document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
		//			System.out.println("\t" + cc);
		//		}
		//		for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
		//			System.out.println("---");
		//			System.out.println("mentions");
		//			for (Mention m : sentence.get(MentionsAnnotation.class)) {
		//				System.out.println("\t" + m);
		//			}
		//		}
	}

	public static void main(String[] args) {
		// Create the Stanford CoreNLP pipeline
		//tokenize,ssplit,pos,lemma,ner,parse,sentiment
		Properties props = PropertiesUtils.asProperties("annotators", "tokenize,ssplit,pos,lemma,depparse,ner,parse,mention,coref,natlog,openie"
		// , "depparse.model", "edu/stanford/nlp/models/parser/nndep/english_SD.gz"
		// "annotators", "tokenize,ssplit,pos,lemma,parse,natlog,openie"
		// , "parse.originalDependencies", "true"
		);
		props.setProperty("openie.resolve_coref", "true");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		//		SubjectObjectIdentification soiObj;
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		while (true) {

			System.out.println("Enter review:");
			review = reader.nextLine();

			//		System.out.println("Review : " + review);
			document = annotate(review);
			//		System.out.println("Document : " + document.toString());
			pipeline.annotate(document);
			//		soiObj = new SubjectObjectIdentification(review);
			//		CoreNLPController.getPipeline(language).annotate(document);
			//				List<CoreMap> sentencesList = 
			processReview(document);
		}

	}

}
