import java.util.Scanner;
import utils.*;
import static utils.Formatting.*;

public class Main {
    // Scanner and Random objects
    private static final Scanner sc = new Scanner(System.in);

    // Global input validation variables
    private static boolean validChoiceHandler, validChoiceHandler2;

    public static void main(String[] args) {
        boolean gameRunning = true;

        do {
            // Generate a new player at the start of the while loop to so that all information about the player is reset
            final Player playerCharacter = new Player("Player", 1000);
            boolean playerAlive = true;
            boolean repeatOptions = true;
            playerName(playerCharacter);
            missionInformation(playerCharacter);
            weaponSelection(playerCharacter);
            itemsSelection(playerCharacter);

            do {
                // Recalculate player weight before each action
                playerCharacter.calculatePlayerWeight();

                validChoiceHandler = true;
                validChoiceHandler2 = true;
                boolean printGap = true;

                print("What would you like to do? " + ANSI_TEXT_BLUE + "(1) Use item, (2) Move, (3) Look Nearby, (4) Search, (5) Rest, (6) Check Character, (7) View Given Map" + ANSI_RESET);
                lineBreak();
                printColour(" > ", ANSI_TEXT_GREEN);
                String choice = sc.nextLine().toUpperCase();

                if (choice.isEmpty()) {
                    printGap = false;
                    clearLine(2);
                } else {
                    char choiceChar = choice.charAt(0);
                    switch (choiceChar) {
                        // Use item
                        case '1' -> {
                            printColour("<=-- Using Item --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Items.useItem(playerCharacter);
                        }
                        // Move
                        case '2' -> {
                            printColour("<=-- Navigating --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Navigation.navigate(playerCharacter);
                        }
                        // View Map
                        case '3' -> {
                            printColour("<=-- Looking Nearby --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Navigation.viewMap();
                        }
                        // Search
                        case '4' -> {
                            printColour("<=-- Searching --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Player.search(playerCharacter);
                        }
                        // Rest
                        case '5' -> {
                            printColour(" <=-- Resting --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Player.rest(playerCharacter);
                        }
                        // Check Character
                        case '6' -> {
                            printColour("<=-- Checking Character --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Player.checkCharacter(playerCharacter);
                        }
                        case '7' -> {
                            printColour("<=-- Viewing Map --=>", ANSI_TEXT_YELLOW);
                            lineBreak();
                            lineBreak();
                            Navigation.viewMissionMap();
                        }
                        // Invalid choice
                        default -> {
                            printGap = false;
                            clearLine(2);
                        }
                    }
                }

                if (printGap) {
                    lineBreak();
                    printSpacer();
                }

                // if (!Combat.alive) {
                if (!playerAlive) {
                    lineBreak();
                    printColour("+--------------------------------------------------+", ANSI_TEXT_RED);
                    lineBreak();
                    printColour("| You failed to complete the mission... Game Over  |", ANSI_TEXT_RED);
                    lineBreak();
                    printColour("+--------------------------------------------------+", ANSI_TEXT_RED);
                    lineBreak();
                    print("Do you want to play again? (Y)es / (N)o");
                    lineBreak();
                    validChoiceHandler = true;
                    while (validChoiceHandler) {
                        printColour(" > ", ANSI_TEXT_GREEN);
                        String restartGame = sc.nextLine().toUpperCase();

                        if (restartGame.isEmpty()) {
                            clearLine(1);
                        } else {
                            char restartGameChar = restartGame.charAt(0);

                            switch (restartGameChar) {
                                case 'Y' -> {
                                    validChoiceHandler = false;
                                    repeatOptions = false;
                                    lineBreak();
                                    lineBreak();
                                    lineBreak();
                                }
                                case 'N' -> {
                                    validChoiceHandler = false;
                                    repeatOptions = false;
                                    gameRunning = false;
                                }
                                default -> {
                                    clearLine(2);
                                }
                            }
                        }
                    }
                }
            } while (repeatOptions);
        } while (gameRunning);
        lineBreak();
        printColour("Thank you for playing our game!", ANSI_TEXT_GREEN);
        lineBreak();
    }

        // Method to ask the player for their name
    public static void playerName(Player playerCharacter) {
        validChoiceHandler = true;

        while (validChoiceHandler) {
            validChoiceHandler2 = true;
            printColour("Enter your name: ", ANSI_TEXT_GREEN);
            String playerName = sc.nextLine();

            // If the player enters an empty name, the line is cleared
            if (playerName.isEmpty()) {
                clearLine(1);
            } else {
                // Asks the player to confirm their name
                while (validChoiceHandler2) {
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
                            validChoiceHandler = false;
                            validChoiceHandler2 = false;
                            lineBreak();
                            }
                            case 'N' -> {
                                clearLine(3);
                                validChoiceHandler2 = false;
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

    // Method to display information about the mission
    public static void missionInformation(Player playerCharacter) {
        print("\"" + ANSI_TEXT_YELLOW + playerCharacter.getName() + ANSI_RESET + ", you have been chosen for an important reconnaissance mission.");
        lineBreak();
        print("Your objective is to rescue two scientists that were conducting important research at a classified facility. We haven't heard from them -- make sure they're safe.");
        lineBreak();
        print("Our intelligence teams haven't been able to find any information about the facility yet, however we were told that it is a highly dangerous animal testing site.");
        lineBreak();
        print("Who knows what sort of testing they do up there...");
        lineBreak();
        print("Anyways, the headquarters sent over this map, but communication cut out before they were able to tell us what it was about. Whatever it was, it must be important.");
        lineBreak();
        print("We've got another issue -- our arsenal's nearly empty. Ammunition is low, and we don't have enough medkits to go around. You'll have to make do with whatever you can scavenge in the armoury. Stick to stealth if you can -- this mission is too important to risk on a firefight.");
        lineBreak();
        Navigation.viewMissionMap();
        print("Godspeed, soldier.\"");
        lineBreak();
        lineBreak();
        printSpacer();

    }

    // This is where the player is asked to choose a weapon
    public static void weaponSelection(Player playerCharacter) {
        validChoiceHandler = true;

        print("Choose a weapon to start with: ");
        printColour("(1) Knife, (2) Pistol, (3) Rifle", ANSI_TEXT_BLUE);
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(1) Knife" + ANSI_RESET + " is the most basic weapon, it has" + ANSI_TEXT_YELLOW + " low damage and range" + ANSI_RESET + " but its" + ANSI_TEXT_YELLOW + " the lightest, making it agile" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(2) Pistol" + ANSI_RESET + " has a" + ANSI_TEXT_YELLOW + " larger range with moderate damage" + ANSI_RESET + " but," + ANSI_TEXT_YELLOW + " its heavier than the knife and doesn't come with any ammo" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(3) Rifle" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " long range weapon with a high damage output" + ANSI_RESET + " but it is" + ANSI_TEXT_YELLOW + " heavy and difficult to handle and only has some ammo" + ANSI_RESET + ".");
        lineBreak();

        while (validChoiceHandler) {
            validChoiceHandler2 = true;
            printColour(" > ", ANSI_TEXT_GREEN);
            String weaponChosen = sc.nextLine().toUpperCase();

            if (weaponChosen.isEmpty()) {
                clearLine(1);
            } else {
                char weaponChosenChar = weaponChosen.charAt(0);

                switch (weaponChosenChar) {
                    case '1' -> {
                        playerCharacter.setWeapon(new Weapon("Knife", 2, 1000000, 1));
                    }
                    case '2' -> {
                        playerCharacter.setWeapon(new Weapon("Pistol", 100, 0, 2));
                    }
                    case '3' -> {
                        playerCharacter.setWeapon(new Weapon("Rifle", 12, 6, 3));
                    }
                    default -> {
                        clearLine(1);
                        continue;
                    }
                }

                while (validChoiceHandler2) {
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
                                validChoiceHandler = false;
                                validChoiceHandler2 = false;
                            }
                            case 'N' -> {
                                clearLine(3);
                                validChoiceHandler2 = false;
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

    // This is where the player is asked to choose 2 items to start with
    public static void itemsSelection(Player playerCharacter) {
        int itemHandler = 0;

        while (itemHandler < 2) {
            validChoiceHandler = true;

            if (itemHandler == 0) {
                print("Pick your first item to start with: ");
                printColour("(1) Food Pack, (2) Ammo Box, (3) First Aid Kit", ANSI_TEXT_BLUE);
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(1) Food Pack" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " small item that regenerates 10 health" + ANSI_RESET + ".");
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(2) Ammo Box" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " slightly larger item that gives you 18 ammo for your weapon" + ANSI_RESET + ".");
                lineBreak();
                print("The " + ANSI_TEXT_BLUE + "(3) First Aid Kit" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " large item that heals you for 30 health" + ANSI_RESET + ".");
            } else {
                print("Pick your second item to start with: ");
            }
            lineBreak();

            while (validChoiceHandler) {
                validChoiceHandler2 = true;
                String itemName = "";
                printColour(" > ", ANSI_TEXT_GREEN);
                String itemChosen = sc.nextLine().toUpperCase();

                if (itemChosen.isEmpty()) {
                    clearLine(1);
                } else {
                    char itemChosenChar = itemChosen.charAt(0);

                    switch (itemChosenChar) {
                        case '1' -> {
                            itemName = "Food Pack";
                        }
                        case '2' -> {
                            if (playerCharacter.getWeapon().getName().equals("Knife")) {
                                print("Knifes do not require ammo, please pick another item.");
                                lineBreak();
                                continue;
                            } else {
                                itemName = "Ammo Box";
                            }
                        }
                        case '3' -> {
                            itemName = "First Aid Kit";
                        }
                        default -> {
                            clearLine(1);
                            validChoiceHandler2 = false;
                        }
                    }

                    while (validChoiceHandler2) {
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
                                    validChoiceHandler = false;
                                    validChoiceHandler2 = false;

                                    switch (itemChosenChar) {
                                        case '1' -> {
                                            playerCharacter.addBackpackItem(new Items("Food Pack", 10, 1, 0));
                                        }
                                        case '2' -> {
                                            playerCharacter.addBackpackItem(new Items("Ammo Box", 18, 2, 1));
                                        }
                                        case '3' -> {
                                            playerCharacter.addBackpackItem(new Items("First Aid Kit", 30, 3, 0));
                                        }
                                        default -> {
                                            clearLine(1);
                                            validChoiceHandler2 = false;
                                        }
                                    }
                                }
                                case 'N' -> {
                                    clearLine(3);
                                    validChoiceHandler2 = false;
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