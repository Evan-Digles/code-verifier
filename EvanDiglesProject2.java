// Evan Digles COSC 455 Project 2
// I'll be leaving a todo list up here.
// Handling errors termination and returning position of error needs to be done.
    // For error messages, just print the position, and the token that was causing the issue, and then stops the syntax analysis for that file.
// Creating the rest of the procedures for the grammar needs to be done, but this will be minimally troublesome once I
// understand how I want to handle error termination.
// I would like the program to be able to continue to be used even if a file fails. so
// TODO: Ask the professor about if it's fine for the program to persist even if a file fails its analysis.
    // It is fine.
// In other words, does the program need to return something, or is it fine?
    // Just error messages is fine.

// Imports.
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;

// Class starts here.
public class EvanDiglesProject2 {


    // IMPORTANT: If you want the output to look exactly like the given examples, change this to true.
    static final boolean makeLikeExample = false; // I... I don't know if I can use this more than just here???
    // IMPORTANT see above.

    public static void main(String args[]) throws IOException{ // Main method.

        if (makeLikeExample == false) {
            System.out.println("Welcome to the program! Please enter the name of the File that you'd like to read from."); // My beloved starting text.
        } else if (makeLikeExample == true) {
            //don't
        }

        // Variables

        boolean doQuit = false; // Is the user done?

        // Lexeme Related Variables
        final char acceptableCharacters[] = // Acceptable terminal characters for the gammar we're working with. This is good.
                                      // Note, if the character doesn't appear here, it should NEVER appear in ANY input file.
                                      {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', // Lower case letters.
                                       'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
                                       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', // Upper case letters.
                                       'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                       '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // Numbers.
                                       ':', '+', '-', '*', '.', ',', ';', '=', '<', '!', '>', '/', '(', ')', // Other symbols. NOTE: I can make these if they're specifically occuring in some way be lexically invalid. Such at making just "!" invalid and not "!=".
                                       ' ', '\n', '\t', '_', // Ah yes, white space!
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
        final String keyWords[] = // Special words that are not symbols themselves.
                            { "program", "bool", "int", "if", "then", "else", "end", "while", "do", 
                              "print", "or", "mod", "and", "not", "false", "true"
                            };
        final String symbolTable[] = // Special Symbols or symbol combinations that have some special function. These should also separate lexemes...
                            { ":", ".", ",", ":=", "<", "=<", "=", "!=", ">=", ">", "+", "-", "*",
                              "/", ";", "(", ")",
                            }; 
        final String whiteSpace[] = // This stuff we use for white space.
                            { " ", "\n", "\t", // Our whitespace should be space, newline, and tab.
                                               // We shouldn't need to use this, this should mostly just be for reference here.
                            };                 // I apologize to those who care about efficiency.
        final String acceptableIDCharacters[] = // If we have an ID, then only these characters should be in it. So all letters, digits, and "_".
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
        final String numbers[] = // In case it's a number.
                        {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",

                        };
        final String letters[] = // In case it's a letter.
                        {// Letters, lower case.
                            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                         // Letters, upper case.
                            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                        };


        

        // I should probably just put all the lexeme related variables into an object and pass that object instead. But I can wait
        // on making that change until project 2 or something. As long as the code works and works clearly it should be fine.
                        

        // Scanner
        Scanner userInput = new Scanner(System.in); // Get User Input.

        
        

        while (doQuit != true) { // Main loop.

            // Variables and resets.

            // Character Input Related Variables
            String currentCharacter[] = new String[1]; // NOTE: not actually a character, but a string. We use an array here so that
                                                    // methods can change it without returning anything.
            int currentLine[] = new int[] {1}; // Line, array for changing. We start at 1 rather than 0.
            int currentColumn[] = new int[] {0}; // Column, array for changing.

            // File
            File userFile;
            FileInputStream userFileStream;
            currentCharacter[0] = "";

            // Lexeme stuff that needs to get reset.
            lexeme inLexeme[] = new lexeme[1]; // Our current lexeme.
            boolean didError[] = {false};
            String errorMessage[] = new String[1];
            boolean commenting[] = {false};
            boolean lexDone[] = {false};

            
            if (makeLikeExample == false) {
                System.out.println("Enter File Name, or type 'quit' or 'exit' to exit the program.");
            } else if (makeLikeExample == true) {
                System.out.println("Please enter a file to analyze (or quit):");
            }
            
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
 
            inLexeme[0] = new lexeme(userFileStream, currentCharacter, currentLine, currentColumn, symbolTable, numbers, letters);

            // Initial next() call to get the first lexeme.
            next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, true);
            //System.out.println("First lexeme read in"); // DEBUG
            program(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
            // Past here is where we should have different things for our project 2 code.
            /*
            if (makeLikeExample == false) {
                System.out.println( position(inLexeme) + " '" + kind(inLexeme) + "' " + value(inLexeme) );
            } else if (makeLikeExample == true) {
                System.out.println( position(inLexeme) + ":'" + kind(inLexeme) + "' " + value(inLexeme) );
            }
            */

            /*
            while ( lexDone[0] == false && didError[0] == false) {
                //System.out.println("Entered loop?"); Useful for debugging.
                next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
                if (didError[0] == true) {
                    System.out.println(errorMessage[0]);
                    continue; // Don't print the next thing.
                }
                if (makeLikeExample == false) {
                    System.out.println( position(inLexeme) + " '" + kind(inLexeme) + "' " + value(inLexeme) );
                } else if (makeLikeExample == true) {
                    System.out.println( position(inLexeme) + ":'" + kind(inLexeme) + "' " + value(inLexeme) );
                }
            }
            */
                

            userFileStream.close();

        }

        System.out.println("Thanks for using the program! Exiting."); // Exit message.
        userInput.close();


    }

    // Retrieves the File.
    private static File getFile(String getThisFile) throws IOException { // Get the file, if we don't have the file then throw the error.


        File theFile = new File(getThisFile);
        if (theFile.exists() == false) {
            throw new IOException("File does not exist.");
        }

        return theFile;

    }

    // Retrieves the next character.
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
        //System.out.println(currentCharacter[0] + " " + currentColumn[0] ); Useful debug. Use this if a position is off.

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

        // Variables
        String theLexeme;
        String theKind = "";
        String theValue;
        FileInputStream userFileStream;
        String currentCharacter[];
        int currentLine[];
        int currentColumn[];
        int lexLine;
        int lexColumn;
        String[] symbolTable;
        String[] numbers;
        String[] letters;

        lexeme(FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], String[] symbolTable, String[] numbers, String[] letters) { // constructor
            
            this.theLexeme = "";
            this.userFileStream = userFileStream;
            this.currentCharacter = currentCharacter;
            this.currentLine = currentLine;
            this.currentColumn = currentColumn;
            this.lexLine = currentLine[0];
            this.lexColumn = currentColumn[0];
            this.symbolTable = symbolTable;
            this.numbers = numbers;
            this.letters = letters;
        }
        
        // Adds a character to the lexeme.
        public void addCharacter(String addThis) {
            this.theLexeme = this.theLexeme.concat(addThis);
            if (this.theLexeme.length() == 1) { // If we just added the first character to the Lexeme, we need to see if we can determine it's kind.
                // First, check if it's an ID/keyword. If it can be a keyword it'll be caught later by the final check.
                for(String checkThis : letters) {
                    if (this.theLexeme.equals(checkThis) || this.theLexeme.equals("_")) {
                        this.updatelexemeKind("ID");
                        return;
                    }
                }
                // Next, check if it's a number.
                for(String checkThis : numbers) {
                    if (this.theLexeme.equals(checkThis)) {
                        this.updatelexemeKind("NUM");
                        return;
                    }
                }
                // Finally, could it be the start of an operator?
                // Don't worry about the kind, this will be caught before it hits the output by the final check.
                for(String checkThis : symbolTable) {
                    if (this.theLexeme.equals(String.valueOf(checkThis.charAt(0)))) {
                        this.updatelexemeKind("OP");
                        return;
                    }
                }
            }

            return;
        }

        // Wipes the lexeme out, leaving nothing behind.
        public void reset() {
            this.theLexeme = "";
        }

        // Good 'ol toString().
        public String toString() {
            return this.theLexeme;
        }

        // In case we need to change the position.
        public void updatePosition() {
            this.lexLine = currentLine[0];
            this.lexColumn = currentColumn[0];
        }

        // Initial position.
        public String position() {
            return this.lexLine + ":" + this.lexColumn;
        }

        // Initial lexeme line.
        public int line() {
            return this.lexLine;
        }

        // Initial lexeme column.
        public int column() {
            return this.lexColumn;
        } 

        // updates the kind of lexeme.
        public void updatelexemeKind(String kind) {
            this.theKind = kind;
        }

        // tells the kind of lexeme.
        public String lexemeKind() {
            return this.theKind;
        }

        public String lexemeValue() { // If it's an ID or a NUM, the value should change to that, otherwise it should stay blank...
            this.theValue = "";
            if (this.theKind.equals("ID") || this.theKind.equals("NUM")) {
                this.theValue = this.theLexeme;
            } 

            return this.theValue;
        }

        public void commentReshape(int commentStartAt) { // We use this in the case of a comment occuring to remove the //.
            this.theLexeme = this.theLexeme.substring(0, commentStartAt);
        }

        


    }

    // Gets the next lexeme for us.
    public static void next(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {
        
        inLexeme[0].reset(); // Reset the current lexeme.
        inLexeme[0].updatePosition(); // Update the position to the current starting position of the lexeme.
        inLexeme[0].updatelexemeKind("");
        boolean didWhiteSpace = false;
        boolean possibleComment = false;
        boolean didInterior = false;
        boolean needToUpdatePosition = false;
        boolean munchHalt = false;
        boolean commentCheck = false; // Added to fix the 'OP' comment issue.
        //System.out.println("We got here"); Debug
        while((didWhiteSpace != true && (currentCharacter[0].equals("End-Of-File") == false)) && didError[0] != true && munchHalt != true) { // Until the current lexeme is done, keep going.

            // Check if the character being put in is valid.
            if (commenting[0] == true && currentCharacter[0].equals("NewLine")) { // Newlines exit us from the comment mode.

                commenting[0] = false;
            }

            if (needToUpdatePosition == true) { // If there was a comment in the previous line, we need to update the position at the next non-white space.
                needToUpdatePosition = false;
                inLexeme[0].updatePosition();
            }

            if (commenting[0] == false) {
                


                /*if (currentCharacter[0].equals("")) { // Initial start of the file or special white space? Skip it!
                    nextChar(userFileStream, currentCharacter, currentLine, currentColumn); // Get the initial character.
                    if (isFirst == true && (currentCharacter[0].equals(" ") == true)) { // Boring, hacky way that changes a number caused by an inconsequential
                                                                                                 // bug that has taken me more time to try to fix than it took to write this code.
                                                                                                 // It was faster to do this than to continue to try and fix the issue.
                                                                                                 // Is it inefficient? Yes. Do I hate it? Also yes. Will this be the same in the final project for the semester?
                                                                                                 // I certainly hope so! If I couldn't figure it out today, then I'm probably incapable of figuring it out period.
                        currentColumn[0] = currentColumn[0] - 1;                                 // My hopes have been quashed, as mere minutes later this bug was fixed.
                        inLexeme[0].updatePosition();
                    }
                } */


                // nevermind this bug gets fixed by the munchCheck helper method...
                // Just goes to show to not worry about doing hacky things to try and fix your code
                // until after you have all the required implementation working.



                // Hit a whitespace? We should then skip all forwards whitespace and then
                if (currentCharacter[0].equals("") == true || currentCharacter[0].equals(" ") == true || currentCharacter[0].equals("NewLine") == true || currentCharacter[0].equals("Carriage") || currentCharacter[0].equals("\t") == true /*|| (currentCharacter[0].equals("End-Of-File") == true)*/) {
                    didWhiteSpace = true;
                    while (currentCharacter[0].equals("") == true || currentCharacter[0].equals(" ") == true || currentCharacter[0].equals("NewLine") == true || currentCharacter[0].equals("Carriage") || currentCharacter[0].equals("\t") == true /*|| (currentCharacter[0].equals("End-Of-File") == true)*/) { // Skip the whitespace.
                        nextChar(userFileStream, currentCharacter, currentLine, currentColumn);
                    }
                    if (inLexeme[0].toString().equals("")) {
                        didWhiteSpace = false;
                        needToUpdatePosition = true;
                    }
                    continue; // Finished with whitespace? We're done!
                } 
                // Maximum Munch Check.
                if (munchCheck(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == true && inLexeme[0].toString().length() > 0) {
                //if (currentCharacter[0].equals(";") && inLexeme[0].toString().length() > 0) { // Debug. I won't take this comment out because of sentimental reasons.
                    munchHalt = true;
                    continue;
                }
                if (munchHalt == true) { // We need this for further loops.
                    continue;
                }

                    if (checkCurrent(currentCharacter[0], acceptableCharacters) == false) {
                        didError[0] = true;
                        //System.out.println(didError);
                        if (makeLikeExample == false) {
                            errorMessage[0] = ("Unacceptable Character '" + currentCharacter[0] + "' at: " + currentLine[0] + ":" + currentColumn[0]);
                        } else if (makeLikeExample == true) {
                            errorMessage[0] = currentLine[0] + ":" + currentColumn[0] + ":>>>>> Illegal character " + currentCharacter[0];
                        }
                        continue;
                    }
                
                    // Not a whitespace? Put it in!
                    if (possibleComment == true && currentCharacter[0].equals("/")) {
                        commenting[0] = true;

                        // More useful debug.
                        //System.out.println("We got here");
                        //System.out.println(inLexeme[0].toString().length());
                        //System.out.println(inLexeme[0].toString());

                        if (inLexeme[0].toString().length() > 1) { // If there's no white space and a //, remove the // and leave the lexeme.
                            inLexeme[0].commentReshape(inLexeme[0].toString().length() - 1);
                        } else { // Otherwise, there is no lexeme, remove the // also.
                            inLexeme[0].reset();
                            didWhiteSpace = false;
                        }
                    } else if (possibleComment == false && currentCharacter[0].equals("/")) {
                        possibleComment = true;
                        inLexeme[0].addCharacter(currentCharacter[0]);
                    } else { // Not commenting? Go ahead then.
                        inLexeme[0].addCharacter(currentCharacter[0]);
                        possibleComment = false;
                    }
                nextChar(userFileStream, currentCharacter, currentLine, currentColumn);


            } else {
                didWhiteSpace = false;
                nextChar(userFileStream, currentCharacter, currentLine, currentColumn);
                commentCheck = true;
            }
            if (commentCheck == false) { // Alright, so this part and the else statement ensure that if a comment occurs we skip to the next part. This fixes the 'OP' issue.
                didInterior = true;      // Solving the 'OP' issue here has 2 after effects. First: If there's nothing but white space after, the end of text omits the continuing white space, which is fine.
            } else {                     // Second, if there is nothing but a comment, the end of text treats the existence of the comment as a non-omitted white space, which should also be fine.
                commentCheck = false;
                didInterior = false;
            }
        } 
        
        //So we have our full complete lexeme. Now we need to check if it's lexically correct. Lets hand that off to another helper method.
        if (inLexeme[0].toString().length() > 0) {
            if (finalLexicalCheck(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers) == false ) {
                didError[0] = true;
            } 
        }
        if (currentCharacter[0].equals("End-Of-File") && didInterior == false) {
            inLexeme[0].addCharacter("End-Of-File");
            inLexeme[0].updatelexemeKind("end-of-text"); // If this wasn't explicitly required in the instructions I would have it as "End-Of-File".
            //in
            lexDone[0] = true;
        } 

    }

    public static boolean checkCurrent(String theChar, char[] acceptArray) { // Returns true if the char is acceptable, otherwise, return false.

        for (char checkThis : acceptArray) {
            //System.out.println("Does " + theChar + " equal " + Character.toString(checkThis)); // Debug for finding issues.
            if (theChar.equals("") || theChar.equals("Carriage") || theChar.equals("NewLine") || theChar.equals("End-Of-File") || (theChar.equals(String.valueOf(checkThis)))) {
                return true;
            }
        }

        return false;
    }

    // This should check the final form of our most powerful lexemes!
    // Returns true if it's fine, and false if there's an error.
    public static boolean finalLexicalCheck(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String keyWords[], String symbolTable[], String accaptableIDCharacters[], String numbers[] ) {

        for (String checkThis : keyWords) { // First, we check if this one of our keywords.
            if (inLexeme[0].toString().equals(checkThis)) {
                inLexeme[0].updatelexemeKind(checkThis);
                return true;
            }
        }

        for (String checkThis : symbolTable) { // Then we check if it's in the symbol table.
            if (inLexeme[0].toString().equals(checkThis)) {
                inLexeme[0].updatelexemeKind(checkThis);
                return true;
            }
        }

        // Otherwise we need to check if it's a number or a letter.
        // So, if the first letter is a number, assume it is a number, and then check if all the parts are numbers.
        boolean isANum = false;
        for (String checkThis : numbers) {
            if (inLexeme[0].toString().substring(0, 1).equals(checkThis)) {
                isANum = true;
                inLexeme[0].updatelexemeKind("NUM");
                continue;
            }
        }
        
        // If it's a number, make sure it meets the only digits specification. Otherwise, assume it is an ID and make sure it meets that.
        // It's easiest to check for it being a number first since we know numbers always start with a number, according to our grammar.
        if (isANum == true) {
            for (int i = 0; i < inLexeme[0].toString().length(); i++) {
                boolean noError = false;
                for (String checkThis : numbers) {
                    if (inLexeme[0].toString().substring(i, i + 1).equals(checkThis)) {
                        noError = true;
                        continue;
                    }
                }
                if (noError == false) {
                    if (makeLikeExample == false) {
                    errorMessage[0] = "Unacceptable character for a NUM " + inLexeme[0].toString().substring(i, i + 1) + " at postition: " + inLexeme[0].line() + ":" + (inLexeme[0].column() + i);
                    } else if (makeLikeExample == true) {
                    errorMessage[0] = inLexeme[0].line() + ":" + (inLexeme[0].column() + i) + ":>>>>> Illegal character " + inLexeme[0].toString().substring(i, i + 1); 
                    }
                    return false;
                }
            }
        } else {
            inLexeme[0].updatelexemeKind("ID");
            for (int i = 0; i < inLexeme[0].toString().length(); i++) {
                boolean noError = false;
                for (String checkThis : accaptableIDCharacters) {
                    if (inLexeme[0].toString().substring(i, i + 1).equals(checkThis) && !(inLexeme[0].toString().substring(0, 1).equals("_"))) {
                        noError = true;
                        continue;
                    }
                }
                //if (inLexeme[0].toString().substring(0, 0 + 1).equals("_")) { // Checks if the first character of the ID is "_".
                //    noError = true;
                //}
                if (noError == false) {
                    if (makeLikeExample == false) {
                        errorMessage[0] = "Unacceptable character found " + inLexeme[0].toString().substring(i, i + 1) + " at postition: " + inLexeme[0].line() + ":" + (inLexeme[0].column() + i);
                    } else if (makeLikeExample == true) {
                        errorMessage[0] = inLexeme[0].line() + ":" + (inLexeme[0].column() + i) + ":>>>>> Illegal character " + inLexeme[0].toString().substring(i, i + 1); 
                    }
                    return false;
                }
            }
        }

        
        // Everything went fine and it is an ID? Good job! We're done here so return true.
        return true;
    }

    // Checks if we need to apply maximum munch.
    public static boolean munchCheck(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) { // Return true if we need to move over for munch, otherwise return false.
        if (inLexeme[0].lexemeKind().equals("ID")) {
            for (String checkThis : acceptableIDCharacters) {
                if  (currentCharacter[0].equals(checkThis)) { // Next character is suitable for ID? Go ahead.
                    return false;
                }
            }
        }
        if (inLexeme[0].lexemeKind().equals("NUM")) {
            for (String checkThis : numbers) {
                if  (currentCharacter[0].equals(checkThis)) { // Next character is suitable for NUM? Go ahead.
                    return false;
                }
            }
        }
        if (inLexeme[0].lexemeKind().equals("OP")) {
            for (String checkThis : symbolTable) {
                for (int i = 0; i < checkThis.length(); i++) {
                    if  (currentCharacter[0].equals(String.valueOf(checkThis.charAt(i)))) { // Next character is suitable for the Symbol Table? Go ahead. Unless...
                        if (inLexeme[0].toString().length() >= 2) { // Is the symbol longer than 2? Munch it!
                            return true;
                        }
                        if (inLexeme[0].toString().equals(currentCharacter[0]) && (currentCharacter[0].equals("/") == false)) { // Repeating symbol? Munch it!... Unless it's a comment.
                            return true;
                        }
                        return false;
                    }
                }
            }
            
        }
        

        return true;
    }

    public static String kind(lexeme[] inLexeme) { // Required in the instructions.
        return inLexeme[0].lexemeKind();
    }

    public static void setKind(String kindOf, String[] kind) { // Use this to set the kind of the lexeme.
        kind[0] = kindOf;
    }

    public static String value(lexeme[] inLexeme) { // Required in the instructions.
        return inLexeme[0].lexemeValue();
    }    

    public static String position(lexeme[] inLexeme) { // Required in the instructions.
        return inLexeme[0].position();
    }

    // Project 2 methods start here.

    // This section is for each individual case. You'll find the match and assert functions further below.
    // Note: Just Program will not return anything, but we could change that in the future if it needs to be a standalone method.
    // For the general procedures, use this for returning if they failed:
    /*
    if (
        [Put your procedure here.]
    ) {
        return false;
    } 
    
     */


    // Initial program procedure.
    public static void program(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        // The following chunk of code is effectively:
        //match("program")
        //match("ID")
        //match(":")
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "program") == false) { // Is the current lexeme "program"?
            //System.out.println("Error! Expected \"program\"");
            return;
        }
        if (matchKind(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "ID") == false) { // Is the current lexeme an Identifier?
            //System.out.println("Error! Expected an Identifier");
            return;
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, ":") == false) {
            //System.out.println("Error! Expected \":\"");
            return;
        }
        // Debug: System.out.println("Holy crap we actually got here");

        // Calls the body procedure. If it returns false then something went wrong and we stop it there.
        /*
        if ( // Slightly different format here to make sure I see and change all the procedures.
        body(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return; // If program is to be standalone make it return bool and change this and the other error returns to return false.
        }
        */
        next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);

        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, ".") == false) {
            //System.out.println("Error! Expected \".\"");
            return;
        }

        System.out.println("No issues found."); // Print this if and only if the thing is syntaxically valid.
        return; // If to be standalone change this to return true.
    }

    // Body procedure.
    // TODO: Termination if error. May require changing return type to boolean.
    public static boolean body(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {
        // "if csymb() is bool or int"
        if ((inLexeme[0].toString().equals("bool") == true) || (inLexeme[0].toString().equals("int") == true)) { // If we don't require it to be an int or bool, do it like this.
            if (
            declarations(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }

        if (
        statements(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }

        return true;
    }

    public static boolean declarations(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {
        
        if (
        declaration(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }

        while ((inLexeme[0].toString().equals("bool") == true) || (inLexeme[0].toString().equals("int") == true)) {
            if (
            declaration(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }

        return true;
    }

    public static boolean declaration(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {
        if ((assertActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "bool") || assertActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "int")) == false) {
            didError[0] = true;
            System.out.printf("Error at position %s with symbol \"%s\", Expected bool or int\n", inLexeme[0].position(), inLexeme[0].toString());
            return false;
        }
        next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
        if (matchKind(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "ID") == false) { // Is the current lexeme an Identifier?
            //System.out.println("Error! Expected an Identifier");
            return false;
        }

        while ((inLexeme[0].toString().equals("'") == true)) {
            next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
            if (matchKind(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "ID") == false) { // Is the current lexeme an Identifier?
                //System.out.println("Error! Expected an Identifier");
                return false;
            }
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, ";") == false) { // Is the current lexeme an Identifier?
            //System.out.println("Error! Expected a \";\"");
            return false;
        }

        return true;
    }

    public static boolean statements(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException{
        if (
            statement(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }
        
        while (inLexeme[0].toString().equals(";") == true) {
            if (
                statement(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }


        return true;
    }

    public static boolean statement(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        // Do this here because why not.
        if ((assertKind(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "ID") || assertActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "if") || assertActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "while") || assertActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, isFirst, "print")) == false) {
            didError[0] = true;
            System.out.printf("Error at position %s with symbol \"%s\", Expected ID, \"if\", \"while\", or \"print\"\n", inLexeme[0].position(), inLexeme[0].toString());
            return false;
        }
        if (inLexeme[0].lexemeKind().equals("ID")) {
            if (
                assignment(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }
        if (inLexeme[0].toString().equals("if")) {
            if (
                conditional(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }
        if (inLexeme[0].toString().equals("while")) {
            if (
                iterative(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }
        if (inLexeme[0].toString().equals("print")) {
            if (
                print(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }

        return true;
    }

    public static boolean assignment(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        if (matchKind(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "ID") == false) {
            return false;
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, ":=") == false) {
            return false;
        }
        if (
            expression(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }

        return true;
    }

    public static boolean conditional(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "if") == false) {
            return false;
        }
        if (
            expression(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "then") == false) {
            return false;
        }
        if (
            body(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }
        if ((inLexeme[0].toString().equals("else") == true)) { // If we don't require it to be an int or bool, do it like this.
            if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "else") == false) {
                return false;
            }
        if (
            body(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
            ) {
                return false;
            }
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "end") == false) {
            return false;
        }



        return true;
    }

    public static boolean iterative(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "while") == false) {
            return false;
        }
        if (
            expression(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "do") == false) {
            return false;
        }
        if (
            body(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false) == false
        ) {
            return false;
        }
        if (matchActual(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false, "end") == false) {
            return false;
        }
        


        return true;
    }

    public static boolean print(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        return true;
    }

    public static boolean expression(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst) throws IOException {

        return true;
    }

    // This section includes the match functions and assert functions.

    // What match needs to do is basically take the lexeme and check if the value in the lexeme is equal to the string we hand to it.
    public static boolean matchActual(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst, String matchThis) throws IOException { // This checks if something is specifically something

        if (didError[0] == true) { // This is solely for printing errors if the lexical analyzer hit an error.
            System.out.println(errorMessage[0]);
            return false;
        }

        if (inLexeme[0].toString().equals(matchThis)) { // Does the lexeme's toString match the matchThis?
            // If it does match, move the lexeme to the next thing we'll be looking at.
            next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
            return true;
        }

        System.out.printf("Error at position %s with symbol \"%s\", Expected \"%s\"\n", inLexeme[0].position(), inLexeme[0].toString(), matchThis); // Moving this here for ease of use.
        return false;
    }

    // Genuinely I mostly only use this for checking for Identifiers and Numbers.
    public static boolean matchKind(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst, String matchThis) throws IOException { // This checks if something is a certain kind.
        
        if (didError[0] == true) {
            System.out.println(errorMessage[0]);
            return false;
        }

        if (inLexeme[0].lexemeKind().equals("ID") && inLexeme[0].lexemeKind().equals(matchThis)) {
            // Is it an ID?
            next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
            return true;
        }

        if (inLexeme[0].lexemeKind().equals("NUM") && inLexeme[0].lexemeKind().equals(matchThis)) {
            // Is it a NUM?
            next(inLexeme, userFileStream, currentCharacter, currentLine, currentColumn, acceptableCharacters, didError, errorMessage, keyWords, symbolTable, acceptableIDCharacters, numbers, letters, commenting, lexDone, false);
            return true;
        }

        System.out.printf("Error at position %s with symbol \"%s\", Expected \"%s\"\n", inLexeme[0].position(), inLexeme[0].toString(), matchThis); // Same as above.
        return false;
    }

    // Similar to matchActual but doesn't call next() if it's true. Error messages for if an assert comes back negative need to be set manually.
    public static boolean assertActual(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst, String matchThis) throws IOException { // This checks if something is specifically something, but doesn't move on to the next if it's true.

        if (didError[0] == true) {
            System.out.println(errorMessage[0]);
            return false;
        }

        if (inLexeme[0].toString().equals(matchThis)) { // Does the lexeme's toString match the matchThis?
            // If it does match, move the lexeme to the next thing we'll be looking at.
            return true;
        }

        // No error prining here because 
        return false;
    }

    // Same boat as assertActual. But for kinds.
    public static boolean assertKind(lexeme[] inLexeme, FileInputStream userFileStream, String currentCharacter[], int currentLine[], int currentColumn[], char acceptableCharacters[], boolean didError[], String errorMessage[], String[] keyWords, String[] symbolTable, String[] acceptableIDCharacters, String[] numbers, String[] letters, boolean[] commenting, boolean[] lexDone, boolean isFirst, String matchThis) throws IOException { // This checks if something is a certain kind.
        
        if (didError[0] == true) {
            System.out.println(errorMessage[0]);
            return false;
        }

        if (inLexeme[0].lexemeKind().equals("ID") && inLexeme[0].lexemeKind().equals(matchThis)) {
            // Is it an ID?
            return true;
        }

        if (inLexeme[0].lexemeKind().equals("NUM") && inLexeme[0].lexemeKind().equals(matchThis)) {
            // Is it a NUM?
            return true;
        }

        return false;
    }
    

}
