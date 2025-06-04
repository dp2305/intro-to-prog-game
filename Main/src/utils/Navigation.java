package utils;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * The {@code Navigation} class handles the player's location throughout the game map in a 5x5 grid system.
 * Each tile corresponds to a location with a name, description, and other data such as if the player has already searched the location, if the location has a combat encounter, and if the location has a items.
 */
public class Navigation {

   private static final Scanner sc = new Scanner(System.in);

   // Player's current location and coordinates
   private static int currentLocation = 00;
   private static int playerX, playerY;
   private static int characterLocationIndex;

   // Location names
   private static final String[] locationNames = {
      "Main Base",       "Main Base",       "River",      "Destroyed Lab",  "Destroyed Lab",
      "Main Base",       "Forest",          "River",      "Burnt Forest",   "Burnt Forest",
      "Forest",          "Forest",          "River",      "River",          "Abandoned Camp",
      "Forest",          "Forest",          "Mountains",  "River",          "Mountains",
      "Abandoned Camp",  "Abandoned Camp",  "Caves",      "Mountains",      "Mountains"
   };

   // Different boolean arrayes used to keep track of the 'features' at that location - player may have visited the location and searched it but did not encounter an enemy
   // Whether the player has rested at the location
   private static final boolean[] restedLocations = {
      false, false, true,  false, false,
      false, false, true,  false, false,
      false, false, true,  true,  false,
      false, false, false, true,  false,
      false, false, false, false, false
   };

   // Whether the location has searchable items - 0 = no items, 1 = searchable, 2 = story item search, 3 = npc location,9 = already searched
   private static final int[] locationSearchIndex = {
      2, 1, 0, 2, 3,
      1, 0, 0, 2, 0,
      2, 1, 0, 0, 2,
      0, 0, 1, 2, 1,
      2, 1, 2, 0, 2
   };

   // Whether the location has a combat encounter
   private static final boolean[] locationHasCombat = {
      false, false, true, false, false,
      false, true,  true, true,  true,
      true,  true,  true, true,  true,
      true,  true,  true, true,  true,
      true,  true,  true, true,  true
   };

   // The maximum number of possible combat encounters at the location
   private static final int[] possibleCombatEncounters = {
      0, 0, 3, 0, 0,
      0, 1, 3, 1, 1,
      1, 1, 3, 3, 2,
      1, 1, 2, 2, 2,
      1, 2, 2, 2, 2
   };

   // Descriptions of the locations
   private static final String[] locationDescriptions = {
      "There might be some useful items here.",
      "There might be some useful items here.",
      "This rivers currents are too harsh, it's dangerous.",
      "This is where the scientists were working, they must be around here.",
      "This is where the scientists were working, they must be around here.",

      "There might be some useful items here.",
      "The forest is dense, and the trees are tall. There might be something lurking here.",
      "This rivers currents are too harsh, it's dangerous.",
      "Its all burnt down. There might be something lurking here.",
      "Its all burnt down. There might be something lurking here.",

      "It looks very dense. There might be something lurking here.",
      "It looks very dense. There might be something lurking here.",
      "This rivers currents are too harsh, it's dangerous.",
      "This rivers currents are too harsh, it's dangerous.",
      "It seems dangerous here, but it might have something useful.",

      "It looks very dense. There might be something lurking here.",
      "It looks very dense. There might be something lurking here.",
      "The terrain is very rough. There might be something lurking here.",
      "This rivers currents are too harsh, it's dangerous.",
      "The terrain is very rough. There might be something lurking here.",

      "It seems dangerous here, but it might have something useful.",
      "It seems dangerous here, but it might have something useful.",
      "Its dark and wet here. There might be something lurking here.",
      "The terrain is very rough. There might be something lurking here.",
      "The terrain is very rough. There might be something lurking here.",
   };


   /**
    * Handles player navigation by taking input directions and updating the player's location on a 5x5 grid map.
    * Prints the map, current location, and movement options to the console.
    * Triggers combat if the new location has a combat encounter.
    *
    * @param player The player that is navigating
    */
   public static void navigate(Player player) {
      boolean isValidChoice = true;

      // Calculate the coordinates and relative index of the player's location
      playerX = currentLocation % 5;
      playerY = currentLocation / 5;
      characterLocationIndex = playerY * 5 + playerX;

      // Print the current location and player options
      print("You are currently in the " + colour(YELLOW, locationNames[characterLocationIndex]) + ", where do you want to go?");
      lineBreak();

      // Print the map
      map();

      // Print the player's movement options
      print(BLUE, "'W' - Up, 'A' - Left, 'S' - Down, 'D' - Right, '0' - Go Back");
      lineBreak();

      while (isValidChoice) {
         // Get the player's movement direction
         print(GREEN, " > ");
         String movementDirection = sc.nextLine().toUpperCase();

         if (movementDirection.isEmpty()) {
               clearLine(1);
         } else {
            char movementDirectionChar = movementDirection.charAt(0);

            // Move the player or take them back to the player options
            switch (movementDirectionChar) {
               case 'W' -> {
                  if (playerY != 0) {
                     currentLocation -= 5;
                     isValidChoice = false;
                  } else {
                     clearLine(1);
                     continue;
                  }
               }
               case 'A' -> {
                  if (playerX != 0) {
                     currentLocation -= 1;
                     isValidChoice = false;
                  } else {
                     clearLine(1);
                     continue;
                  }
               }
               case 'S' -> {
                  if (playerY != 4) {
                     currentLocation += 5;
                     isValidChoice = false;
                  } else {
                     clearLine(1);
                     continue;
                  }
               }
               case 'D' -> {
                  if (playerX != 4) {
                     currentLocation += 1;
                     isValidChoice = false;
                  } else {
                     clearLine(1);
                     continue;
                  }
               }
               case '0' -> {
                  isValidChoice = false;
               }
               default -> {
                  clearLine(1);
                  continue;
               }
            }

            // Calculate the new relative index of the player's location
            playerX = currentLocation % 5;
            playerY = currentLocation / 5;
            characterLocationIndex = playerY * 5 + playerX;

            // Remove the previous map and print the updated map
            clearLine(14);
            map();

            // Print the player's new location
            print("You are now in the " + colour(YELLOW, locationNames[characterLocationIndex]) + ". \"" + locationDescriptions[characterLocationIndex] + "\"");
            lineBreak();

            // Check if the location has combat
            if (locationHasCombat[characterLocationIndex]) {
               Combat.combat(player);
            }
         }
      }
   }

   /**
    * Prints the 5x5 grid map to the console showing the player's current position, nearby locations on the map.
    */
   public static void map() {
      // Calculate player's current location
      playerX = currentLocation % 5;
      playerY = currentLocation / 5;

      // Print the map
      for (int y = 0; y < 5; y++) {
         print("+---+---+---+---+---+");
         lineBreak();
         for (int x = 0; x < 5; x++) {
            print("| ");
            if (playerY == y && playerX == x) {
               print(GREEN, "P "); // Player's current location
            } else if ((playerY == y && (playerX == x - 1 || playerX == x + 1)) || (playerX == x && (playerY == y - 1 || playerY == y + 1))) {
               print(YELLOW, "O "); // Travelable locations
            } else {
               print(RED, "- "); // Non-travelable locations
            }
         }
         print("|");
         lineBreak();
      }
      print("+---+---+---+---+---+");
      lineBreak();
   }

   /**
    * Displays the map of the player's location and the map required for the objective of the game.
    */
   public static void viewCombinedMap() {
      // Calculate player's current location
      playerX = currentLocation % 5;
      playerY = currentLocation / 5;

      // Print the map
      for (int y = 0; y < 5; y++) {
         print("+---+---+---+---+---+               +---+---+---+---+---+");
         lineBreak();
         for (int x = 0; x < 5; x++) {
            print("| ");
            if (playerY == y && playerX == x) {
               print(GREEN, "P "); // Player's current location
            } else if ((playerY == y && (playerX == x - 1 || playerX == x + 1)) || (playerX == x && (playerY == y - 1 || playerY == y + 1))) {
               print(YELLOW, "O "); // Travelable locations
            } else {
               print(RED, "- "); // Non-travelable locations
            }
         }
         print("|               ");
         for (int x = 0; x < 5; x++) {
            print("| ");
            int locationIndex = y * 5 + x;
            switch (locationSearchIndex[locationIndex]) {
               case 2 -> {
                  print(RED, "X "); // Unsearched story item locations
               }
               case 3 -> {
                  print(BLUE, "X "); // NPC location
               }
               case 8 -> {
                  print(GREEN, "X "); // Searched story item locations
               }
               default -> {
                  print("  ");
               }
            }
         }
         print("|");
         lineBreak();
      }
      print("+---+---+---+---+---+               +---+---+---+---+---+");
      lineBreak();
   }

   /**
    * Displays the map required for the objective of the game.
    */
   public static void viewMissionMap() {
      // Print the map for the mission
      for (int y = 0; y < 5; y++) {
         print("+---+---+---+---+---+");
         lineBreak();
         for (int x = 0; x < 5; x++) {
            print("| ");
            int index = y * 5 + x;
            switch (locationSearchIndex[index]) {
               case 2 -> {
                  print(RED, "X "); // Unsearched story item locations
               }
               case 3 -> {
                  print(BLUE, "X "); // NPC location
               }
               case 8 -> {
                  print(GREEN, "X "); // Searched story item locations
               }
               default -> {
                  print("  ");
               }
            }
         }
         print("|");
         lineBreak();
      }
      print("+---+---+---+---+---+");
      lineBreak();
   }

   /**
    * Checks if the player is able to rest at the location.
    *
    * @return True if the player is able to rest at the location, otherwise false
    */
   public static boolean getRestedLocations() {
      return restedLocations[characterLocationIndex];
   }

   /**
    * Checks if the player has searched the location.
    *
    * @return The index of the location search
    */
   public static int getLocationSearchIndex() {
      return locationSearchIndex[characterLocationIndex];
   }

   /**
    * Checks the possible encounters the player can have at the location.
    *
    * @return The number of possible encounters
    */
   public static int getPossibleEncounters() {
      return possibleCombatEncounters[characterLocationIndex];
   }

   /**
    * Updates the search index to indicate that it has already been searched.
    *
    * @param setIndex The index to set the location search index to
    */
   public static void updateLocationSearchIndex(int setIndex) {
      // Index of 8 for searched story items and 9 for searched normal items
      locationSearchIndex[characterLocationIndex] = setIndex;
   }

   /**
    * Updates the rested locations.
    */
   public static void updateRestedLocations() {
      restedLocations[characterLocationIndex] = false;
   }

   /**
    * Updates the location fight status.
    */
   public static void updateLocationFightStatus() {
      locationHasCombat[characterLocationIndex] = false;
   }
}