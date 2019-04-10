package myproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;



/**
 * @author ghost
 *
 */
public class Test2 {

	public static void main(String[] args) throws IOException {
//		 TODO Auto-generated method stub
//		  try {
//	            new Test2().sentenceDetect();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } 
//		
		
		
   //     System.out.println("----------");

        
        
		
		// testing the model and printing the types it found in the input sentence
//        TokenNameFinder nameFinder = new NameFinderME(test2.trainTheModel(objectStream));
//        String[] testSentence ={"Visa","and","MasterCard", "","ok"};
//        System.out.println(testSentence.length);
//        System.out.println("Finding types in the test sentence..");
//        Span[] names = nameFinder.find(testSentence);
//        for(Span name:names){
//           System.out.println("----------");
//          System.out.println(name);
//            String personName="";
//            for(int i=name.getStart();i<name.getEnd();i++){
//                personName+=testSentence[i]+" ";
//            }
//            System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
//        } 
		
		
		
		InputStream is2 = new FileInputStream("en-token.bin");
		TokenizerModel model2 = new TokenizerModel(is2);
		Tokenizer tokenizer = new TokenizerME(model2);
		String tokens[] = tokenizer.tokenize("Visa ok yes or no ?");
		List sentences = new ArrayList<String>() ;
		for (String a : tokens) {
			System.out.println(a);
            sentences.add(a);
		}
			
		is2.close();

		Test2 test2 = new Test2();
		ObjectStream objectStream =test2.readTrainningData("creditCardDataTraining.txt");
        TokenNameFinder nameFinder = new NameFinderME(test2.trainTheModel(objectStream));

        InputStream is = new FileInputStream("nerr-custom-model.bin");

    	TokenNameFinderModel model = new TokenNameFinderModel(is);
    	is.close();
     
     
    	String []sentence = (String[]) sentences.toArray();
    			// new String[]{"Visa","ok","", "ok","sjjs"};
     
    		Span nameSpans[] = nameFinder.find(sentence);
			System.out.println("------------");	

    		for(Span s: nameSpans)
    			System.out.println(s + "->" + s.getType());	
	}

	
	
	public void sentenceDetect() throws InvalidFormatException, IOException {
        String paragraph = "Apache openNLP visa supports the most common NLP tasks, such as tokenization, sentence segmentation, part-of-speech tagging, named entity extraction, chunking, parsing, and coreference resolution. These tasks are usually required to build more advanced text processing services. OpenNLP also includes maximum entropy and perceptron based machine learning.";
 
        // refer to model file "en-sent,bin", available at link http://opennlp.sourceforge.net/models-1.5/
        InputStream is = new FileInputStream("C:\\Users\\ghost\\Desktop\\AnnotatedSentences.txt\\ner-custom-model.bin");
        SentenceModel model = new SentenceModel(is);
 
        // load the model
        SentenceDetectorME sdetector = new SentenceDetectorME(model);
 
        // detect sentences in the paragraph
        String sentences[] = sdetector.sentDetect(paragraph);
 
        // print the sentences detected, to console
        for(int i=0;i<sentences.length;i++){
          //  System.out.println(sentences[i]);
        }
        is.close();
        
        
      
    } 

	
	
	public ObjectStream readTrainningData(String txtFile) {
		InputStreamFactory in = null;
		try {
		    in = new MarkableFileInputStreamFactory(new File(txtFile));
		} catch (FileNotFoundException e2) {
		    e2.printStackTrace();
		}
		 
		ObjectStream sampleStream = null;
		try {
		    sampleStream = new NameSampleDataStream(
		        new PlainTextByLineStream(in, StandardCharsets.UTF_8));
		} catch (IOException e1) {
		    e1.printStackTrace();
		} 
		
		return sampleStream;
	}
	
	
	public TokenNameFinderModel trainTheModel(ObjectStream sampleStream) {
		
		TrainingParameters params = new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 70);
		params.put(TrainingParameters.CUTOFF_PARAM, 1); 
		
		TokenNameFinderModel nameFinderModel = null;
		try {
		    nameFinderModel = NameFinderME.train("en", null, sampleStream,
		        params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		} catch (IOException e) {
		    e.printStackTrace();
		} 
		
		File output = new File("nerr-custom-model.bin");
		FileOutputStream outputStream;
	
		try {
			outputStream = new FileOutputStream(output);
			nameFinderModel.serialize(outputStream); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameFinderModel;
	}
}
