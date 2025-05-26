package utils;
import java.util.Scanner;

public class NPC {
    private final String name;
    private final Scanner passedInScanner;

    public NPC(String name, Scanner passedInScanner) {
        this.name = name;
        this.passedInScanner = passedInScanner;
    }

    public void briefMission() {

        System.out.println(name + ": Soldier, listen up.");

        System.out.println(name + ": We've lost contact with 9 key individuals.");
        System.out.println(name + ": Each of them is stranded in a hostile zone. Your mission is to extract them.");

        System.out.print(name + ": Do you understand the mission? ((y)es/(n)o): ");
        String response = passedInScanner.nextLine();

        if (response.equalsIgnoreCase("y")) {
            System.out.println(name + ": Good. We're counting on you.");
        } else {
            System.out.println(name + ": Shut the hell up. In fact, I've got your whole family hostage, so get a move on before I get someone to blow their brains out.");
        }

        System.out.println(name + ": First location: Your mum's house.");
        System.out.println(name + ": An virologist is trapped inside. She has vital intel. Bring her back alive.");

        // The game would proceed from here
    }
}
