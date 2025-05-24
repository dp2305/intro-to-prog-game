import java.util.Random;
import java.util.Scanner;
import utils.*;

public class Main {

    // ANSI codes for color - better readability in the console
    private static final String ANSI_RESET = "\u001B[0;49m";
    private static final String ANSI_TEXT_RED = "\u001B[91m";
    private static final String ANSI_TEXT_GREEN = "\u001B[92m";
    private static final String ANSI_TEXT_YELLOW = "\u001B[93m";
    private static final String ANSI_TEXT_BLUE = "\u001B[94m";

    // Scanner and Random obje  cts
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    // Game variables
    private static Player playerCharacter = new Player("Player", 50);
    public static boolean gameRunning = true;
    private static boolean validChoiceHandler;
    private static boolean validChoiceHandler2;

    public static void main(String[] args) {
        startTutorial();
        startGame();
        displayBackgroundInformation();
        missionStartPart1();

        print(playerCharacter.getWeapon().getName());

        // // Handle player's choice
        // while (validChoiceHandler) {
        //     // This is the intro code that gives the player the first choice
        //     print("You have been dropped off at the main base.");
        //     lineBreak();
        //     print("What would you like to do from here? ");
        //     printColour("(S)earch, (L)ook around", ANSI_TEXT_BLUE);
        //     lineBreak();
        //     printColour(" > ", ANSI_TEXT_GREEN);
        //     lineBreak();
        //     char choice = sc.next().toUpperCase().charAt(0);
        //     switch (choice) {
        //         case 'S' -> {
        //             print("You search the area and find some supplies.");
        //             // Add more flavour text here
        //             lineBreak();
        //             validChoiceHandler = false;
        //         }
        //         case 'L' -> {
        //             print("You look around and see various buildings and military equipment.");
        //             // Add more flavour text here
        //             lineBreak();
        //             validChoiceHandler = false;
        //         }
        //         default -> {
        //             printColour("Invalid choice. Please try again.", ANSI_TEXT_RED);
        //             lineBreak();
        //         }
        //     }
        // }
    }

    // Method to show player what colours coresponde to what
    public static void startTutorial() {
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

    // Method for starting the game
    // This is where the player is asked to enter their name
    public static void startGame() {
        validChoiceHandler = true;

        printColour("            -----=== Game Start ===-----", ANSI_TEXT_YELLOW);
        lineBreak();
        lineBreak();

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

    // Method for displaying the background information
    public static void displayBackgroundInformation() {
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

    // Method for starting the mission
    // This is where the player is asked to choose a weapon
    public static void missionStartPart1() {
        validChoiceHandler = true;

        print("Choose a weapon to start with: ");
        printColour("(K)nife, (P)istol, (R)ifle", ANSI_TEXT_BLUE);
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "Knife" + ANSI_RESET + " is the most basic weapon, it has" + ANSI_TEXT_YELLOW + " low damage and range" + ANSI_RESET + " but its" + ANSI_TEXT_YELLOW + " the lightest, making it agile." + ANSI_RESET + ".");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "Pistol" + ANSI_RESET + " has a larger range with a moderate damage output, but, its heavier than the knife and doesn't come with any ammo.");
        lineBreak();
        print("The " + ANSI_TEXT_BLUE + "Rifle" + ANSI_RESET + " is a" + ANSI_TEXT_YELLOW + " long range weapon with a high damage output" + ANSI_RESET + " but it is" + ANSI_TEXT_YELLOW + " heavy and difficult to handle" + ANSI_RESET + ".");
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
                    case 'K' -> {
                        playerCharacter.setWeapon(new Weapon("Knife", 3, 1000000, 1, 1));
                    }
                    case 'P' -> {
                        playerCharacter.setWeapon(new Weapon("Pistol", 6, 0, 2, 2));
                    }
                    case 'R' -> {
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
    }

    // Method for starting the mission
    // This is where the player is asked to choose 2 items to start with
    public static void missionStartPart2() {
        print("Choose 2 items to start with: ");
        printColour("1. Food Pack, 2. Ammo Box, 3. First Aid Kit", ANSI_TEXT_BLUE);
        lineBreak();

        print("You are inside of a helicopter, about to be dropping into the mission area.");
    }

    public static void mission1() {
        validChoiceHandler = true;

        lineBreak();
        print("you are being dropped off at the closed military base to the mission area.");
        print("you are dropped off on the heli-pad");
        print("what would you like to do from here?");
        lineBreak();
        printColour("(S)earch, (L)ook around, (M)ap", ANSI_TEXT_BLUE);
    }

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

    // Method to clear line(s) for a cleaner console output, e.g. after an error message is printed
    public static void clearLine(int clearLineCount) {
        for (int i = 0; i < clearLineCount; i++) {
            System.out.print("\033[1A\033[2K");
        }
    }
}