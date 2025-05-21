package utils;
import java.util.Scanner;

public class Locations {
    private static final Scanner sc = new Scanner(System.in);

    private static int currentLocation = 11;

    private static final int[][] ROOM_LOCATION = new int [5][5];

    private static void map() {
        int[] mapLocation = new int[3];
        int index = 0;

        int num = currentLocation;

        char plocation = 'P'; //Displays the location of the player's current location
        char noroom = '-'; // this shows the areas that aren't accessible;
        char room = 'O';  //displays when an area can be visited

        // run a while loop to break up the current location into individuals digits
        while(num > 0){
            int digit = num % 10;
            mapLocation[index] = digit;
            index++;
            num = num / 10;
        }

        //here we will draw the map on screen. My map is 3X3.
        // My map uses 1001 at the base, 300 at the top for navigation
        // Start the cout at 3, go to 1 so 300 is at the top of the map

        for (int i = 3; i > 0; i--) {
            System.out.println();
            System.out.println("---------------");

            //horizontal coordinates for the map
            for (int j = 1 ; j < 4 ; j++){
                System.out.print("| ");

                //code used to check if the room exitsts as a part of the Room_LOCATIONS array
                boolean isRoom = false;
                for (int k = 0; k < ROOM_LOCATION.length; k++) {
                    if (ROOM_LOCATION[k][0] == i && ROOM_LOCATION[k][1] == j) {
                        isRoom = true;
                        break;
                    }
                }

                //will print player location if the mapLocation indexes match i and j
                if(mapLocation[2] == i && mapLocation[1] == j){
                    System.out.print(plocation);
                }
                else if (isRoom == true){
                    System.out.print(room);
                }
                else{
                    System.out.print(noroom);
                }
                System.out.print(" |");

            }
        }
        System.out.println("\n---------------\n");
    }
}
