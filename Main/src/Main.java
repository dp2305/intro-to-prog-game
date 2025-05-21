import java.util.Scanner;
import static utils.Game_start.*;
import utils.Game_start;
import utils.Locations;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        GameStart();
        backgroundInformation();
        MissionStart();

        //this is the intro code that gives the plaver the first choice.
        System.out.println("you have been dropped of at the main base.");
        System.out.println("what would you like to do from here");
        System.out.println("(S)earch \n (L)ook around");
        firstChoice = sc.next().charAt(1);
    }
}