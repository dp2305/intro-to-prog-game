import java.util.Scanner;
import utils.*;
import static utils.Formatting.*;

public class Main {
   // Scanner and Random objects
   private static final Scanner sc = new Scanner(System.in);

   // Global input validation variables
   private static boolean primaryChoiceHandler, secondaryChoiceHandler;

   public static void main(String[] args) {
      boolean gameRunning = true;
      boolean repeatOptions = true;
      boolean printSeperator = true;

      printGameTutorial();

      do {
         // Generate a new player at the start of the while loop so that all player information is reset
         final Player playerCharacter = new Player(100, true); // Player is created without a name as it will be set later
         promptPlayerName(playerCharacter);
         displayMissionInformation(playerCharacter);
         playerWeaponSelection(playerCharacter);
         playerItemSelection(playerCharacter);

         do {
            // Recalculate player weight before each action
            playerCharacter.calculateWeight();

            primaryChoiceHandler = true;
            secondaryChoiceHandler = true;

            print("What would you like to do? " + colour(BLUE, "(1) Use item, (2) Move Around, (3) View Maps, (4) Search Location, (5) Rest, (6) Check Character"));
            lineBreak();
            print(GREEN, " > ");
            String choice = sc.nextLine().toUpperCase();

            if (choice.isEmpty()) {
               printSeperator = false;
               clearLine(2);
            } else {
               int choiceInt;

               try {
                  choiceInt = Integer.parseInt(choice);
               } catch (NumberFormatException e) {
                  choiceInt = 0;
               }

               switch (choiceInt) {
                  // Use item
                  case 1 -> {
                     print(YELLOW, "<=-- Using Item --=>");
                     lineBreak(2);
                     Items.useItem(playerCharacter);
                  }
                  // Move
                  case 2 -> {
                     print(YELLOW, "<=-- Moving --=>");
                     lineBreak(2);
                     Navigation.navigate(playerCharacter);
                  }
                  // View Map
                  case 3 -> {
                     print(YELLOW, "<=-- Viewing Maps --=>");
                     lineBreak(2);
                     Navigation.viewCombinedMap();
                  }
                  // Search
                  case 4 -> {
                     print(YELLOW, "<=-- Searching Location --=>");
                     lineBreak(2);
                     playerCharacter.search();
                  }
                  // Rest
                  case 5 -> {
                     print(YELLOW, " <=-- Resting --=>");
                     lineBreak(2);
                     playerCharacter.rest();
                  }
                  // Check Character
                  case 6 -> {
                     print(YELLOW, "<=-- Checking Character --=>");
                     lineBreak(2);
                     playerCharacter.checkCharacter();
                  }
                  // Add all nuclear cores to the player's backpack for testing purposes
                  case 5428 -> {
                     print(YELLOW, "<=-- Adding Nuclear Cores --=>");
                     lineBreak(2);
                     for (int i = 0; i < 9; i++) {
                        playerCharacter.addBackpackItem(new Items("Nuclear Core " + (i + 1), 0, 0, 2));
                     }
                     lineBreak();
                  }
                  // Invalid choice
                  default -> {
                     printSeperator = false;
                     clearLine(2);
                  }
               }
            }

            if (printSeperator) {
               lineBreak();
               printSpacer();
            }

            if (!playerCharacter.getIsAlive()) {
               print(RED, "+--------------------------------------------------+");
               lineBreak();
               print(RED, "| You failed to complete the mission... Game Over  |");
               lineBreak();
               print(RED, "+--------------------------------------------------+");
               lineBreak();
               lineBreak();
               print("Do you want to play again? " + colour(BLUE, "(Y)es / (N)o"));
               lineBreak();
               repeatOptions = false;
            } else if (playerCharacter.getIsGameEnding()){
               lineBreak();
               print(GREEN, "+--------------------------------------------------+");
               lineBreak();
               print(GREEN, "|       You completed the mission! Game Over       |");
               lineBreak();
               print(GREEN, "+--------------------------------------------------+");
               lineBreak();
               lineBreak();
               print("Do you want to play again? " + colour(BLUE, "(Y)es / (N)o"));
               lineBreak();
               repeatOptions = false;
            }
         } while (repeatOptions);

         primaryChoiceHandler = true;
         while (primaryChoiceHandler) {
               print(GREEN, " > ");
               String restartGame = sc.nextLine().toUpperCase();

               if (restartGame.isEmpty()) {
                  clearLine(1);
               } else {
                  char restartGameChar = restartGame.charAt(0);

                  switch (restartGameChar) {
                     case 'Y' -> {
                           primaryChoiceHandler = false;
                           clearLine(5000);
                     }
                     case 'N' -> {
                           primaryChoiceHandler = false;
                           gameRunning = false;
                     }
                     default -> {
                           clearLine(2);
                     }
                  }
               }
         }
      } while (gameRunning);
      lineBreak();
      print(GREEN, "Thank you for playing our game!");
      lineBreak();
   }

   /*
    * Prints the game tutorial to the console.
    */
   public static void printGameTutorial() {
      print(GREEN, "Welcome to our game!");
      lineBreak();
      print("Your inputs are denoted by the \"" + colour(GREEN, " > ") + " \"");
      lineBreak();
      print("You must type your input, it can be a number, letter or something else, it depends on what is being asked");
      lineBreak();
      print("If you type something and it seems like it disappeared, that means your input was wrong. Make sure you read so you know what to input.");
      lineBreak(2);
      printSpacer();
   }

   /**
    * Prompts the player for their name.
    *
    * @param playerCharacter The player to set the name for
    */
   public static void promptPlayerName(Player playerCharacter) {
      primaryChoiceHandler = true;

      while (primaryChoiceHandler) {
         secondaryChoiceHandler = true;
         print(GREEN, "Enter your name: ");
         String playerName = sc.nextLine().trim();

         // If the player enters an empty name, the line is cleared
         if (playerName.isEmpty()) {
               clearLine(1);
         } else {
            // Asks the player to confirm their name
            while (secondaryChoiceHandler) {
               print("Are you sure you want to use \"" + colour(YELLOW, playerName) + "\" as your name?" + colour(BLUE, " (Y)es / (N)o"));
               lineBreak();
               print(GREEN, " > ");
               String nameConfirm = sc.nextLine().toUpperCase();

               if (nameConfirm.isEmpty()) {
                  clearLine(2);
               } else {
                  char nameConfirmChar = nameConfirm.charAt(0);

                  switch (nameConfirmChar) {
                     case 'Y' -> {
                        playerCharacter.setName(playerName);
                        primaryChoiceHandler = false;
                        secondaryChoiceHandler = false;
                        lineBreak();
                     }
                     case 'N' -> {
                        clearLine(3);
                        secondaryChoiceHandler = false;
                     }
                     default -> {
                        clearLine(2);
                     }
                  }
               }
            }
         }
      }
      printSpacer();
   }

   /**
    * Displays information about the mission to the player.
    *
    * @param playerCharacter The player to display the mission information for
    */
   public static void displayMissionInformation(Player playerCharacter) {
      print("\"" + colour(YELLOW, playerCharacter.getName()) + ", you have been chosen for an important reconnaissance mission.");
      lineBreak();
      print("Your objective is to rescue two scientists that were conducting important research at a classified nuclear facility. We haven't heard from them -- make sure they're safe until further reinforcements arrive.");
      lineBreak();
      print("Our intelligence teams haven't been able to find any information about the facility yet, however we were told that it is highly radioactive.");
      lineBreak();
      print("The facility sent over this map, but communication cut out before they were able to tell us what it was about. Whatever it was, it must be important.");
      lineBreak();
      Navigation.viewMissionMap();
      print("We've got another issue -- our armoury's nearly empty. Ammunition is low, and we don't have enough medkits to go around. You'll have to make do with whatever you can scavenge. Stick to stealth if you can -- this mission is too important to risk on a firefight.");
      lineBreak();
      print("Godspeed, soldier.\"");
      lineBreak(2);
      printSpacer();
   }

   /**
    * Prompts the player to choose a weapon.
    *
    * @param playerCharacter The player to choose a weapon for
    */
   public static void playerWeaponSelection(Player playerCharacter) {
      primaryChoiceHandler = true;

      print("Choose a weapon to bring with you: ");
      lineBreak();
      print("The " + colour(BLUE, "(1) Pistol") + " is the most basic weapon, it's " + colour(YELLOW, "light and reliable") + ". While it deals " + colour(YELLOW, "low damage") + ", it's ideal for " + colour(YELLOW, "close-quarters combat") + ".");
      lineBreak();
      print("The " + colour(BLUE, "(2) Rifle") + " is a balanced weapon, offering " + colour(YELLOW, "moderate power, accuracy and weight") + ". It's versatile and effective at both " + colour(YELLOW, "medium and long ranges") + ".");
      lineBreak();
      print("The " + colour(BLUE, "(3) Sniper") + " is a " + colour(YELLOW, "heavy but high-precision weapon") + ", designed for " + colour(YELLOW, "long-range engagements") + ". It delivers " + colour(YELLOW, "powerful shots") + " but requires " + colour(YELLOW, "ammo") + ".");
      lineBreak();

      while (primaryChoiceHandler) {
         secondaryChoiceHandler = true;
         print(GREEN, " > ");
         String weaponChosen = sc.nextLine().toUpperCase();

         if (weaponChosen.isEmpty()) {
               clearLine(1);
         } else {
            int weaponChosenInt;

            try {
               weaponChosenInt = Integer.parseInt(weaponChosen);
            } catch (NumberFormatException e) {
               weaponChosenInt = 0;
            }

            switch (weaponChosenInt) {
               case 1 -> {
                  playerCharacter.setWeapon(new Weapon("Pistol", 8, 16, 1, 1));
               }
               case 2 -> {
                  playerCharacter.setWeapon(new Weapon("Rifle", 10, 12, 3, 3));
               }
               case 3 -> {
                  playerCharacter.setWeapon(new Weapon("Sniper", 14, 8, 5, 5));
               }
               // Admin weapon - for debugging purposes
               case 5428 -> {
                  playerCharacter.setWeapon(new Weapon("Admin", 12, 500, 3, 3));
                  playerCharacter.setHealth(300);
               }
               default -> {
                  clearLine(1);
                  continue;
               }
            }

            while (secondaryChoiceHandler) {
               print("Are you sure you want to use the " + colour(YELLOW, playerCharacter.getWeapon().getName()) + " as your weapon?" + colour(BLUE, " (Y)es / (N)o"));
               lineBreak();
               print(GREEN, " > ");
               String weaponConfirm = sc.nextLine().toUpperCase();

               if (weaponConfirm.isEmpty()) {
                  clearLine(2);
               } else {
                  char weaponConfirmChar = weaponConfirm.charAt(0);

                  switch (weaponConfirmChar) {
                     case 'Y' -> {
                        primaryChoiceHandler = false;
                        secondaryChoiceHandler = false;
                     }
                     case 'N' -> {
                        clearLine(3);
                        secondaryChoiceHandler = false;
                     }
                     default -> {
                        clearLine(2);
                     }
                  }
               }
            }
         }
      }
      lineBreak();
   }

   /**
    * Prompts the player to choose 2 items to start with.
    *
    * @param playerCharacter The player to choose items for
    */
   public static void playerItemSelection(Player playerCharacter) {
      int itemHandler = 0;

      while (itemHandler < 2) {
         primaryChoiceHandler = true;

         if (itemHandler == 0) {
            print("Pick your first item to take with you: ");
            lineBreak();
            print("The " + colour(BLUE, "(1) Food Pack") + " is a" + colour(YELLOW, " small item that regenerates 10 health") + ".");
            lineBreak();
            print("The " + colour(BLUE, "(2) Ammo Box") + " is a" + colour(YELLOW, " slightly heavier item that gives you 18 ammo for your weapon") + ".");
            lineBreak();
            print("The " + colour(BLUE, "(3) First Aid Kit") + " is a" + colour(YELLOW, " large item that heals you for 25 health") + ".");
         } else {
               print("Pick your second item to take with you: ");
         }
         lineBreak();

         while (primaryChoiceHandler) {
            secondaryChoiceHandler = true;
            String itemName = "";
            print(GREEN, " > ");
            String itemChosen = sc.nextLine().toUpperCase();

            if (itemChosen.isEmpty()) {
               clearLine(1);
            } else {
               int itemChosenInt;

               try {
                  itemChosenInt = Integer.parseInt(itemChosen);
               } catch (NumberFormatException e) {
                  itemChosenInt = 0;
               }

               switch (itemChosenInt) {
                  case 1 -> {
                        itemName = "Food Pack";
                  }
                  case 2 -> {
                        itemName = "Ammo Box";
                  }
                  case 3 -> {
                        itemName = "First Aid Kit";
                  }
                  default -> {
                        clearLine(1);
                        secondaryChoiceHandler = false;
                  }
               }

               while (secondaryChoiceHandler) {
                  print("Are you sure you want to pick the " + colour(YELLOW, itemName) + "?" + colour(BLUE, " (Y)es / (N)o"));
                  lineBreak();
                  print(GREEN, " > ");
                  String itemConfirm = sc.nextLine().toUpperCase();

                  if (itemConfirm.isEmpty()) {
                        clearLine(2);
                  } else {
                        char itemConfirmChar = itemConfirm.charAt(0);

                        switch (itemConfirmChar) {
                        case 'Y' -> {
                           switch (itemChosenInt) {
                              case 1 -> {
                                 playerCharacter.addBackpackItem(new Items("Food Pack", 10, 1, 0));
                              }
                              case 2 -> {
                                 playerCharacter.addBackpackItem(new Items("Ammo Box", 18, 2, 1));
                              }
                              case 3 -> {
                                 playerCharacter.addBackpackItem(new Items("First Aid Kit", 25, 3, 0));
                              }
                              default -> {
                                 clearLine(1);
                              }
                           }
                           secondaryChoiceHandler = false;
                           primaryChoiceHandler = false;
                        }
                        case 'N' -> {
                           clearLine(3);
                           secondaryChoiceHandler = false;
                        }
                        default -> {
                           clearLine(2);
                        }
                     }
                  }
               }
            }
         }
         itemHandler++;
         lineBreak();
      }
      printSpacer();
   }
}