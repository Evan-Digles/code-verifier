//Evan Digles COSC 455 Project 1
// Okay.

// Imports.
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

public class EvanDiglesProject1 {

    public static void main(String args[]) throws IOException{ // Main method.

        System.out.println("Welcome to the program! Please enter the name of the File that you'd like to read from."); // Placeholder starting text.

        // Variables

        boolean doQuit = false; // Is the user done?
        Scanner userInput = new Scanner(System.in); // Get User Input.
        String currentCharacter[] = new String[1]; // NOTE: not actually a character, but a string. We use an array here so that
                                                   // methods can change it without returning anything.
        int currentLine[] = new int[] {1}; // Line, array for changing. We start at 1 rather than 0.
        int currentColumn[] = new int[1]; // Column, arracy for changing.
        

        while (doQuit != true) { // Main loop.

            // Variables and resets.
            File userFile;
            FileInputStream userFileStream;
            currentCharacter[0] = null;

            System.out.println("Enter File Name, or type 'quit' or 'exit' to exit the program.");
            String userNextLine = userInput.nextLine();

            if (userNextLine.toLowerCase().equals("quit") || userNextLine.toLowerCase().equals("exit")) { // If we quit, we quit.
                doQuit = true;
                continue;
            }

            try { // Check if the file exists.
                userFile = getFile(userNextLine);
            } catch (IOException noFile) {
                System.out.println("That file does not exist.");
                continue; // Start the loop over if we don't have that file.
            }
            userFileStream = new FileInputStream(userFile);

            while(currentCharacter[0] != "End-Of-File") { // Until the file is done, keep going.
                nextChar(userFileStream, currentCharacter, currentLine, currentColumn);
                if (currentCharacter[0] != "Carriage" && currentCharacter[0] != "End-Of-File") { // As shown in the instructions (Specifically Project 0 Description), whitespace is printed except for these two things. If you copy paste the example, it will print the same intended output.
                    System.out.println(position(currentLine, currentColumn) + " " + currentCharacter[0]);
                } 
            }

            userFileStream.close();

        }

        System.out.println("Thanks for using the program! Exiting."); // Exit message.
        userInput.close();


    }

    private static File getFile(String getThisFile) throws IOException { // Get the file, if we don't have the file then throw the error.


        File theFile = new File(getThisFile);
        if (theFile.exists() == false) {
            throw new IOException("File does not exist.");
        }

        return theFile;

    }

    public static void nextChar(FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[]) throws IOException { // I was trying to be creative. Anyways, this gets a character and sends it to the single digit string array we set earlier.
        
        int byteChar;
        char takeToString = '1';
        String sendThisOut = null;

        if ((byteChar = userFileStream.read()) != -1) { // This clever bit of code inside the () I found from https://www.geeksforgeeks.org/java-io-fileinputstream-class-java/ It lets the filestream signal end of file. Surprisingly the only thing I couldn't really figure out well on my own.
            takeToString = (char)byteChar;
            // System.out.println(byteChar); This is used for testing.
            sendThisOut = String.valueOf(takeToString);
        } else {
            sendThisOut = "End-Of-File";
        }
        
        if (takeToString != '\n') {
            currentColumn[0] = currentColumn[0] + 1;
        } else {
            currentColumn[0] = 0;
            currentLine[0] = currentLine[0] + 1;
        }
        if (byteChar == 13 || byteChar == 10) { // We want to ignore the ASCII for Newline and Carriage.
            if (byteChar == 13) {
                sendThisOut = "Carriage";
                currentCharacter[0] = sendThisOut;
            } else {
                sendThisOut = ""; // Leave empty if there's a new line.
                currentCharacter[0] = sendThisOut;
            }
            
        } else {
            
            currentCharacter[0] = sendThisOut;
        }

        //System.out.println(currentChar);

        return;
    }

    public static String currentChar(String currentCharacter[]) { // Yes, I would like to see the current character.

        String outString = currentCharacter[0];
        

        return outString;
    }

    public static String position(int currentLine[], int currentColumn[]) { // Returns a string of the current position. I'll probably need to replace this in the future.
        String position = currentLine[0] + ":" + currentColumn[0];
        return position;
    }


    
}
