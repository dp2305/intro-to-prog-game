package utils;
import java.util.Scanner;

public class NPC {
    private String name;

    public NPC(String name) {
        this.name = name;
    }

    public void briefMission() {
        Scanner sc = new Scanner(System.in);

        System.out.println(name + ": Soldier, listen up.");

        System.out.println(name + ": We've lost contact with 9 key individuals.");
        System.out.println(name + ": Each of them is stranded in a hostile zone. Your mission is to extract them.");

        System.out.print(name + ": Do you understand the mission? (yes/no): ");
        String response = sc.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println(name + ": Good. We’re counting on you.");
        } else {
            System.out.println(name + ": Shut the hell up. In fact, I've got your whole family hostage, so get a move on before I get someone to blow their brains out.");
        }
        
        System.out.println(name + ": First location: Your mum's house.");
        System.out.println(name + ": An virologist is trapped inside. She has vital intel. Bring her back alive.");

        // The game would proceed from here
    }
}
