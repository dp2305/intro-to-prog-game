package utils;
import java.util.Scanner;
import static utils.Formatting.*;

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
        false, false, false, false, false,
        false, false, false, false, false,
        false, false, false, false, false,
        false, false, false, false, false,
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

    public static void navigate(Player player) {
        boolean isValidChoice = true;

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

        while (isValidChoice) {
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
                print("You are now in the " + ANSI_TEXT_YELLOW + locationNames[characterLocationIndex] + ANSI_RESET + ". \"" + locationDescriptions[characterLocationIndex] + "\"");
                lineBreak();

                // Check if the location has combat
                if (locationHasCombat[characterLocationIndex]) {
                    Combat.combat(player);
                }
            }
        }
    }

    // Method to print a map based on the players location
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
                    printColour("P ", ANSI_TEXT_GREEN); // Player's current location
                } else if ((playerY == y && (playerX == x - 1 || playerX == x + 1)) || (playerX == x && (playerY == y - 1 || playerY == y + 1))) {
                    printColour("O ", ANSI_TEXT_YELLOW); // Travelable locations
                } else {
                    printColour("- ", ANSI_TEXT_RED); // Non-travelable locations
                }
            }
            print("|");
            lineBreak();
        }
        print("+---+---+---+---+---+");
        lineBreak();
    }

    // Different method to view the map as well as nearby locations
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

    // Method to view the map related to the mission
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
                        printColour("X ", ANSI_TEXT_RED); // Unsearched locations
                    }
                    case 3 -> {
                        printColour("X ", ANSI_TEXT_BLUE); // NPC locations
                    }
                    case 9 -> {
                        printColour("X ", ANSI_TEXT_GREEN); // Searched locations
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

    // To check if the player is at a certain loation
    public static String getLocationName() {
        return locationNames[characterLocationIndex];
    }

    // To check if the player is able to rest at the lcoation
    public static boolean getRestedLocations() {
        return restedLocations[characterLocationIndex];
    }

    // To check if the played has searched the location
    public static int getLocationSearchIndex() {
        return locationSearchIndex[characterLocationIndex];
    }

    // To check the possible encounters the player can have at the location
    public static int getPossibleEncounters() {
        return possibleCombatEncounters[characterLocationIndex];
    }

    // Update the search index to indicate that it has already been searched
    public static void updateLocationSearchIndex() {
        locationSearchIndex[characterLocationIndex] = 9;
    }

    // Update the rested locations
    public static void updateRestedLocations() {
        restedLocations[characterLocationIndex] = false;
    }

    // Update the location fight status
    public static void updateLocationFightStatus() {
        locationHasCombat[characterLocationIndex] = false;
    }
}