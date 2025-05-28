## Issues List
1. Input was being incorrectly read using `sc.next()` instead of `sc.nextLine()`, causing errors when handling and validating such inputs
	- Discovered by Aditya
	- Fixed by replacing `sc.next()` with `sc.nextLine()` to properly capture  input
2. The displayed location name was incorrect because it wasn't being updated when the player moved
	- Discovered by Angus/Kai
	- Fixed by updating the location name before and after the player changes locations to correctly display location names
3. The player couldn't rest at any location because the `rest()` method was using the location which the player had already visited
	- Discovered by Aditya
	- Fixed by switching from the visited locations to its own `rest location` variable
4. Using items in different orders caused errors due to items shifting around in the inventory
	- Discovered by Dutt
	- Fixed by implementing a more reliable method for handling item
5. Items found during a search weren't added to the backpack, making them unusuable
	- Discovered by Dutt
	- Fixed by ensuring searched items are added to the backpack after the player searches for them
6. Player damage output was doubled due to damage being added twice during combat
	- Discovered by Aditya
	- Fixed by correcting the combat logic to apply damage only once per attack
7. Ammo count did not decrease after using it in combat, leading to infinite ammo
	- Discovered by Angus/Kai
	- Fixed by decreasing the ammo count after a weapon is used
8. Flee chance increased each time the player tried to escape, making fleeing easier instead of harder
	- Discovered by Aditya
	- Fixed by adjusting the flee mechanic to decrease success probability on repeated attempts
9. Replaying the game after it ended didn't reset game state, causing previous items and location data to remain attached to the player
	- Discovered by Angus/Kai
	- Fixed by creating a new player instance on each game restart to fully reset the game state
10. After defeating an enemy, players couldn't encounter any enemies in other locations
	- Discovered by Dutt
	- Fixed by resetting the enemy encounter state after each battle