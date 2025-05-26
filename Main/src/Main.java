import java.util.Random;
import java.util.Scanner;
import utils.*;
import static utils.Formatting.*;

public class Main {

    // Scanner and Random obje  cts
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    // Game variables
    private static Player playerCharacter = new Player("Player", 50);
    public static boolean gameRunning = true;
    private static boolean validChoiceHandler, validChoiceHandler2;

    public static void main(String[] args) {
        printTutorial();
        printSpacer();
        playerName();
        printSpacer();
        missionStartInformation();
        printSpacer();
        weaponSelection();
        itemsSelection();

        printSpacer();

        while (gameRunning) {
            validChoiceHandler = true;
            validChoiceHandler2 = true;
            boolean printGap = true;

            print("What would you like to do? " + ANSI_TEXT_BLUE + "(1) Use item, (2) Move, (3) View Map, (4) Search, (5) Rest, (6) Check Character" + ANSI_RESET);
            lineBreak();
            printColour(" > ", ANSI_TEXT_GREEN);
            String choice = sc.nextLine().toUpperCase();

            if (choice.isEmpty()) {
                clearLine(2);
            } else {
                char choiceChar = choice.charAt(0);
                switch (choiceChar) {
                    case '1' -> {
                        lineBreak();
                        printColour("            <=-- Using Item --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        print("What item would you like to use? \n" + ANSI_TEXT_BLUE + playerCharacter.showBackpack() + ANSI_RESET);
                        lineBreak();
                        while (validChoiceHandler) {
                            printColour(" > ", ANSI_TEXT_GREEN);
                            String itemChoice = sc.nextLine();

                            if (itemChoice.isEmpty()) {
                                clearLine(1);
                            } else {
                                char itemChoiceChar = itemChoice.charAt(0);
                                switch (itemChoiceChar) {
                                    case '1' -> {
                                        Items item = playerCharacter.getBackpackItem(0);
                                        item.useItem(playerCharacter, item);
                                        validChoiceHandler = false;
                                    }
                                    case '2' -> {
                                        Items item = playerCharacter.getBackpackItem(1);
                                        item.useItem(playerCharacter, item);
                                        validChoiceHandler = false;
                                    }
                                    case '0' -> {
                                        validChoiceHandler = false;
                                    }
                                    default -> {
                                        clearLine(1);
                                    }
                                }
                            }
                        }
                    }
                    case '2' -> {
                        printColour("            <=-- Navigating --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        lineBreak();
                        Navigation.navigate(playerCharacter);
                    }
                    case '3' -> {
                        printColour("            <=-- Viewing Map --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        lineBreak();
                        Navigation.viewMap();
                    }
                    case '4' -> {
                        printColour("            <=-- Searching --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        lineBreak();
                        // search for items
                    }
                    case '5' -> {
                        printColour("            <=-- Resting --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        lineBreak();
                        // rest (heal a random amount of health from (1-4)), must only rest once per 2 movements and cannot rest if health is full
                    }
                    case '6' -> {
                        printColour("            <=-- Checking Character --=>", ANSI_TEXT_YELLOW);
                        lineBreak();
                        lineBreak();
                        print("You have " + playerCharacter.getHealth() + " health.");
                        lineBreak();

                        // Display Weapon - don't display ammo if the weapon is a knife
                        if (playerCharacter.getWeapon().getName().equals("Knife")) {
                            print("You are carrying a " + playerCharacter.getWeapon().getName() + ".");
                        } else {
                            print("You are carrying a " + playerCharacter.getWeapon().getName() + " with " + playerCharacter.getWeapon().getAmmo() + " ammo.");
                        }
                        lineBreak();

                        // Display Backpack
                        print("You are carrying the following items: \n" + playerCharacter.showBackpack());
                    }
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
        }
    }

    // Method to show player what colours correspond to what
    public static void printTutorial() {
        print(ANSI_TEXT_GREEN + "Green" + ANSI_RESET + " means that you must enter your choice, for example:");
        lineBreak();
        print(" - " + ANSI_TEXT_GREEN + "Enter your name: " + ANSI_RESET + " name");
        lineBreak();
        print(" - " + ANSI_TEXT_GREEN + " > " + ANSI_RESET + "Y");
        lineBreak();
        print(ANSI_TEXT_BLUE + "Blue" + ANSI_RESET + " are the choices that you can make.");
        lineBreak();
        print(ANSI_TEXT_YELLOW + "Yellow" + ANSI_RESET + " is information about the story of the game.");
        lineBreak();
        print(ANSI_TEXT_RED + "Red" + ANSI_RESET + " is an error message.");
        lineBreak();
        lineBreak();
    }

    // Method to ask the player for their name
    public static void playerName() {
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
    }

    // Method to display information about the mission
    public static void missionStartInformation() {
        print("\"" + ANSI_TEXT_YELLOW + playerCharacter.getName() + ANSI_RESET + ", you have been chosen for an important reconnaissance mission.");
        lineBreak();
        print("Your objective is to rescue scientists that were conducting important research at a classified facility.");
        lineBreak();
        print("Our teams haven't been able to find any information about the facility, but we know that it is highly dangerous.");
        lineBreak();
        print("Godspeed soldier.\"");
        lineBreak();
        lineBreak();
    }

    // This is where the player is asked to choose a weapon
    public static void weaponSelection() {
        validChoiceHandler = true;

        print("Choose a weapon to start with: ");
        printColour("(1) Knife, (2) Pistol, (3) Rifle", ANSI_TEXT_BLUE);
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(1) Knife" + ANSI_RESET + " is the most basic weapon, it has" + ANSI_TEXT_YELLOW + " low damage and range" + ANSI_RESET + " but its" + ANSI_TEXT_YELLOW + " the lightest, making it agile" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(2) Pistol" + ANSI_RESET + " has a" + ANSI_TEXT_YELLOW + " larger range with moderate damage" + ANSI_RESET + " but," + ANSI_TEXT_YELLOW + " its heavier than the knife and doesn't come with any ammo" + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "(3) Rifle" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " long range weapon with a high damage output" + ANSI_RESET + " but it is" + ANSI_TEXT_YELLOW + " heavy and difficult to handle" + ANSI_RESET + ".");
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
                        playerCharacter.setWeapon(new Weapon("Knife", 3, 1000000, 1, 1));
                    }
                    case '2' -> {
                        playerCharacter.setWeapon(new Weapon("Pistol", 6, 0, 2, 2));
                    }
                    case '3' -> {
                        playerCharacter.setWeapon(new Weapon("Rifle", 15, 12, 3, 3));
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

    // Method for starting the mission
    // This is where the player is asked to choose 2 items to start with
    public static void itemsSelection() {
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
    }

    // mission 1 still needs to be completed
    public static void mission1() {
        validChoiceHandler = true;

        // Handle player's choice
        while (validChoiceHandler) {
            // This is the intro code that gives the player the first choice
            print("You have been dropped off at the main base.");
            lineBreak();
            print("What would you like to do from here? ");
            printColour("(S)earch, (L)ook around", ANSI_TEXT_BLUE);
            lineBreak();
            printColour(" > ", ANSI_TEXT_GREEN);
            lineBreak();
            String choice = sc.next().toUpperCase();

            if (choice.isEmpty()) {
                clearLine(1);
            } else {
                char choiceChar = choice.charAt(0);
                switch (choiceChar) {
                    case 'S' -> {
                        print("You search the area and find some supplies.");
                        // Add more flavour text here
                        lineBreak();
                        validChoiceHandler = false;
                    }
                    case 'L' -> {
                        print("You look around and see various buildings and military equipment.");
                        // Add more flavour text here
                        lineBreak();
                        validChoiceHandler = false;
                    }
                    default -> {
                        printColour("Invalid choice. Please try again.", ANSI_TEXT_RED);
                        lineBreak();
                    }
                }
            }
        }
    }
}