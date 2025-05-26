package utils;
import java.util.Random;
import java.util.Scanner;
import static utils.Formatting.*;

public class Navigation {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();

    // Player's current location and coordinates
    private static int currentLocation = 00;
    private static int playerX, playerY;
    private static int characterLocationIndex;

    // Location names
    private static final String[] locationNames = {
        "Base",            "Base",            "River",      "Destroyed Lab",  "Destroyed Lab",
        "Base",            "Forest",          "River",      "Forest",         "Forest",
        "Forest",          "Forest",          "River",      "River",          "Abandoned Camp",
        "Forest",          "Forest",          "Mountains",  "River",          "Mountains",
        "Abandoned Camp",  "Abandoned Camp",  "Mountains",  "Mountains",      "Mountains"
    };

    // Whether the player has visited the location
    private static final boolean[] visitedLocations = {
        true, false, false, false, false,
        false, false, false, false, false,
        false, false, false, false, false,
        false, false, false, false, false,
        false, false, false, false, false
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
        "It looks very dense. There might be something lurking here.",
        "It looks very dense. There might be something lurking here.",

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
        "The terrain is very rough. There might be something lurking here.",
        "The terrain is very rough. There might be something lurking here.",
        "The terrain is very rough. There might be something lurking here."
    };

    // Whether the location has combat
    private static final boolean[] locationHasCombat = {
        false, false, true, false, false,
        false, true,  true, true,  true,
        true,  true,  true, true,  true,
        true,  true,  true, true,  true,
        true,  true,  true, true,  true
    };

    // The maximum number of possible combat encounters at the location
    private static final int[] possibleCombatEncounters = {
        0, 0, 4, 0, 0,
        0, 1, 4, 1, 1,
        1, 1, 4, 4, 2,
        1, 1, 2, 4, 2,
        2, 2, 2, 2, 2
    };

    public static void navigate(Player player) {
        boolean validChoiceHandler = true;

        // Calculate the coordinates and relative index of the player's location
        playerX = currentLocation % 5;
        playerY = currentLocation / 5;
        characterLocationIndex = playerY * 5 + playerX;

        // Print the current location and player options
        print("You are currently in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET + ", where do you want to go?");
        lineBreak();

        // Print the map
        map();

        // Print the player's movement options
        printColour("'W' - Up, 'A' - Left, 'S' - Down, 'D' - Right, '0' - Go Back", ANSI_TEXT_BLUE);
        lineBreak();

        while (validChoiceHandler) {
            // Get the player's movement direction
            printColour(" > ", ANSI_TEXT_GREEN);
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
                            validChoiceHandler = false;
                        } else {
                            clearLine(1);
                            continue;
                        }
                    }
                    case 'A' -> {
                        if (playerX != 0) {
                            currentLocation -= 1;
                            validChoiceHandler = false;
                        } else {
                            clearLine(1);
                            continue;
                        }
                    }
                    case 'S' -> {
                        if (playerY != 4) {
                            currentLocation += 5;
                            validChoiceHandler = false;
                        } else {
                            clearLine(1);
                            continue;
                        }
                    }
                    case 'D' -> {
                        if (playerX != 4) {
                            currentLocation += 1;
                            validChoiceHandler = false;
                        } else {
                            clearLine(1);
                            continue;
                        }
                    }
                    case '0' -> {
                        validChoiceHandler = false;
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
                print("You are now in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex] + "\"");
                lineBreak();


                // Check if the location has combat and if the player has not visited it
                if (locationHasCombat[characterLocationIndex] && !visitedLocations[characterLocationIndex]) {

                    int encounterChance = random.nextInt(100);

                    if (encounterChance < 66) {
                        int enemyCount = random.nextInt(possibleCombatEncounters[characterLocationIndex]) + 1;
                        print("You have encountered " + enemyCount + " enemies!");
                        lineBreak();

                        Combat.combat(player, enemyCount);
                    } else {
                        print("You did not encounter any enemies.");
                        lineBreak();
                    }
                }

                // Update the visited locations
                visitedLocations[characterLocationIndex] = true;
            }
        }
    }

    /*
      public static void map() {
        // Calculate player's current location
        playerX = currentLocation % 5;
        playerY = currentLocation / 5;

        // Print the map
        for (int y = 0; y < 5; y++) {
            System.out.println("+---+---+---+---+---+");
            for (int x = 0; x < 5; x++) {
                System.out.print("| ");
                if (playerY == y && playerX == x) {
                    printColour("P ", ANSI_TEXT_GREEN); // Player's current location
                } else if ((playerY == y && (playerX == x - 1 || playerX == x + 1)) || (playerX == x && (playerY == y - 1 || playerY == y + 1))) {
                    printColour("O ", ANSI_TEXT_YELLOW); // Travelable locations
                } else {
                    printColour("- ", ANSI_TEXT_RED); // Non-travelable locations
                }
            }
            System.out.println("|");
        }
        System.out.println("+---+---+---+---+---+");
    }
    */
    public static void map() {
        // Calculate player's current location
        playerX = currentLocation % 5;
        playerY = currentLocation / 5;

        for (int y = 0; y < 5; y++) {
            System.out.println("+---+---+---+---+---+");
            for (int x = 0; x < 5; x++) {
                System.out.print("| ");
                int idx = y * 5 + x;
                if (playerY == y && playerX == x) {
                    printColour("P ", ANSI_TEXT_GREEN); // Player's current location
                } else {
                    // Get the first character of the area's name
                    String areaName = locationNames[idx];
                    char firstChar = Character.toUpperCase(areaName.charAt(0));
                    // Optionally: color based on visited/unvisited
                    if (visitedLocations[idx]) {
                        printColour(firstChar + " ", ANSI_TEXT_YELLOW); // Visited
                    } else {
                        printColour(firstChar + " ", ANSI_TEXT_RED);    // Not visited
                    }
                }
            }
            System.out.println("|");
        }
        System.out.println("+---+---+---+---+---+");
    }

    public static void viewMap() {
        // Calculate the coordinates and relative index of the player's location
        playerX = currentLocation % 5;
        playerY = currentLocation / 5;
        characterLocationIndex = playerY * 5 + playerX;

        // Print the player's current location
        System.out.println("You are currently in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET);

        // Print the map
        map();

        // Show possible directions and destination names and descriptions
        if (playerY > 0) {
            print(ANSI_TEXT_YELLOW + "Above" + ANSI_RESET + " you is the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex - 5] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex - 5] + "\"");
            lineBreak();
        }
        if (playerY < 4) {
            print(ANSI_TEXT_YELLOW + "Below" + ANSI_RESET + " you is the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex + 5] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex + 5] + "\"");
            lineBreak();
        }
        if (playerX > 0) {
            print("To the " + ANSI_TEXT_YELLOW + "Left" + ANSI_RESET + " of you is the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex - 1] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex - 1] + "\"");
            lineBreak();
        }
        if (playerX < 4) {
            print("To the " + ANSI_TEXT_YELLOW + "Right" + ANSI_RESET + " of you is the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex + 1] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex + 1] + "\"");
            lineBreak();
        }
    }

    public static void search(Player player) {

    }

    public static boolean playerHasVisitedLocation() {
        return visitedLocations[characterLocationIndex];
    }
}