import java.net.*;
import java.io.*;

public class Protocol {
	private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;
    private static final int FINAL = 4;
    
    private static final int NUMJOKES = 3;
    
    private int state = WAITING;
    private int currentJoke = 0;
	
	private String[] clues = { "list", "get", "put" };
	
	public String processInput(String theInput) {
		String theOutput = null;		
		if (state == WAITING) {
        	theOutput = "Client begin!!";
            state = SENTKNOCKKNOCK;
        } 
        if (state == SENTKNOCKKNOCK) {
        	if (theInput.equalsIgnoreCase("list")) {
                
                state = SENTCLUE;
            } else {
                theOutput = "Please input 'list'";
            }   
        }
        if (state == SENTCLUE) {
        	if (theInput.equalsIgnoreCase("get")) {
                
                state = ANOTHER;
            } else {
                theOutput = "Please input 'get fname'";
            }
        }
        if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("put")) {
                
                state = FINAL;
            } else {
                theOutput = "Please input 'put fname'";
            }
        }
        if (state == FINAL) {
        	theOutput = "Exit";
        }
        return theOutput;
	}
}
