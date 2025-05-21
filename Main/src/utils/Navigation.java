package utils;
import java.util.Scanner;

public class Navigation {

    public static Scanner sc = new Scanner(System.in);
    private static int currentLocation = 220;
    private static int tempLocation;
    private static boolean gameRunning = true;

    public static void main(String[] args) {
        System.out.println("Welcome to the Nav tutorial!");

        do {
            navigation();
        } while (gameRunning);
    }

    public static void navigation() {
        tempLocation = currentLocation;
        System.out.println("You are currently at " + currentLocation);
        System.out.println("Which direction would you like to go?");
        System.out.println("[N]orth, [E]ast, [W]est, [S]outh");
        System.out.println("Press Q to quit");
        System.out.println("Enter direction: ");
        String directionEntered = sc.nextLine().toLowerCase();

        switch (directionEntered) {
            case "n" -> {
                currentLocation = currentLocation + 100;
                goToLocation(currentLocation);
            }
            case "e" -> {
                currentLocation = currentLocation + 10;
                goToLocation(currentLocation);
            }
            case "w" -> {
                currentLocation = currentLocation - 10;
                goToLocation(currentLocation);
            }
            case "s" -> {
                currentLocation = currentLocation - 100;
                goToLocation(currentLocation);
            }
            case "q" -> {
                System.out.println("Quitting...");
                gameRunning = false;
            }
            default -> {
                System.out.println("Please enter a valid direction");
            }
        }
    }

    public static void goToLocation(int locationEntered) {
        switch (locationEntered) {
            case 120 -> room120();
            case 130 -> room130();
            case 210 -> room210();
            case 220 -> room220();
            case 320 -> room320();
            default -> {
                System.out.println("No path forward this way");
                currentLocation = tempLocation;
            }
        }
    }

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


