# LPOO1617_T6G5

|Table of Contents|
|:---------------:|
|[Package and class diagram (UML)](#package-and-class-diagram-uml)|
|[Design Patterns Used](#expected-design-patterns-used)|
|[GUI Design and Mock-Ups](#gui-design-and-mock-ups)|
|[List of Tests](#list-of-tests)|

## Package and class diagram (UML)
![UML Diagram](https://cloud.githubusercontent.com/assets/13498941/25568250/97bd0156-2df6-11e7-89f4-447b37c0c771.png)

## (Expected) Design Patterns Used

  * ### Model–view–controller (MVC) - architectural pattern
    ![MVC Pattern](https://cloud.githubusercontent.com/assets/13498941/25565780/c646a81e-2dc5-11e7-9bbd-5a8330b1cbbf.png)
    
    The Game features three main packages:
    * **Model**: It expresses the application's behavior in terms of the problem domain, independent of the user interface (Controller package). It directly manages the data, logic and rules of the application.
    * **View**: An output representation of the model's information.
    * **Controller**: Accepts input and converts it to commands for the model or view.
   
    Why we decided to use it:
    * facilitates simultaneous development;
    * seperates responsabilities;
    * reduces coupling of components and, therefore, facilitates testing;
    * models can have multiple views.


  * ### Observer
    ![Observer Pattern](https://cloud.githubusercontent.com/assets/13498941/25565835/08738738-2dc7-11e7-96d5-9f74cb6ac843.png)
  
    Participating classes:
    * Observers: GameController (implements InputListener), WorldContactListener (implements ContactListener).
    * Subjects: several GUI elements (extends Actor), EntityModel (which has Fixtures).
  
    Why we decided to use it:
    * Maintains consistency between related objects.
    * Permits cleaner code with event based design.
  
  
  * ### Factory
    ![Factory](https://cloud.githubusercontent.com/assets/13498941/25568305/a50c0ed2-2df7-11e7-9883-cd7e08b9e3cc.png)
    
    Participating classes: ViewFactory and GameScreen.
    
    Why we decided to use it: 
    * Creates objects without exposing the instantiation logic to the client (therefore allowing for higher abstraction and cleaner code in the GameScreen class).
  
  
  * ### Factory Method
    ![Factory Method](https://cloud.githubusercontent.com/assets/13498941/25565883/fbb6c1c6-2dc7-11e7-8301-0bdbcaa90a28.png)

    Participating classes: EntityView and all sub-classes (namely BallView, PowerUpView, ...).
    
    Why we decided to use it:
    * Allows the subclasses to decide which class to instantiate (useful for when a class knows it should create an object, but not it's type).
    
    
  * ### State
  
    Participating classes: 
  
    Why we decide to use it:
    * Useful for making the code more logic and understandable.
    * When the internal state of an object changes, it allows it to change its behaviour.
  
  
  * ### GameLoop
    ![GameLoop Pattern](https://cloud.githubusercontent.com/assets/13498941/25568348/a44aab88-2df8-11e7-95f5-3206f001386a.png)
    
    Participating classes:
  
    Why we decide to use it:
    * 
  
  
  * ### Update Method
    
    Participating classes:
  
    Why we decide to use it:
    * 
  
  
  * ### Singleton
  
    Participating classes: GameModel.
    
    Why we decided to use it: 
  
    
## GUI Design and Mock-Ups

  * ### Main Menu
  ![MainMenu](http://imgur.com/tJOtAo2.png)

## Functionalities:
* Choose to Play a game.
* Choose to Play a multiplayer game.
* Choose to Customize the game (the ball's skin).

  
  * ### Levels Menu
  ![LevelsMenu](http://imgur.com/IxRENwS.png)
  
  ## Functionalities:
  (When a User chose to play a Game, this menu appears.)
*	Select from a possible playable level to play.
*	Swipe down to navigate between levels.
*	Levels completed have the fastest time they were completed in, above them.
*	Levels that are available for play, have a different color.
*	Return to the Main Menu, by clicking in the respective button.

  
  * ### Play Screen
  ![PlayScreen](http://imgur.com/QVstH0g.png)
 
 ## Functionalities:
(This Screen appears by initiating a Multiplayer game or by clicking in an available Level)
*	Play the game, with the phone’s accelerometer and the touch screen.
*	Pause the Game, where the User can choose to resume the game, restart the game, exit the play mode or change the volume.

  
  * ### Customize Menu
  ![CustomizeMenu](http://imgur.com/FdSkxPV.png)
  
  ## Functionalities:
*	By swiping left and right the User can choose which skin his ball will have.
*	Return to the Main Menu, by clicking in the respective button.

 
## List of Tests
