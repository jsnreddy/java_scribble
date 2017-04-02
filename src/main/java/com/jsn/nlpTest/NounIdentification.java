/**
 * 
 */
package com.jsn.nlpTest;

/**
 * @author Surendranath Reddy
 *
 */
public class NounIdentification {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//		XpEngine.init(true);
		String sentenceStr = "allReviews-p";

		String suffix = Integer.toString(1) + Integer.toString(2);
		System.out.println("Sentence : " + sentenceStr + suffix);
		//		Annotation document = new Annotation(sentenceStr);
		//
		//		List<CoreMap> sentenceList = document.get(CoreAnnotations.SentencesAnnotation.class);
		//
		//		for (CoreMap sentence : sentenceList) {
		//			List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		//			for (CoreLabel token : tokens) {
		//				if (PosTags.isNoun(Languages.EN, token.tag())) {
		//					System.out.println(token.word() + " is Noun");
		//				}
		//			}
		//		}

	}

}
