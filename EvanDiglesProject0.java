//Evan Digles COSC 455 Project 0
// Had a bit of trouble with some other things, so this got pushed back further than I would've liked.

// Imports.
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class EvanDiglesProject0 {

    public static void main(String args[]) { // Main method.

        System.out.println("Welcome to the program! Please enter the name of the File that you'd like to read from."); // Placeholder starting text.

        // Variables

        boolean doQuit = false; // Is the user done?
        Scanner userInput = new Scanner(System.in); // Get User Input.

        do { // Main loop.
            System.out.println("Enter File Name:");

            try {
                getFile(userInput.nextLine());
            } catch (IOException noFile) {
                System.out.println("That file does not exist.");
            }


        } while (doQuit == false);

        System.out.println("Thanks for using the program! Exiting.");
        userInput.close();


    }

    public static File getFile(String getThisFile) throws IOException {


        File theFile = new File(getThisFile);
        if (theFile.exists() == false) {
            throw new IOException("File does not exist.");
        }

        return theFile;

    }
    
}
