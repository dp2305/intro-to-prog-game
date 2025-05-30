package utils;

/**
 * The {@code Formatting} class is used to enhance readability through the use of colors in the text.
 * It contains methods for printing text and managing the console's layout.
 */
public class Formatting {

   // ANSI codes for color - better readability in the console
   public static final String RESET = "\u001B[0;49m";
   public static final String RED = "\u001B[91m";
   public static final String GREEN = "\u001B[92m";
   public static final String YELLOW = "\u001B[93m";
   public static final String BLUE = "\u001B[94m";

   /**
    * A method that prints text to the console, shorter than the default print method to keep the code cleaner and simpler to read.
    *
    * @param text the text to print
    */
   public static void print(String text) {
      System.out.print(text);
   }

   /**
    * A method that prints the text in the specified color.
    * @param colour The color to apply to the text
    * @param text   The text to print
    */
   public static void print(String colour, String text) {
      System.out.print(colour + text + RESET);
   }

   /**
    * Returns a string with a colour applied to the text.
    *
    * @param text   The text that needs the colour
    * @param colour The color to apply to the text
    *
    * @return a string with the colour applied to the text
    */
   public static String colour(String colour, String text) {
      return String.format("%s%s%s", colour, text, RESET);
   }

   /**
    * Prints a single line break.
    */
   public static void lineBreak() {
      System.out.println();
   }

   /**
    * Prints multiple line breaks.
    *
    * @param lineCount The number of line breaks to create
    */
   public static void lineBreak(int lineCount) {
      for (int i = 0; i < lineCount; i++) {
         System.out.println();
      }
   }

   /**
    * Clears the specified number of lines from the console output.
    * Useful for removing previous messages or cleaning up the display.
    *
    * @param clearLineCount The number of lines to clear
    */
   public static void clearLine(int clearLineCount) {
      for (int i = 0; i < clearLineCount; i++) {
         System.out.print("\033[1A\033[2K");
      }
   }

   /**
    * A spacer for separating content in the console.
    */
   public static void printSpacer() {
      print(GREEN, "            ----------------------------");
      lineBreak(2);
   }
}