import java.util.Scanner;
import utils.*;


public class Main {

    private static Scanner sc = new Scanner(System.in);
    public static char weaponChosen;
    private static GameCharacters playerCharacter = new GameCharacters("Dutt", "Human", 50);


    public static void main(String[] args) {


        start();

        //backgroundInformation();
        //MissionStart();

        //this is the intro code that gives the plaver the first choice.
        System.out.println("you have been dropped of at the main base.");
        System.out.println("what would you like to do from here");
        System.out.println("(S)earch \n (L)ook around");
    }


    public static String start() {
        System.out.println("Choose your name warrior: ");
        String pName = sc.nextLine();
        playerCharacter.setName(pName);
        System.out.println("\nBackground information:");

        return "Game Start";
    }


    public static String backgroundInformation() {
        System.out.printf("%s you have been chosen for a important reconnaissance mission. \nyou objective is to rescue scientists that were conducting important reseach.",playerCharacter.getName());
        System.out.println("\nAlong the way you will find other squads which were deployed earlier but failed to finish the task");
        System.out.println("God Speed soldier.");
        return "";
    }

    public static String MissionStart() {
        System.out.println("\nChoose a weapon to start with");
        System.out.println("(K)nife, (P)istol, (R)ifle");
        weaponChosen  = sc.next().charAt(0);
        System.out.println("you are inside of a helicopter, about to be dropping into the mission area. ");
        return "";
    }


}