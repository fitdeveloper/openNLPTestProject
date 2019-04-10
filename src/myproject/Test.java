package myproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class Test {

	/**
	 * @param args
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static void main(String args[]) throws InvalidFormatException,
	IOException {
				String paragraph = "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29. Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group. Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields PLC, was named a director of this British industrial conglomerate.";
				
				// always start with a model, a model is learned from training data
				InputStream is = new FileInputStream("C:\\Users\\ghost\\Desktop\\eclipse-workspaceJee\\myproject\\en-sent.bin");
				SentenceModel model = new SentenceModel(is);
				SentenceDetectorME sdetector = new SentenceDetectorME(model);
				
				String sentences[] = sdetector.sentDetect(paragraph);
				for(int i= 0;i<sentences.length;i++) {
					System.out.println(sentences[i]);
				}
				is.close();				
	} 
	
	
	
	

	
	

}



