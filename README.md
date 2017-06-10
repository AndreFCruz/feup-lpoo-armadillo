# LPOO1617_T6G5

[![BCH compliance](https://bettercodehub.com/edge/badge/AndreFCruz/LPOO1617_T6G5?branch=master&token=f4cdc1d758665692d8603b9b6a9dfc271a8927b3)](https://bettercodehub.com/)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)

|Table of Contents|
|:---------------:|
|[Setup/Installation Procedure](#setupinstallation-procedure)|
|[Package and class diagram (UML)](#package-and-class-diagram-uml)|
|[Design Patterns Used](#design-patterns-used)|
|[GUI Design and Mock-Ups](#gui-design-and-mock-ups)|
|[Tests Coverage](#tests-coverage)|
|[Relevant design decisions and lessons learned](#relevant-design-decisions-and-lessons-learned)|

## Setup/Installation Procedure
   * ### To install the android app
     1. Sign up for the alpha release on the Google Play store [here](https://play.google.com/apps/testing/com.lpoo.game).
     2. Download and install the app on your android smartphone.
   
   * ### To install the development environment
     1. Simply clone this repository and open it with Android Studio.

## Package and class diagram (UML)


[Original](https://cloud.githubusercontent.com/assets/13498941/25568250/97bd0156-2df6-11e7-89f4-447b37c0c771.png)  package and class diagram for reference. This was drafted before we began development.

## Design Patterns Used

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
    EntityModel's state is controlled by a state machine and its restrict transactions.
    Participating classes: BallModel.
  
    Why we decide to use it:
    * Useful for making the code more logic and understandable.
    * When the internal state of an object changes, it allows it to change its behaviour.
  
  * ### Null Object
  ** UML image ? **
    Participating classes: NullGameServices.
  
    Why we decided to use it:
    * Prevents constant null checks.
    * The result of the null check would be to do nothing.
  
  * ### Update Method
    ![Update Method](https://cloud.githubusercontent.com/assets/13498941/25568393/cdc324f8-2df9-11e7-9e6c-1d0823576018.png)
    Participating classes: EntityModel and all its sub-classes (BallModel, PowerUp, etc.), ShapeModel and all its subclasses, as well as the BuoyancyController class.
  
    Why we decided to use it:
    * facilitates updating all entities in the game. 
    * renders smooth animation of entities (independent of frame-rate).
    
   * ### GameLoop
    ![GameLoop Pattern](https://cloud.githubusercontent.com/assets/13498941/25568348/a44aab88-2df8-11e7-95f5-3206f001386a.png)
    Implemented using LibGDX's Game template method pattern.
    Participating classes: Armadillo, GameScreen and GameModel.
  
    Why we decided to use it: decouples the progression of game time from user input and processor speed.

 
## Tests Coverage
** TODO PRINTSCREEN NEEDED **

## Relevant design decisions and lessons learned
#### Singleton... and why we decided not to use it
We had initially planned for the GameModel class to implement the Singleton pattern. Eventually it became more troublesome than the problems it solved, so we decided to refactor it out.
1. It increases code coupling and makes automated testing more difficult.
2. 


## Original GUI Design and Mock-Ups

  * ### Main Menu
    ![MainMenu](http://imgur.com/tJOtAo2.png)

    **Functionalities:**
    * Choose to Play a game.
    * Choose to Play a multiplayer game.
    * Choose to Customize the game (the ball's skin).

  
  * ### Levels Menu
    ![LevelsMenu](http://imgur.com/IxRENwS.png)
  
    **Functionalities:**
    (Appears on App Launch)
    *	Select from a possible playable level to play.
    *	Swipe down to navigate between levels.
    *	Levels completed have the fastest time they were completed in, above them.
    *	Levels that are available for play, have a different color.
    *	Return to the Main Menu, by clicking in the respective button.

  
  * ### Play Screen
    ![PlayScreen](http://imgur.com/QVstH0g.png)
 
    **Functionalities:**
    (This Screen appears by initiating a Multiplayer game or by clicking in an available Level)
    *	Play the game, with the phone’s accelerometer and the touch screen.
    *	Pause the Game, where the User can choose to resume the game, restart the game, exit the play mode or change the volume.

  
  * ### Customize Menu
    ![CustomizeMenu](http://imgur.com/FdSkxPV.png)
  
    **Functionalities:**
    *	By swiping left and right the User can choose which skin his ball will have.
    *	Return to the Main Menu, by clicking in the respective button.
