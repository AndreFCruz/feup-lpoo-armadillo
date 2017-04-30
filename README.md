# LPOO1617_T6G5

|Table of Contents|
|:---------------:|
|[Package and class diagram (UML)](#package-and-class-diagram-uml)|
|[Design Patterns Used](#expected-design-patterns-used)|
|[GUI Design and Mock-Ups](#gui-design-and-mock-ups)|
|[List of Tests](#list-of-tests)|

## Package and class diagram (UML)

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
    ![Factory]()
    
    Participating classes: ViewFactory and GameScreen.
    
    Why we decided to use it: Creates objects without exposing the instantiation logic to the client (therefore allowing for higher abstraction and cleaner code in the GameScreen class).
  
  
  * ### Factory Method
    ![Factory Method](https://cloud.githubusercontent.com/assets/13498941/25565883/fbb6c1c6-2dc7-11e7-8301-0bdbcaa90a28.png)

    Participating classes: EntityView and all sub-classes (namely BallView, PowerUpView, ...).
    
    Why we decided to use it:
    
    
  * ### State
  
  * ### GameLoop
  
  * ### Update Method
  
  * ### Singleton
  
    Participating classes: GameModel.
    
    Why we decided to use it: 
  
    
## GUI Design and Mock-Ups

## List of Tests
