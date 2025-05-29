import java.util.Scanner;
import utils.*;
import static utils.Formatting.*;


/**
 * Main class is the heart for the text-based survival game called “Rescue the survivors" RTS for short.
 * Responsible for game setup, character creation, item/weapon selection and the main gameplay loop.
 * Helper classes are used from the utils package for formatting, navigation, item usage, and player status.
 * F5AC Group 2 - [Aditya, Dutt, Angus]
 */

public class Main {
    // Scanner and Random objects
    private static final Scanner sc = new Scanner(System.in);

    // Global input validation variables
    private static boolean primaryChoiceHandler, secondaryChoiceHandler;

    /**
     * The main game loop that manages the overall structure and flow of the game.
     * Character instantiation</li>
     * Setup steps (name, weapon, and item selection)
     * Repeating player actions until game over
     * Game over messages and replay options
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        boolean gameRunning = true;
        boolean repeatOptions = true;
        boolean printSeperator = true;

        do {
            // Generate a new player at the start of the while loop so that all player information is reset
            final Player playerCharacter = new Player("Player", 1000, true);
            promptPlayerName(playerCharacter);
            displayMissionInformation(playerCharacter);
            playerWeaponSelection(playerCharacter);
            playerItemSelection(playerCharacter);

            do {
                // Recalculate player weight before each action
                playerCharacter.calculatePlayerWeight();

                primaryChoiceHandler = true;
                secondaryChoiceHandler = true;

                print("What would you like to do? " + ANSI_TEXT_BLUE + "(1) Use item, (2) Move, (3) Look Nearby, (4) Search, (5) Rest, (6) Check Character, (7) View Given Map" + ANSI_RESET);
                lineBreak();
                printColour(" > ", ANSI_TEXT_GREEN);
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
                            printColour("<=-- Using Item --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Items.useItem(playerCharacter);
                        }
                        // Move
                        case 2 -> {
                            printColour("<=-- Navigating --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Navigation.navigate(playerCharacter);
                        }
                        // View Map
                        case 3 -> {
                            printColour("<=-- Looking Nearby --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Navigation.viewMap();
                        }
                        // Search
                        case 4 -> {
                            printColour("<=-- Searching --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Player.search(playerCharacter);
                        }
                        // Rest
                        case 5 -> {
                            printColour(" <=-- Resting --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Player.rest(playerCharacter);
                        }
                        // Check Character
                        case 6 -> {
                            printColour("<=-- Checking Character --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Player.checkCharacter(playerCharacter);
                        }
                        // View Mission Map
                        case 7 -> {
                            printColour("<=-- Viewing Map --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            Navigation.viewMissionMap();
                        }
                        // Add all fragments to the player's backpack for testing purposes
                        case 5428 -> {
                            printColour("<=-- Adding Fragments --=>", ANSI_TEXT_YELLOW);
                            lineBreak(2);
                            for (int i = 0; i < 9; i++) {
                                playerCharacter.addBackpackItem(new Items("Fragment " + (i + 1), (i + 1), 0, 2));
                            }
                            print("All fragments added.");
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
                    printColour("+--------------------------------------------------+", ANSI_TEXT_RED);
                    lineBreak();
                    printColour("| You failed to complete the mission... Game Over  |", ANSI_TEXT_RED);
                    lineBreak();
                    printColour("+--------------------------------------------------+", ANSI_TEXT_RED);
                    lineBreak();
                    lineBreak();
                    print("Do you want to play again? " + ANSI_TEXT_BLUE + "(Y)es / (N)o" + ANSI_RESET);
                    lineBreak();
                    repeatOptions = false;
                } else if (playerCharacter.getIsGameEnding()){
                    lineBreak();
                    printColour("+--------------------------------------------------+", ANSI_TEXT_GREEN);
                    lineBreak();
                    printColour("|       You completed the mission! Game Over       |", ANSI_TEXT_GREEN);
                    lineBreak();
                    printColour("+--------------------------------------------------+", ANSI_TEXT_GREEN);
                    lineBreak();
                    lineBreak();
                    print("Do you want to play again? " + ANSI_TEXT_BLUE + "(Y)es / (N)o" + ANSI_RESET);
                    lineBreak();
                    repeatOptions = false;
                }
            } while (repeatOptions);

            primaryChoiceHandler = true;
            while (primaryChoiceHandler) {
                printColour(" > ", ANSI_TEXT_GREEN);
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
        printColour("Thank you for playing our game!", ANSI_TEXT_GREEN);
        lineBreak();
    }

    /**
     * Prompts the player to input a character name and confirm it.
     * Loops until a non-empty, confirmed name is entered.
     * @param playerCharacter The player object to assign the name to.
     */
    public static void promptPlayerName(Player playerCharacter) {
        primaryChoiceHandler = true;

        while (primaryChoiceHandler) {
            secondaryChoiceHandler = true;
            printColour("Enter your name: ", ANSI_TEXT_GREEN);
            String playerName = sc.nextLine();

            // If the player enters an empty name, the line is cleared
            if (playerName.isEmpty()) {
                clearLine(1);
            } else {
                // Asks the player to confirm their name
                while (secondaryChoiceHandler) {
                    print("Are you sure you want to use \"" + ANSI_TEXT_YELLOW + playerName + ANSI_RESET + "\" as your name?" + ANSI_TEXT_BLUE + " (Y)es / (N)o" + ANSI_RESET);
                    lineBreak();
                    printColour(" > ", ANSI_TEXT_GREEN);
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
     * Displays the mission briefing to the player.
     * Introduces the story context, goal, and background setting.
     * @param playerCharacter The player character to personalize the briefing with their name.
     */
    public static void displayMissionInformation(Player playerCharacter) {
        print("\"" + ANSI_TEXT_YELLOW + playerCharacter.getName() + ANSI_RESET + ", you have been chosen for an important reconnaissance mission.");
        lineBreak();
        print("Your objective is to rescue two scientists that were conducting important research at a classified facility. We haven't heard from them -- make sure they're safe until further reinforcements arrive.");
        lineBreak();
        print("Our intelligence teams haven't been able to find any information about the facility yet, however we were told that it is a highly dangerous animal testing site.");
        lineBreak();
        print("Who knows what sort of testing they do up there...");
        lineBreak();
        print("Anyways, the headquarters sent over this map, but communication cut out before they were able to tell us what it was about. Whatever it was, it must be important.");
        lineBreak();
        Navigation.viewMissionMap();
        print("We've got another issue -- our arsenal's nearly empty. Ammunition is low, and we don't have enough medkits to go around. You'll have to make do with whatever you can scavenge in the armoury. Stick to stealth if you can -- this mission is too important to risk on a firefight.");
        lineBreak();
        print("Godspeed, soldier.\"");
        lineBreak();
        lineBreak();
        printSpacer();
    }

    /**
     * Presents weapon choices to the player and allows them to select one.
     * The selection is confirmed before being finalised.
     * @param playerCharacter The player object to assign the selected weapon to.
     */
    public static void playerWeaponSelection(Player playerCharacter) {
        primaryChoiceHandler = true;

        print("Choose a weapon to bring with you: ");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(1) Pistol" + ANSI_RESET + " is the most basic weapon, it's " + ANSI_TEXT_YELLOW + "light and reliable" + ANSI_RESET + ". While it deals " + ANSI_TEXT_YELLOW + "low damage" + ANSI_RESET + ", it's ideal for " + ANSI_TEXT_YELLOW + "close-quarters combat" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(2) Rifle" + ANSI_RESET + " is a balanced weapon, offering " + ANSI_TEXT_YELLOW + "moderate power, accuracy and weight" + ANSI_RESET + ". It's versatile and effective at both " + ANSI_TEXT_YELLOW + "medium and long ranges" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(3) Sniper" + ANSI_RESET + " is a " + ANSI_TEXT_YELLOW + "heavy but high-precision weapon" + ANSI_RESET + ", designed for " + ANSI_TEXT_YELLOW + "long-range engagements" + ANSI_RESET + ". It delivers " + ANSI_TEXT_YELLOW + "powerful shots" + ANSI_RESET + " but requires " + ANSI_TEXT_YELLOW + "ammo" + ANSI_RESET + ".");
        lineBreak();

        while (primaryChoiceHandler) {
            secondaryChoiceHandler = true;
            printColour(" > ", ANSI_TEXT_GREEN);
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
                        playerCharacter.setWeapon(new Weapon("Pistol", 6, 14, 1, 1));
                    }
                    case 2 -> {
                        playerCharacter.setWeapon(new Weapon("Rifle", 8, 10, 3, 3));
                    }
                    case 3 -> {
                        playerCharacter.setWeapon(new Weapon("Sniper", 12, 6, 5, 5));
                    }
                    // Admin weapon - for debugging purposes
                    case 5428 -> {
                        playerCharacter.setWeapon(new Weapon("Admin", 12, 500, 3, 3));
                        playerCharacter.setHealth(200);
                    }
                    default -> {
                        clearLine(1);
                        continue;
                    }
                }

                while (secondaryChoiceHandler) {
                    print("Are you sure you want to use the " + ANSI_TEXT_YELLOW + playerCharacter.getWeapon().getName() + ANSI_RESET + " as your weapon?" + ANSI_TEXT_BLUE + " (Y)es / (N)o" + ANSI_RESET);
                    lineBreak();
                    printColour(" > ", ANSI_TEXT_GREEN);
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
     * Allows the player to select two starting items (e.g., Food Pack, Ammo Box, First Aid Kit).
     * Each selection is confirmed before being added to the player's backpack.
     * @param playerCharacter The player object to which selected items are added.
     */
    public static void playerItemSelection(Player playerCharacter) {
        int itemHandler = 0;

        while (itemHandler < 2) {
            primaryChoiceHandler = true;

            if (itemHandler == 0) {
                print("Pick your first item to take with you: ");
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(1) Food Pack" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " small item that regenerates 7 health" + ANSI_RESET + ".");
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(2) Ammo Box" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " slightly heavier item that gives you 16 ammo for your weapon" + ANSI_RESET + ".");
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(3) First Aid Kit" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " large item that heals you for 20 health" + ANSI_RESET + ".");
            } else {
                print("Pick your second item to take with you: ");
            }
            lineBreak();

            while (primaryChoiceHandler) {
                secondaryChoiceHandler = true;
                String itemName = "";
                printColour(" > ", ANSI_TEXT_GREEN);
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
                        print("Are you sure you want to pick the " + ANSI_TEXT_YELLOW + itemName + ANSI_RESET + "?" + ANSI_TEXT_BLUE + " (Y)es / (N)o" + ANSI_RESET);
                        lineBreak();
                        printColour(" > ", ANSI_TEXT_GREEN);
                        String itemConfirm = sc.nextLine().toUpperCase();

                        if (itemConfirm.isEmpty()) {
                            clearLine(2);
                        } else {
                            char itemConfirmChar = itemConfirm.charAt(0);

                            switch (itemConfirmChar) {
                                case 'Y' -> {
                                    switch (itemChosenInt) {
                                        case 1 -> {
                                            playerCharacter.addBackpackItem(new Items("Food Pack", 7, 1, 0));
                                        }
                                        case 2 -> {
                                            playerCharacter.addBackpackItem(new Items("Ammo Box", 16, 2, 1));
                                        }
                                        case 3 -> {
                                            playerCharacter.addBackpackItem(new Items("First Aid Kit", 20, 3, 0));
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