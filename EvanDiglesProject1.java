// Evan Digles COSC 455 Project 1
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

        // Lexeme Related Variables
        char acceptableCharacters[] = // Acceptable terminal characters for the gammar we're working with. This is good.
                                      {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', // Lower case letters.
                                       'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
                                       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', // Upper case letters.
                                       'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                       '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // Numbers.
                                       ':', '+', '-', '*', '.', ',', ';', '=', '<', '!', '>', '/', '_', '(', ')', // Other symbols. NOTE: I can make these if they're specifically occuring in some way be lexically invalid. Such at making just "!" invalid and not "!=".
                                       ' ', '\n', '\t', // Ah yes, white space!
                                      }; 
        /*String specialSymbols[] = // Special words or symbols with certain meanings from the grammar sheet, in order of appearance on the grammar sheet.
                                  // These should do something specific, or otherwise be removed from this list later.
                                  // A check for whitespace or some other symbol after this should come after.
                                    // Should we iterate through these when looking at each step of lexeme identification, or only at the end?
                                    // Maybe I should check for more than just whitespace if this is encountered?
                                  // I'll probably go through these and 'trim the fat', which is to say, remove what doesn't need to be here later.
                                  {"program", ":", ".", "bool", "int", ",", ";", ":=", "if", "then", "else", "end", 
                                   "while", "do", "print", "<", "=<", "=", "!=", ">=", ">", "+", "-", "or", "*", "/", 
                                   "mod", "and", "not", "false", "true", "_", 
                                  };*/ // Depreciated, but kept for reference.
        String keyWords[] = // Special words that are not symbols themselves.
                            { "program", "bool", "int", "if", "then", "else", "end", "while", "do", 
                              "print", "or", "mod", "and", "not", "false", "true"
                            };
        String symbolTable[] = // Special Symbols or symbol combinations that have some special function.
                            { ":", ".", ",", ":=", "<", "=<", "=", "!=", ">=", ">", "+", "-", "*",
                              "/", "_", " ", // I also include whitespace here for some reason.
                            }; 
        String whiteSpace[] = // This stuff we use for white space.
                            { " ", "\n", "\t", // Our whitespace should be space, newline, and tab.
                                               // We shouldn't need to use this, this should mostly just be for reference here.
                            };                 // I apologize to those who care about efficiency.
        String acceptableIDCharacters[] = // If we have an ID, then only these characters should be in it. So all letters, digits, and "_".
                            {// Letters, lower case.
                                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                             // Letters, upper case.
                                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                             // Digits
                                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
                             // "_"
                                "_",

                            };
        String kind[] = new String[1]; // Our current kind of lexeme.
        lexeme OurLexeme[] = new lexeme[1]; // Our current lexeme.
                        

        // Scanner
        Scanner userInput = new Scanner(System.in); // Get User Input.

        // Character Input Related Variables
        String currentCharacter[] = new String[1]; // NOTE: not actually a character, but a string. We use an array here so that
                                                   // methods can change it without returning anything.
        int currentLine[] = new int[] {1}; // Line, array for changing. We start at 1 rather than 0.
        int currentColumn[] = new int[] {1}; // Column, arracy for changing.
        

        while (doQuit != true) { // Main loop.

            // Variables and resets.
            File userFile;
            FileInputStream userFileStream;
            currentCharacter[0] = "";

            

            System.out.println("Enter File Name, or type 'quit' or 'exit' to exit the program.");
            String userNextLine = userInput.nextLine();

            if (userNextLine.toLowerCase().equals("quit") || userNextLine.toLowerCase().equals("exit")) { // If we quit, we quit.
                doQuit = true;
                continue;
            }

            // Testing:
            setKind("TODO KIND", kind);

            try { // Check if the file exists.
                userFile = getFile(userNextLine);
            } catch (IOException noFile) {
                System.out.println("That file does not exist.");
                continue; // Start the loop over if we don't have that file.
            }
            userFileStream = new FileInputStream(userFile);
 
            OurLexeme[0] = new lexeme(userFileStream, currentCharacter, currentLine, currentColumn);

            next(OurLexeme, userFileStream, currentCharacter, currentLine, currentColumn);
            System.out.println( position(OurLexeme) + " " + kind(OurLexeme) + " " + value(OurLexeme) );
            //System.out.println(currentCharacter[0]);
            //System.out.println(OurLexeme[0].toString());
            while ( currentCharacter[0].equals("End-Of-File") == false ) {
                //System.out.println("Entered loop?");
                next(OurLexeme, userFileStream, currentCharacter, currentLine, currentColumn);
                //System.out.println(OurLexeme[0]);
                //System.out.println(currentCharacter[0]);
                System.out.println( position(OurLexeme) + " " + kind(OurLexeme) + " " + value(OurLexeme) ); 
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
            currentCharacter[0] = "End-Of-File";
        }
        
        if (takeToString != '\n') {
            currentColumn[0] = currentColumn[0] + 1;
        } else {
            currentColumn[0] = 0;
            currentLine[0] = currentLine[0] + 1;
        }
        if (byteChar == 13 || byteChar == 10) { // We want to ignore the ASCII for Newline and Carriage. Now only the carriage...
            if (byteChar == 13) {
                sendThisOut = "Carriage";
                currentCharacter[0] = sendThisOut;
            } else {
                sendThisOut = "NewLine"; // Leave empty if there's a new line.
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

    // Lexeme Class and related stuff for Lexemes.
    static class lexeme { // Should require all the same things as nextChar.
        // Todo: Lexeme storage stuff.

        String theLexeme;
        String theKind = "ID"; // Placeholder for testing.
        String theValue;
        FileInputStream userFileStream;
        String currentCharacter[];
        int currentLine[];
        int currentColumn[];
        int lexLine;
        int lexColumn;

        lexeme(FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[]) { // constructor
            //TODO

            this.theLexeme = "";
            this.userFileStream = userFileStream;
            this.currentCharacter = currentCharacter;
            this.currentLine = currentLine;
            this.currentColumn = currentColumn;
            this.lexLine = currentLine[0];
            this.lexColumn = currentColumn[0];
        }
        

        public void addCharacter(String addThis) {
            //System.out.println("Concat " + theLexeme + " " + addThis);
            this.theLexeme = this.theLexeme.concat(addThis);
        }

        public void reset() {
            theLexeme = "";
        }

        public String toString() {
            return theLexeme;
        }

        public void updatePosition() {
            this.lexLine = currentLine[0];
            this.lexColumn = currentColumn[0];
        }

        public String position() {
            return this.lexLine + " " + this.lexColumn;
        }

        public String lexemeKind() {
            return this.theKind;
        }

        public String lexemeValue() {
            this.theValue = null;
            if (this.theKind.equals("ID") || this.theKind.equals("NUM")) {
                this.theValue = this.theLexeme;
            } 

            return this.theValue;
        }

        


    }

    public static void next(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[] ) throws IOException {
        
        inLexeme[0].reset(); // Reset the current lexeme.
        inLexeme[0].updatePosition(); // Update the position to the current starting position of the lexeme.
        boolean didWhiteSpace = false;
        //System.out.println("We got here");
        while(didWhiteSpace != true && (currentCharacter[0].equals("End-Of-File") == false)) { // Until the current lexeme is done, keep going.

            //System.out.println("Char before next: " + currentCharacter[0]);
            if (currentCharacter[0].equals("")) { // Initial start of the file or special white space? Skip it!
                nextChar(userFileStream, currentCharacter, currentLine, currentColumn); // Get the initial character.
            }
            //System.out.println("testing");
            //System.out.println(currentCharacter[0]);
            if (currentCharacter[0].equals("End-Of-File")) {
                continue;
            }
            //System.out.println(inLexeme[0].toString() + "ok");
            // Hit a whitespace? We should then skip all forwards whitespace and then
            if (currentCharacter[0].equals("") == true || currentCharacter[0].equals(" ") == true || currentCharacter[0].equals("NewLine") == true || currentCharacter[0].equals("Carriage") || currentCharacter[0].equals("\t") == true || (currentCharacter[0].equals("End-Of-File") == true)) {
                didWhiteSpace = true;
                while (currentCharacter[0].equals("") == true || currentCharacter[0].equals(" ") == true || currentCharacter[0].equals("NewLine") == true || currentCharacter[0].equals("Carriage") || currentCharacter[0].equals("\t") == true || (currentCharacter[0].equals("End-Of-File") == true)) { // Skip the whitespace.
                    nextChar(userFileStream, currentCharacter, currentLine, currentColumn);
                }
                continue; // Finished with whitespace? We're done!
            } else { // Not a whitespace? Put it in!
                inLexeme[0].addCharacter(currentCharacter[0]);
            }
            nextChar(userFileStream, currentCharacter, currentLine, currentColumn);


        }
        //System.out.println("We got here 2");
    }

    public static boolean checkCurrent(String theChar, char[] acceptArray) { // Returns true if the char is acceptable, otherwise, return false.

        for (char checkThis : acceptArray) {
            if (theChar.charAt(0) == checkThis) {
                return true;
            }
        }

        return false;
    }

    public static String kind(lexeme[] OurLexeme) { // Required in the instructions.
        return OurLexeme[0].lexemeKind();
    }

    public static void setKind(String kindOf, String[] kind) { // Use this to set the kind of the lexeme.
        kind[0] = kindOf;
    }

    public static String value(lexeme[] OurLexeme) {
        return OurLexeme[0].lexemeValue();
    }    

    public static String position(lexeme[] OurLexeme) {
        return OurLexeme[0].position();
    }
    
}
