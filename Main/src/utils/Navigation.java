package utils;
import java.util.Scanner;


public class Navigation {

    // Scanner to get user input from the console
    public static Scanner myImput = new Scanner(System.in);
    // Current room location (starting at 220)
    private static int currentLocation = 220;
    // Temporary variable to store location in case of invalid move
    private static int tempLocation;
    // Controls whether the game is running or not
    private static boolean gameRunning = true;

    public static void main(String[] args) {

        System.out.println("Welcome to the Nav tutorial!");

        // Main game loop: runs while the game is active
        do {
            // Handle player navigation
            navigation();
        } while (gameRunning);

    }

    // Handles the navigation logic and user input
    public static void navigation() {
        // Save current location in case of invalid move
        tempLocation = currentLocation;
        // Display current location and direction options
        System.out.println("You are currently at " + currentLocation);
        System.out.println("Which direction would you like to go?");
        System.out.println("[n]orth, [e]ast, [w]est, [s]outh\n");
        System.out.println("Press Q to quit");
        System.out.println("Enter direction: ");
        // Get and normalise user input
        String directionEntered = myImput.nextLine().toLowerCase();

        // Adjust location based on direction entered
        switch (directionEntered) {
            case "n" ->  {
                currentLocation = currentLocation + 100;
                goToLocation(currentLocation);
            }
            case "e" ->  {
                currentLocation = currentLocation + 10;
                goToLocation(currentLocation);
            }
            case "w" ->  {
                currentLocation = currentLocation - 10;
                goToLocation(currentLocation);
            }
            case "s" ->  {
                currentLocation = currentLocation - 100;
                goToLocation(currentLocation);
            }
            // Quit the game
            case "q" -> {
                System.out.println("Quitting...");
                gameRunning = false;
            }
            // Handle invalid input
            default -> {
                System.out.println("Please enter a valid direction");
            }
        }
    }
    // Checks if the new location is valid and calls the corresponding room method
    public static void goToLocation(int locationEntered) {

        switch (locationEntered) {
            case 120 -> room120();
            case 130 -> room130();
            case 210 -> room210();
            case 220 -> room220();
            case 320 -> room320();
            default -> {
                // If location is invalid, restore previous location
                System.out.println("No path forward this way");
                currentLocation = tempLocation;
            }
        }
    }

    // Displays message for each room
    public static void room120() {
        System.out.println("You are currently at room 120");
    }

    public static void room130() {
        System.out.println("You are currently at room 130");
    }

    public static void room210() {
        System.out.println("You are currently at room 210");
    }

    public static void room220() {
        System.out.println("You are currently at room 220");
    }

    public static void room320() {
        System.out.println("You are currently at room 320");
    }
}