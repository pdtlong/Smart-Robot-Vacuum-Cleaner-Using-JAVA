# Smart Robot Vacuum Cleaner Simulator Using Java
---
This is my first AI Project (late 2018) with my close friend about building a simulation of a smart home cleaning robot based on the **Spiral Spanning Tree Coverage (STC)** algorithm.

**Main functions performed:**
- Move around the map of the house, or room and do not repeat the cleaned position.
-Look for stains on the floor that need cleaning on the way around the room.
- Avoid the obstacles in front of you when moving and wait for the obstacle to move to another place.
- Automatically find the charger when the battery is almost empty (less than or equal to 10%).

----
## So, How to Run ? **(Must have installed java)**

- Step 1: Go to Folder: .\Final Demo\dist
- Step 2: Right click to **Robot_cleaner_AI_Final.jar**
or
 - Run by cmd:
 ```
 java -jar "Robot_cleaner_AI_Final.jar" 
 ```
Because of the time limit and project scope, the house map will be provided for the robot. So it will run offline
now the problem is how to move around the whole house without repeating again. Otherwise, make sure to perform all the remaining tasks assigned below:

-----
## DEMO:

- **Add VB** : Used to add stains on the floor
- **Add VC**: Used to add moving obstacles
- **Start**: to start cleaning
- **Quick**: Increases movement speed x2
- **Power**: the power level can automatically set the battery for the robot (ex: 80)

https://user-images.githubusercontent.com/55480300/178575965-b4d72ee8-bd24-4299-b806-75a2195afdff.mp4

