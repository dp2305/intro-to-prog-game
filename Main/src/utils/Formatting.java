package utils;

/**
 * Formatting class enhances readability through the use of colors in the text.
 * This class is not meant to be instantiated.
 * Contains static methods and data
 * Has methods to print formatted text and managing console layout.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 * @version ?????
 */

public class Formatting {

   /** ANSI reset code to clear all text formatting. */
   public static final String ANSI_RESET = "\u001B[0;49m";

   /** ANSI code for red-coloured text. */
   public static final String ANSI_TEXT_RED = "\u001B[91m";
   /** ANSI code for green-coloured text. */
   public static final String ANSI_TEXT_GREEN = "\u001B[92m";
   /** ANSI code for yellow-coloured text. */
   public static final String ANSI_TEXT_YELLOW = "\u001B[93m";
   /** ANSI code for blue-coloured text. */
   public static final String ANSI_TEXT_BLUE = "\u001B[94m";

   /**
    * Prints the given text without a newline.
    *
    * @param text the text to print
    */

   // Printing methods created to simplify coding and reading text-heavy sections of code

   // Method to print text
   public static void print(String text) {
      System.out.print(text);
   }
   // Method to print colourful text
   public static void printColour(String text, String colour) {
      System.out.print(colour + text + ANSI_RESET);
   }

   // Method to print a line break
   public static void lineBreak() {
      System.out.println();
   }

   // Method to print multiple line breakss
   public static void lineBreak(int lineCount) {
      for (int i = 0; i < lineCount; i++) {
         System.out.println();
      }
   }

   // Method to clear line(s) for a cleaner console output, e.g. after an error message is printed
   public static void clearLine(int clearLineCount) {
      for (int i = 0; i < clearLineCount; i++) {
         System.out.print("\033[1A\033[2K");
      }
   }

   public static void printSpacer() {
      printColour("            ----------------------------", ANSI_TEXT_GREEN);
      lineBreak(2);
   }
}