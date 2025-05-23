package utils;
import java.util.Scanner;

public class GameStart {
    private static final Scanner sc = new Scanner(System.in);
    public static String MC;
    public static char weaponChosen;

   // Constructor that automatically starts the game when a GameStart object is created.

    public GameStart(){
        start();
    }

    public static String start() {
        System.out.println("Choose your name warrior: ");
        MC = sc.nextLine();
        System.out.println("\nBackground information:");

        return "Game Start";
    }
    // Displays the background story of the mission to the player.
    public static String backgroundInformation() {
        System.out.printf("%s you have been chosen for a important reconnaissance mission. \nyou objective is to rescue scientists that were conducting important reseach.",MC);
        System.out.println("\nAlong the way you will find other squads which were deployed earlier but failed to finish the task");
        System.out.println("God Speed soldier.");
        return "";
    }

    // Prompts the player to choose a weapon and begins the mission.

    public static String MissionStart() {
        System.out.println("\nChoose a weapon to start with");
        System.out.println("(K)nife, (P)istol, (R)ifle");
        weaponChosen  = sc.next().charAt(0);
        System.out.println("you are inside of a helicopter, about to be dropping into the mission area. ");
        return "";
    }
}
