package utils;
import java.util.Random;
import java.util.Scanner;

public class Combat {

    public static Scanner sc = new Scanner(System.in);
    private static Random diceRoll = new Random();

    // Make player and monster static so they are accessible in static methods
    private static Player player = new Player();
    private static Enemy enemy = new Enemy();
    private static boolean alive = true;

    public static void copyPlayer(Player tempPlayer) {
        player.setName(tempPlayer.getName());
        player.setHealth(tempPlayer.getHealth());
        player.setWeapon(tempPlayer.getWeapon());
    }

    public static void combat() {
        int enemySelection = diceRoll.nextInt(2);
        int weaponSelection = diceRoll.nextInt(3);

        // Player Weapons
        Weapon[] playerWeapons = {
            new Weapon("", 3, 1000000, 1, 1),
            new Weapon("Pistol", 6, 0, 2, 2),
            new Weapon("Rifle", 15, 12, 3, 3)
        };

        // Enemies
        Enemy[] enemies = {
            new Enemy("Wolf", 10, 3),
            new Enemy("Bear", 20, 6)
        };

        // Wolf Attacks
        Weapon[] wolfWeapons = {
            new Weapon("Headbutt", 2, 1000000, 2, 1),
            new Weapon("Claws", 3, 1000000, 1, 1),
            new Weapon("Bite", 4, 1000000, 2, 1)
        };

        // Bear Attacks
        Weapon[] bearWeapons = {
            new Weapon("Punch", 4, 1000000,2, 1),
            new Weapon("Kick", 5, 1000000, 2, 1),
            new Weapon("Crush", 6, 1000000, 1, 1)
        };

        // Enemy Weapons
        Weapon[] enemyWeapons = {
            wolfWeapons[weaponSelection],
            bearWeapons[weaponSelection]
        };

        // Set Enemy and Weapon
        enemy = enemies[enemySelection];
        enemy.setWeapon(enemyWeapons[weaponSelection]);


        int tempPlayerHealth, tempEnemyHealth;
        int playerAttackRoll, enemyAttackRoll;
        boolean continueFighting = true;
        boolean fleeFighting;

        while (continueFighting) {
            System.out.println("Player Health left: " + player.getHealth());
            System.out.println("\nWhat would you like to do?");
            System.out.println("[f]ight\n[r]un");
            String choice = sc.nextLine().toLowerCase();

            switch (choice) {
                case "f" -> {
                    // human attack
                    System.out.printf("you attempted to hit %s with your %s\n", enemy.getName(), player.getWeapon().getName());
                    playerAttackRoll = diceRoll.nextInt(6) + 1; // 1-6 instead of 0-5
                    int playerDamage = playerAttackRoll + player.getWeapon().getDamage(); // weapon damage
                    if (playerAttackRoll <= 1) {
                        System.out.printf("you failed to hit %s\n", enemy.getName());
                    } else {
                        System.out.printf("\nyou hit %s for %d\n", enemy.getName(), playerDamage);
                        tempEnemyHealth = enemy.getHealth() - playerDamage;
                        enemy.setHealth(tempEnemyHealth);
                    }

                    if (enemy.getHealth() > 0) {
                        System.out.printf("\n%s has %d health left\n", enemy.getName(), enemy.getHealth());
                        System.out.printf("the monster attempted to hit %s with its %s\n", player.getName(), enemy.getWeapon().getName());
                        enemyAttackRoll = diceRoll.nextInt(6) + 1;
                        int enemyDamage = enemyAttackRoll + enemy.getWeapon().getDamage();
                        if (enemyAttackRoll <= 1) {
                            System.out.printf("%s failed to hit %s\n", enemy.getName(), player.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", enemyDamage);
                            tempPlayerHealth = player.getHealth() - enemyDamage;
                            player.setHealth(tempPlayerHealth);
                        }
                    }
                }

                case "r" -> {
                    fleeFighting = diceRoll.nextBoolean();
                    if (fleeFighting) {
                        System.out.println("you have fleed successfully. tutorial will end now ");
                        continueFighting = false;
                    } else {
                        System.out.println("you failed to flee, the battle continues");
                        enemyAttackRoll = diceRoll.nextInt(6) + 1;
                        System.out.printf("%s has %d health left\n", enemy.getName(), enemy.getHealth());
                        System.out.printf("the monster attempted to hit %s\n", player.getName());
                        if (enemyAttackRoll <= 1) {
                            System.out.printf("%s failed to hit %s\n", enemy.getName(), player.getName());
                        } else {
                            System.out.printf("you get hit for %d\n", enemyAttackRoll);
                            tempPlayerHealth = player.getHealth() - enemyAttackRoll;
                            player.setHealth(tempPlayerHealth);
                        }
                    }
                }

                default -> System.out.println("Invalid choice");
            }
            if (player.getHealth() <= 0) {
                alive = false;
                continueFighting = false;
                System.out.println("You failed your mission, game over.");
                break;
            }
            if (enemy.getHealth() <= 0) {
                continueFighting = false;
                System.out.println("You have defeated the enemy, continue your mission.");
                break;
            }
        }
    }
}