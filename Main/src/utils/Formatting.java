package utils;

/**
 * Formatting class enhances readability through the use of colors in the text.
 * This class is not meant to be instantiated.
 * Contains static methods and data
 * Has methods to print formatted text and managing console layout.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 */

public class Formatting {

   /** ANSI reset code to clear all text formatting. */
   public static final String ANSI_RESET = "\u001B[0;49m";

   /** ANSI code for red coloured text. */
   public static final String ANSI_TEXT_RED = "\u001B[91m";
   /** ANSI code for green coloured text. */
   public static final String ANSI_TEXT_GREEN = "\u001B[92m";
   /** ANSI code for yellow coloured text. */
   public static final String ANSI_TEXT_YELLOW = "\u001B[93m";
   /** ANSI code for blue coloured text. */
   public static final String ANSI_TEXT_BLUE = "\u001B[94m";

   /**
    * Prints text without a newline.
    * @param text the text to print
    */


   public static void print(String text) {
      System.out.print(text);
   }

   /**
    * Prints the given text in the specified color, followed by a reset to default formatting.
    * @param text   the text to print
    * @param colour the ANSI color code to apply to the text
    */
   public static void printColour(String text, String colour) {
      System.out.print(colour + text + ANSI_RESET);
   }

   /**
    * Prints a single line break.
    */

   public static void lineBreak() {
      System.out.println();
   }

   /**
    * Prints multiple line breaks.
    * @param lineCount the number of blank lines to print
    */

   public static void lineBreak(int lineCount) {
      for (int i = 0; i < lineCount; i++) {
         System.out.println();
      }
   }

   /**
    * Clears the specified number of lines from the console output.
    * Useful for removing previous messages or cleaning up the display.
    * @param clearLineCount the number of lines to clear
    */

   public static void clearLine(int clearLineCount) {
      for (int i = 0; i < clearLineCount; i++) {
         System.out.print("\033[1A\033[2K");
      }
   }

   /**
    * Prints a green-colored visual spacer for separating content in the console.
    * Also adds two blank lines after the spacer.
    */

   public static void printSpacer() {
      printColour("            ----------------------------", ANSI_TEXT_GREEN);
      lineBreak(2);
   }
}