//Evan Digles COSC 455 Project 0
// Had a bit of trouble with some other things, so this got pushed back further than I would've liked.

// Imports.
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class EvanDiglesProject0 {

    public static void main(String args[]) throws IOException{ // Main method.

        System.out.println("Welcome to the program! Please enter the name of the File that you'd like to read from."); // Placeholder starting text.

        // Variables

        boolean doQuit = false; // Is the user done?
        Scanner userInput = new Scanner(System.in); // Get User Input.
        String currentCharacter[] = new String[1]; // NOTE: not actually a character, but a string.
        int currentLine[] = new int[1];
        int currentColumn[] = new int[1];
        File userFile; // Initialize this and the fileScanner here for use later.
        Scanner fileScanner;


        do { // Main loop.


            System.out.println("Enter File Name:");

            try {
                userFile = getFile(userInput.nextLine());
                fileScanner = new Scanner(userFile);
            } catch (IOException noFile) {
                System.out.println("That file does not exist.");
                continue; // Start the loop over if we don't have that file.
            }

            nextChar(fileScanner, currentCharacter);
            System.out.println(currentCharacter[0]);
            nextChar(fileScanner, currentCharacter);
            System.out.println(currentCharacter[0]);

            

            fileScanner.close();
        } while (doQuit == false);

        System.out.println("Thanks for using the program! Exiting.");
        userInput.close();


    }

    private static File getFile(String getThisFile) throws IOException { // Get the file, if we don't have the file then throw the error.


        File theFile = new File(getThisFile);
        if (theFile.exists() == false) {
            throw new IOException("File does not exist.");
        }

        return theFile;

    }

    public static void nextChar(Scanner fileScanner, String currentCharacter[]) {
        fileScanner.useDelimiter(""); // Use an empty delimiter.
        currentCharacter[0] = fileScanner.next();
        //System.out.println(currentChar);

        return;
    }

    public static String currentChar(String currentCharacter[]) {
        String outString = currentCharacter[0];

        return outString;
    }


    
}
