/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsphinx.desktop;

/**
 *
 * @author Shivam Tiwari
 */

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
        
public class JSphinxDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ConfigurationManager cm;

        if (args.length > 0) {
            cm = new ConfigurationManager(args[0]);
        } else {
            cm = new ConfigurationManager(JSphinxDesktop.class.getResource("JSphinxDesktop.config.xml"));
        }
        
        // allocate the recognizer
	System.out.println("Loading...");
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }
        
	printInstructions();

        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("Start speaking. Press Ctrl-C to quit.\n");

            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();

                System.out.println("You said: " + resultText + '\n');
		if("alone".equals(resultText)){
                  System.out.println("Do not be afraid, baby!");}

            } else {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }
    
    private static void printInstructions() {
	    System.out.println("Sample sentences:\n\n" +
                    "the green one right in the middle\n" +
		    "the purple one on the lower right side\n" +
		    "the closest purple one on the far left side\n" +
		    "the only one left on the left\n\n\n");
   }
}
