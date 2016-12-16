package com.jsn.nlpTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class NLPengine {

	Properties props;
	StanfordCoreNLP pipeline;
	ArrayList<String> personNames = new ArrayList<String>();

	public NLPengine() {
		//		props = PropertiesUtils.asProperties(
		//	            "annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,ner,parse,dcoref,openie");
		//		props.setProperty("openie.resolve_coref","true");
		//		props.setProperty("openie.triple.strict","false");

		//----for NER
		props = PropertiesUtils.asProperties("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,ner");

		pipeline = new StanfordCoreNLP(props);
	}

	public void getTriples(String arg) throws Exception {
		// Annotate an example document.
		BufferedWriter wbrTriples = new BufferedWriter(new FileWriter("data/sampleTriples.csv", false));
		String text = "";
		/*if (arg != null) {
		  text = IOUtils.slurpFile(arg);
		  System.out.println("Reading from file " +arg);
		} else {
			System.out.println("No file found...!!");
		  text = "Obama was born in Hawaii. He is our president.";
		}*/

		// preprocess the text
		//		Preprocessor preprocessor = new Preprocessor();
		//		text = preprocessor.getNormalizedTextFromFile(arg);

		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);

		// Loop over sentences in the document
		int sentNo = 0;
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
			System.out.println("Sentence #" + ++sentNo + ": " + sentence.get(CoreAnnotations.TextAnnotation.class));

			// Print SemanticGraph
			//	      System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));

			// Get the OpenIE triples for the sentence
			Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);

			// Print the triples
			for (RelationTriple triple : triples) {
				/* System.out.println(triple.confidence + "\t" +
				triple.subjectLemmaGloss() + "\t" +
				triple.relationLemmaGloss() + "\t" +
				triple.objectLemmaGloss()); */
				wbrTriples.write(triple.confidence + "," + triple.subjectGloss() + "," + triple.relationGloss() + "," + triple.objectGloss());
				wbrTriples.newLine();

				wbrTriples.write(" ," + getPOStags(triple.subject) + ',' + getPOStags(triple.relation) + ',' + getPOStags(triple.object));
				wbrTriples.newLine();
				wbrTriples.newLine();

				/*getPersonNames(triple.subject);
				getPersonNames(triple.relation);
				getPersonNames(triple.object);*/

			}

			// Alternately, to only run e.g., the clause splitter:
			/* List<SentenceFragment> clauses = new OpenIE(props).clausesInSentence(sentence);
			for (SentenceFragment clause : clauses) {
			System.out.println(clause.parseTree.toString(SemanticGraph.OutputFormat.LIST));
			}
			System.out.println();*/

		}
		/*Set<String> hs = new HashSet<>();
		hs.addAll(personNames);
		for(String str:hs){
			  System.out.println(str);
		}*/

		wbrTriples.close();
	}

	public String getPOStags(List<CoreLabel> tokens) {
		String posTag;
		StringBuilder allPOStags = new StringBuilder();

		for (CoreLabel token : tokens) {
			posTag = token.get(PartOfSpeechAnnotation.class);
			posTag = posTag + ":" + token.ner();
			allPOStags.append(posTag + " ");
		}
		return allPOStags.toString();
	}

	/*public void getPersonNames(List<CoreLabel> tokens){
		String previousNER = null,previousEntity = null,currentNER=null,currentEntity=null;
		for(CoreLabel token:tokens){
			currentNER = token.ner();
			currentEntity = token.word();
			
			if(currentNER.equals("PERSON")){
				if(previousNER==null)
					personNames.add(currentEntity);
				else if(previousNER.equals("PERSON")){
					if(personNames.contains(previousEntity))
						personNames.remove(previousEntity);
					previousEntity += " " +currentEntity;
					previousNER = currentNER;
					personNames.add(previousEntity);
					continue;
				}else{
					personNames.add(currentEntity);
				}
			}
			
			previousEntity = currentEntity;
			previousNER = currentNER;
		}
	}*/

	public static ArrayList<String> addPersonName(ArrayList<String> personNames, String name) {
		ArrayList<String> names = personNames;
		name = name.toLowerCase();
		String[] shortNames = name.split(" ");

		for (String shortName : shortNames) {
			for (String str : names) {
				str = str.toLowerCase();
				if (str.contains(shortName)) {
					return personNames;
				}
			}
		}

		personNames.add(name);
		return personNames;
	}

	public Set<String> extractPersonNames(String text) {
		// preprocess the text
		// Preprocessor preprocessor = new Preprocessor();
		// text = preprocessor.getNormalizedTextFromFile(arg);

		ArrayList<String> personNames = new ArrayList<String>();
		Annotation doc = new Annotation(text);

		try {
			pipeline.annotate(doc);
		} catch (NullPointerException e) {
			Set<String> personNamesSet = new HashSet<>(Arrays.asList("Null_Error"));
			return personNamesSet;
		}

		// Loop over sentences in the document
		int sentNo = 0;
		for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {

			List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
			String previousNER = null, previousEntity = null, currentNER = null,
					currentEntity = null;
			for (CoreLabel token : tokens) {

				currentNER = token.ner();
				currentEntity = token.word().toLowerCase();
				if (currentNER.equals("PERSON")) {
					if (previousNER == null) {
						personNames.add(currentEntity);
					} else if (previousNER.equals("PERSON")) {
						// handling for 2 word proper nouns
						if (personNames.contains(previousEntity)) {
							personNames.removeAll(Collections.singleton(previousEntity));
							// personNames.remove(previousEntity);
						}
						previousEntity += " " + currentEntity;
						previousNER = currentNER;
						//								personNames.add(previousEntity);
						personNames = addPersonName(personNames, previousEntity);
						continue;
					} else {
						personNames.add(currentEntity);
					}
				}

				previousEntity = currentEntity;
				previousNER = currentNER;

			}
		}
		Set<String> personNamesSet = new HashSet<>();
		personNamesSet.addAll(personNames);
		//	    for(String str:personNamesSet){
		//	    	  System.out.println(str);
		//	    }

		return personNamesSet;
	}

}
