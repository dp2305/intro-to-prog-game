package utils;
import java.util.Scanner;

public class Locations {
    private static Scanner sc = new Scanner(System.in);

    private static int currentLocation = 220;

    private static final int[][] ROOM_LOCATION = new int [5][5];

    public static void main(String[] args) {
        map();
    }

    private static void map() {
        int[] mapLocation = new int[3];
        int index = 0;
        int num = currentLocation;

        char playerLocation = 'P'; // Displays the location of the player's current location
        char noRoom = '-';    // This shows the areas that aren't accessible
        char room = 'O';      // Displays when an area can be visited

        // Run a while loop to break up the current location into individual digits
        while(num > 0) {
            int digit = num % 10;
            mapLocation[index] = digit;
            index++;
            num = num / 10;
        }

        // Here we will draw the map on screen. Map is 3x3.
        // Map uses 100 at the base, 300 at the top for navigation
        // Start the count at 3, go to 1 so 300 is at the top of the map
        for (int i = 3; i > 0; i--) {
            System.out.println();
            System.out.println("---------------");

            // Horizontal coordinates for the map
            for (int j = 1; j < 4; j++) {
                System.out.print("| ");

                // Code used to check if the room exists as a part of the ROOM_LOCATION array
                boolean isRoom = false;
                for (int k = 0; k < ROOM_LOCATION.length; k++) {
                    if (ROOM_LOCATION[k][0] == i && ROOM_LOCATION[k][1] == j) {
                        isRoom = true;
                        break;
                    }
                }

                // Will print player location if the mapLocation indexes match i and j
                if (mapLocation[2] == i && mapLocation[1] == j) {
                    System.out.print(playerLocation);
                }
                else if (isRoom) {
                    System.out.print(room);
                }
                else {
                    System.out.print(noRoom);
                }
                System.out.print(" |");
            }
        }
        System.out.println("\n---------------\n");
    }
}
