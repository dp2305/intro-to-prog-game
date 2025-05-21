package utils;
import java.util.Random;
import java.util.Scanner;

public class Combat {

    public static Scanner sc = new Scanner(System.in);
    private static Random diceroll = new Random();

    // Make player and monster static so they are accessible in static methods
    private static GameCharacters pCharacter = new GameCharacters("JohnDoe", "Human", 20);
    private static GameCharacters monster1 = new GameCharacters("Demon", "monster", 10);
    private static boolean life = true;
    private static boolean gameRunning = true;

    public static void main(String[] args) {
        charCreate();
        do {
            combat(pCharacter, monster1);
        } while (gameRunning);
    }

    public static void charCreate() {
        System.out.println("-- Player Character Creation --");
        System.out.println("Please enter your name: ");
        String pname = sc.nextLine();
        pCharacter.setName(pname);
        System.out.println(pCharacter);
    }

    public static void combat(GameCharacters human, GameCharacters monster) {
        int temphumanhealth, tempmonsterhealth;
        int humanAttackroll, monsterAttackroll;
        boolean fighton = true;
        boolean flee = false;

        while (fighton) {
            System.out.println("Human Health left: " + human.getHealth());
            System.out.println("\nwhat would you like to do?");
            System.out.println("[f]ight\n[r]un");
            String choice = sc.nextLine().toLowerCase();

            switch (choice) {
                case "f":
                    // human attack
                    System.out.printf("you attempted to hit %s\n", monster.getName());
                    humanAttackroll = diceroll.nextInt(6) + 1; // 1-6 instead of 0-5
                    if (humanAttackroll <= 1) {
                        System.out.printf("you failed to hit %s\n", monster.getName());
                    } else {
                        System.out.printf("\nyou hit %s for %d\n", monster.getName(), humanAttackroll);
                        tempmonsterhealth = monster.getHealth() - humanAttackroll;
                        monster.setHealth(tempmonsterhealth);
                    }

                    if (monster.getHealth() > 0) {
                        monsterAttackroll = diceroll.nextInt(6) + 1;
                        System.out.printf("\n%s has %d health left\n", monster.getName(), monster.getHealth());
                        System.out.printf("the monster attempted to hit %s\n", human.getName());
                        if (monsterAttackroll <= 1) {
                            System.out.printf("%s failed to hit %s\n", monster.getName(), human.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", monsterAttackroll);
                            temphumanhealth = human.getHealth() - monsterAttackroll;
                            human.setHealth(temphumanhealth);
                        }
                    }
                    break;

                case "r":
                    flee = diceroll.nextBoolean();
                    if (flee) {
                        System.out.println("you have fleed successfully. tutorial will end now ");
                        fighton = false;
                        gameRunning = false;
                    } else {
                        System.out.println("you failed to flee, the battle continues");
                        monsterAttackroll = diceroll.nextInt(6) + 1;
                        System.out.printf("%s has %d health left\n", monster.getName(), monster.getHealth());
                        System.out.printf("the monster attempted to hit %s\n", human.getName());
                        if (monsterAttackroll <= 1) {
                            System.out.printf("%s failed to hit %s\n", monster.getName(), human.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", monsterAttackroll);
                            temphumanhealth = human.getHealth() - monsterAttackroll;
                            human.setHealth(temphumanhealth);
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
            if (human.getHealth() <= 0) {
                life = false;
                fighton = false;
                System.out.println("you died, game over");
                gameRunning = false;
                break;
            }
            if (monster.getHealth() <= 0) {
                fighton = false;
                System.out.println("the monster dies, game over");
                gameRunning = false;
                break;
            }
        }
    }

    // Move GameCharacters to its own file in real projects, but keep it static here for this example
    public static class GameCharacters {
        private String name;
        private String charType;
        private int health;

        public GameCharacters(String name, String charType, int health) {
            this.name = name;
            this.charType = charType;
            this.health = health;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCharType(String charType) {
            this.charType = charType;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public String getName() {
            return name;
        }

        public String getCharType() {
            return charType;
        }

        public int getHealth() {
            return health;
        }

        public String toString() {
            return String.format("--Character info--\nName: %s\nCharType: %s\nHealth: %d", name, charType, health);
        }
    }
}