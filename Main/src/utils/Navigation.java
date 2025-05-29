package utils;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * Navigation class handles the player's whereabouts throughout the game world in a 5x5 grid system.
 * This class is not meant to be instantiated.
 * Contains static methods and data
 * Each grid cell corresponds to a location with a name, description, and potential enemy encounters.
 * Coordinates location updates, movement options, map rendering, and combat events.
 * This class contains static methods and data
 * F5AC Group 2 - [Aditya, Dutt, Angus]
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

    /**
     * Handles player navigation by taking input directions and updating the player's location.
     * Prints the map, current location, and movement options.
     * Automatically triggers combat if the new location supports it.
     *
     * @param player the Player object representing the current player state
     */
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

    /**
     * Prints the 5x5 grid map to the console showing the player's current position,
     * travelable adjacent locations, and inaccessible locations.
     * Player position is marked with 'P', adjacent travelable locations with 'O',
     * and non-travelable locations with '-'.
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
    /**
     * Displays the player's current location, the map, and descriptions of adjacent locations.
     * Useful for providing context and surroundings to the player.
     */
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
    /**
     * Displays a mission-focused map view with special markers for unsearched locations (red X),
     * NPC locations (blue X), and already searched locations (green X).
     * Other locations are blank.
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
    /**
     * Gets the player's current location name.
     * @return the name of the current location as a String
     */
    public static String getLocationName() {
        return locationNames[characterLocationIndex];
    }


    // To check if the player is able to rest at the lcoation
    /**
     * Checks whether the player has rested at the current location.
     * @return {@code true} if the player has rested here, {@code false} otherwise
     */
    public static boolean getRestedLocations() {
        return restedLocations[characterLocationIndex];
    }

    // To check if the played has searched the location
    /**
     * Returns the search index of the current location.
     * The index indicates whether the location has searchable items or if it has been searched.
     * @return an integer representing the location search state
     */
    public static int getLocationSearchIndex() {
        return locationSearchIndex[characterLocationIndex];
    }

    // To check the possible encounters the player can have at the location
    /**
     * Returns the maximum number of possible combat encounters at the current location.
     * @return the number of possible combat encounters
     */
    public static int getPossibleEncounters() {
        return possibleCombatEncounters[characterLocationIndex];
    }

    // Update the search index to indicate that it has already been searched
    /**
     * Marks the current location as already searched by updating the search index.
     * Sets the location's search index to 9.
     */
    public static void updateLocationSearchIndex() {
        locationSearchIndex[characterLocationIndex] = 9;
    }

    // Update the rested locations
    /**
     * Updates the rested status of the current location, marking it as not rested.
     */
    public static void updateRestedLocations() {
        restedLocations[characterLocationIndex] = false;
    }

    // Update the location fight status
    /**
     * Updates the combat status of the current location, marking it as no longer having combat encounters.
     */
    public static void updateLocationFightStatus() {
        locationHasCombat[characterLocationIndex] = false;
    }
}