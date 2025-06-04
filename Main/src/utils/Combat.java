package utils;
import java.util.Random;
import java.util.Scanner;
import static utils.Formatting.*;

/**
 * The {@code Combat} class is where the player fights against enemies.
 * It randomly picks enemies and their attacks, and gives the player an option to fight, run or use items.
 * It does not end until the player defeats all enemies, runs away, or dies.
 */
public class Combat {

   private static final Scanner sc = new Scanner(System.in);
   private static final Random random = new Random();
   private static int fleeCount = 0; // The number of times the player has fled an encounter - set outside of the method so it doesn't reset every time the method is run

   /**
    * Handles a full combat encounter for the player, where they can face one or more enemies, each with their own health and attacks.
    * Players can choose to fight, flee, or use items during their turn.
    * The combat loop continues until the player dies, flees successfully, or defeats all enemies.
    *
    * @param player The player object involved in combat
    */
   public static void combat(Player player) {
      boolean isPlayerAlive = player.getIsAlive();

      while (isPlayerAlive && player.getHealth() > 0) {
         // All Enemies
         Enemy[] enemies = {
            new Enemy("Snake",            5),
            new Enemy("Raven",            7),
            new Enemy("Vulture",          7),
            new Enemy("Wolf",             10),
            new Enemy("Wild Boar",        15),
            new Enemy("Bear",             20),
            new Enemy("Sabretooth Tiger", 25)
         };

         // All Enemy Attacks
         // Snake Attacks
         Attacks[] snakeAttacks = {
            new Attacks("Bite", 3),
            new Attacks("Coil", 4),
            new Attacks("Poison", 5)
         };

         // Raven Attacks
         Attacks[] ravenAttacks = {
            new Attacks("Beak", 3),
            new Attacks("Charge", 3),
            new Attacks("Talons", 4)
         };

         // Vulture Attacks
         Attacks[] vultureAttacks = {
            new Attacks("Beak", 3),
            new Attacks("Charge", 3),
            new Attacks("Talons", 5)
         };

         // Wolf Attacks
         Attacks[] wolfAttacks = {
            new Attacks("Headbutt", 2),
            new Attacks("Claws", 3),
            new Attacks("Bite", 4)
         };

         // Wild Boar Attacks
         Attacks[] wildBoarAttacks = {
            new Attacks("Kick", 4),
            new Attacks("Charge", 5),
            new Attacks("Ram", 6)
         };

         // Bear Attacks
         Attacks[] bearAttacks = {
            new Attacks("Punch", 4),
            new Attacks("Kick", 5),
            new Attacks("Hug", 6)
         };

         // Sabretooth Tiger Attacks
         Attacks[] sabretoothTigerAttacks = {
            new Attacks("Headbutt", 4),
            new Attacks("Bite", 5),
            new Attacks("Claws", 7)
         };

         // Variables for combat
         int playerAttackChance, enemyAttackChance;
         boolean continueFighting;
         boolean hasTriedToFlee = false;
         int enemyCount; // The number of enemies in the encounter
         int fightCount = 0; // The number of enemies the player has defeated
         int encounterChance = random.nextInt(100);

         // The chance of the player encountering an enemy. Chance increases with weight
         if (encounterChance < 60 + (2 * player.getWeight())) {
            enemyCount = random.nextInt(Navigation.getPossibleEncounters()) + 1;
            print(YELLOW, "<=-- Combat --=>");
            lineBreak();

            if (enemyCount == 1) {
               print("You have encountered an enemy!");
            } else {
               print("You have encountered " + enemyCount + " enemies!");
            }
         } else {
            print("You did not encounter anything dangerous.");
            lineBreak();
            break; // Exit the loop if no encounter
         }
         lineBreak();

         while (fightCount < enemyCount) {
            player.setIsFighting(true);
            continueFighting = true;

            // Selects an index for a random enemy every time a new fightr begins
            int enemySelection = random.nextInt(7); // 0 to 6, since there are 7 enemy types

            // Set random Enemy based on random index outside of the main while loop so it doesn't change every fight
            Enemy enemy = enemies[enemySelection];

            print("You're fighting a " + colour(YELLOW, enemy.getName()) + ".");
            lineBreak();

            while (continueFighting) {
               // Select a random attack for the selected enemy
               int weaponSelection = random.nextInt(3); // 0 to 2, since each enemy has 3 attacks

               // All enemy weapons
               Attacks[] enemyAttacks = {
                  snakeAttacks[weaponSelection],
                  ravenAttacks[weaponSelection],
                  vultureAttacks[weaponSelection],
                  wolfAttacks[weaponSelection],
                  wildBoarAttacks[weaponSelection],
                  bearAttacks[weaponSelection],
                  sabretoothTigerAttacks[weaponSelection]
               };

               enemy.setAttack(enemyAttacks[enemySelection]);

               // Variance in damage for attacks
               int damageVariancePlayer = random.nextInt(5) - 3; // Randomise damage with a variance of -2 to 2
               int damageVarianceEnemy = random.nextInt(3) - 1; // Randomise damage with a variance of -1 to 1
               int weaponAccuracy = player.getWeapon().getRange() * 2;

               int totalPlayerDamage = player.getWeapon().getDamage() + damageVariancePlayer;
               int totalEnemyDamage = enemy.getAttack().getDamage() + damageVarianceEnemy;

               print("You have " + colour(YELLOW, player.getHealth() + " health") + ".");
               lineBreak();
               print("What would you like to do? " + colour(BLUE, "(1) Fight, (2) Run, (3) Use Item"));
               lineBreak();
               print(GREEN, " > ");
               String choice = sc.nextLine().toUpperCase();

               if (choice.isEmpty()) {
                  clearLine(3);
                  continue;
               } else {
                  char choiceChar = choice.charAt(0);

                  switch (choiceChar) {
                     // Attack sequenbce
                     case '1' -> {
                        // Player attack
                        print("You attempted to attack the " + colour(YELLOW, enemy.getName()) + ".");
                        lineBreak();
                        playerAttackChance = random.nextInt(100); // 1-6 instead of 0-5

                        if (player.getWeapon().getAmmo() <= 0) {
                           // Search for an ammo item in the player's backpack
                           boolean foundAmmo = false;
                           int backpackSize = player.getBackpackSize();

                           for (int i = 0; i < backpackSize; i++) {
                              Items item = player.getBackpackItem(i);
                              if (item.getType() == 1) { // type 1 = ammo
                                 player.getWeapon().setAmmo(player.getWeapon().getAmmo() + item.getValue());
                                 print("You were out of ammo. You quickly used the " + colour(YELLOW, item.getName()) + " and added " + colour(YELLOW, item.getValue() + " ammo") + " to your " + colour(YELLOW, player.getWeapon().getName()) + ".");
                                 player.removeBackpackItem(item);
                                 foundAmmo = true;
                                 break;
                              }
                           }

                           if (!foundAmmo) {
                              print("You ran out of ammo for your " + colour(YELLOW, player.getWeapon().getName()) + "... ");
                              lineBreak();

                              boolean fleeChance = random.nextBoolean();

                              if (fleeChance) {
                                 print("You were able to flee, continue your mission.");
                              } else {
                                 print("You failed to flee and got defeated by the enemy.");
                                 continueFighting = false;
                                 isPlayerAlive = false;
                                 player.setIsAlive(isPlayerAlive);
                                 fightCount = 100;
                                 break;
                              }
                           }
                           lineBreak();
                        }

                        // Player attack sequence - player has a 80% chance to hit the enemy, chance increases with weapon accuracy
                        if (playerAttackChance <= 80 + weaponAccuracy) {
                           print("You hit the " + colour(YELLOW, enemy.getName()) + " for " + colour(YELLOW, totalPlayerDamage + " damage") + ".");
                           enemy.setHealth(enemy.getHealth() - (totalPlayerDamage));
                        } else {
                           print("Your attack missed the " + colour(YELLOW, enemy.getName()) + ".");
                        }
                        lineBreak();

                        player.getWeapon().setAmmo(player.getWeapon().getAmmo() - 1);

                        // Enemy attack sequence - failed to attack, attacked successfully - only run sequence if the enemy still has health
                        if (enemy.getHealth() > 0) {
                           print("The " + colour(YELLOW, enemy.getName()) + " has " + colour(YELLOW, enemy.getHealth() + " health") + ".");
                           lineBreak();
                           print("The " + colour(YELLOW, enemy.getName()) + " attempted to attack you with its " + colour(YELLOW, enemy.getAttack().getName()) + ".");
                           lineBreak();
                           enemyAttackChance = random.nextInt(6) + 1; // 1-6 instead of 0-5

                           if (enemyAttackChance <= 1) {
                              print("The " + colour(YELLOW, enemy.getName()) + " couldn't hit you." );
                           } else {
                              print("You got hit for " + colour(YELLOW, totalEnemyDamage + " damage") + ".");
                              player.setHealth(player.getHealth() - totalEnemyDamage);
                           }
                           lineBreak();
                        }
                     }
                     // Player flee sequence
                     case '2' -> {
                        int fleeFightChance;

                        // If the encounter has more than 1 enemy, the player cannot flee
                        if (hasTriedToFlee) {
                           print("You've already tried to flee and failed, continue fighting.");
                        } else {
                           if (enemyCount > 1) {
                              print("You cannot flee during an encounter with multiple enemies.");
                              lineBreak();
                              break; // Prevent fleeing if multiple enemies
                           } else {
                              // Otherwise a random number out of 100 is generated
                              fleeFightChance = random.nextInt(100);
                           }

                           if (fleeFightChance < 50 - fleeCount) {
                              print("You managed to escape successfully. Continue your mission.");
                              if (fleeCount != 30) {
                                 fleeCount+=10;
                              }
                              continueFighting = false;
                           } else {
                              print("You failed to escape, the battle continues.");
                              lineBreak();
                              print("The " + colour(YELLOW, enemy.getName()) + " has " + colour(YELLOW, enemy.getHealth() + " health") + ".");
                              lineBreak();
                              print("The " + colour(YELLOW, enemy.getName()) + " attempted to attack you with its " + colour(YELLOW, enemy.getAttack().getName()) + ".");
                              lineBreak();
                              enemyAttackChance = random.nextInt(6) + 1; // 1-5 instead of 0-5

                              if (enemyAttackChance <= 1) {
                                 print("The " + colour(YELLOW, enemy.getName()) + " failed to attack you.");
                              } else {
                                 print("You got hit for " + colour(YELLOW, totalEnemyDamage + " damage") + ".");
                                 player.setHealth(player.getHealth() - totalEnemyDamage);
                              }
                           }
                           hasTriedToFlee = true;
                        }
                        lineBreak();
                     }
                     // Player uses item
                     case '3' -> {
                        Items.useItem(player);
                     }
                     default -> {
                        clearLine(3);
                     }
                  }
               }

               if (player.getHealth() <= 0) {
                  continueFighting = false;
                  isPlayerAlive = false;
                  fightCount = 100;
                  player.setIsAlive(isPlayerAlive);
               }

               if (enemy.getHealth() <= 0) {
                  continueFighting = false;
                  fightCount++;
                  if (fightCount >= enemyCount) {
                     print("You survived this encounter, continue your mission.");
                     lineBreak();
                     break;
                  } else {
                     print("You survived this enemy, watch out for the next one.");
                     lineBreak();
                  }
               }
            }
            lineBreak();
         }
         if (fightCount >= enemyCount) {
            break;
         }
      }
      Navigation.updateLocationFightStatus();
      player.setIsFighting(false);
   }
}