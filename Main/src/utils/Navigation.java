package utils;
import java.util.Scanner;

public class Navigation {

    // ANSI codes for color - better readability in the console
    private static final String ANSI_RESET = "\u001B[0;49m";
    private static final String ANSI_TEXT_RED = "\u001B[91m";
    private static final String ANSI_TEXT_GREEN = "\u001B[92m";
    private static final String ANSI_TEXT_YELLOW = "\u001B[93m";
    private static final String ANSI_TEXT_BLUE = "\u001B[94m";

    private static final Scanner sc = new Scanner(System.in);

    // Player's current location and coordinates
    private static int currentLocation = 00;
    private static int playerX, playerY;

    // Location names
    private static final String[] locationNames = {
        "Main Base",       "Main Base",       "River",      "Abandoned Lab",  "Abandoned Lab",
        "Main Base",       "Forest",          "River",      "Forest",         "Forest",
        "Forest",          "Forest",          "River",      "River",          "Abandoned Camp",
        "Forest",          "Forest",          "Mountains",  "River",          "Mountains",
        "Abandoned Camp",  "Abandoned Camp",  "Mountains",  "Mountains",      "Mountains"
    };

    public static void navigate() {
        int errorCount = 0;
        boolean validChoiceHandler = true;

        while (validChoiceHandler) {
            boolean incorrectInput = false;

            // Print the map before user input
            map();

            // Calculate the relative index of the player's location
            int characterLocationIndex = playerX * 5 + playerY;

            // Print the current location and player options
            System.out.println("You are currently in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET + ", where do you want to go? "+ ANSI_TEXT_BLUE + "\n('W' - Up, 'A' - Left, 'S' - Down, 'D' - Right, 'Q' - Go Back)" + ANSI_RESET);

            // Get the player's movement direction
            System.out.print(ANSI_TEXT_GREEN + " > " + ANSI_RESET);
            char movementDirection = sc.next().toUpperCase().charAt(0);

            // Move the player or take them back to the player options
            // If the player is at the edge of the map, set incorrectInput to true
            switch (movementDirection) {
                case 'W' -> {
                    if (playerX == 0) {
                        incorrectInput = true;
                    } else {
                        currentLocation -= 10;
                        validChoiceHandler = false;
                    }
                }
                case 'A' -> {
                    if (playerY == 0) {
                        incorrectInput = true;
                    } else {
                        currentLocation -= 1;
                        validChoiceHandler = false;
                    }
                }
                case 'S' -> {
                    if (playerX == 4) {
                        incorrectInput = true;
                    } else {
                        currentLocation += 10;
                        validChoiceHandler = false;
                    }
                }
                case 'D' -> {
                    if (playerY == 4) {
                        incorrectInput = true;
                    } else {
                        currentLocation += 1;
                        validChoiceHandler = false;
                    }
                }
                case 'Q' -> {
                    System.out.println("[go back to main menu]");
                    validChoiceHandler = false;
                }
                default -> {
                    for (int i = 0; i < 14; i++) {
                        System.out.print("\033[1A\033[2K");
                    }
                    System.out.println(ANSI_TEXT_RED + "Invalid input, please try again." + ANSI_RESET);
                    errorCount++;
                    continue;
                }
            }

            // Delete the previous map and print the error message
            if (incorrectInput) {
                for (int i = 0; i < 14; i++) {
                    System.out.print("\033[1A\033[2K");
                }
                System.out.println(ANSI_TEXT_RED + "You can't go that way, try again." + ANSI_RESET);
                errorCount++;
                continue;
            }

            // Remove the previous map and print the updated map
            for (int i = 0; i < 14 + errorCount; i++) {
                System.out.print("\033[1A\033[2K");
            }

            map();

            // Print the player's new location
            System.out.println("You are now in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET + ".");
        }
    }

    private static void map() {
        // Calculate player's current location
        playerX = currentLocation / 10;
        playerY = currentLocation % 10;

        // Print the map
        for (int i = 0; i < 5; i++) {
            System.out.println("+---+---+---+---+---+");
            for (int j = 0; j < 5; j++) {
                System.out.print("| ");
                if (playerX == i && playerY == j) {
                    System.out.print(ANSI_TEXT_GREEN + "P " + ANSI_RESET); // Player's current location
                } else if ((playerX == i && (playerY == j - 1 || playerY == j + 1)) || (playerY == j && (playerX == i - 1 || playerX == i + 1))) {
                    System.out.print(ANSI_TEXT_YELLOW + "O " + ANSI_RESET); // Travelable locations
                } else {
                    System.out.print(ANSI_TEXT_RED + "- " + ANSI_RESET); // Non-travelable locations
                }
            }
            System.out.println("|");
        }
        System.out.println("+---+---+---+---+---+");
    }
}