# LPOO1617_T6G5

[![BCH compliance](https://bettercodehub.com/edge/badge/AndreFCruz/LPOO1617_T6G5?branch=master&token=f4cdc1d758665692d8603b9b6a9dfc271a8927b3)](https://bettercodehub.com/)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)

|Table of Contents|
|:---------------:|
|[Setup/Installation Procedure](#setupinstallation-procedure)|
|[Video Tutorial](#video-tutorial)|
|[Package and class diagram (UML)](#package-and-class-diagram-uml)|
|[Design Patterns Used](#design-patterns-used)|
|[Tests Coverage](#tests-coverage)|
|[Other Considerations](#other-considerations)|

## Setup/Installation Procedure
   * ### To install the android app through the Google Play Store
     1. Sign up for the alpha release on the play store [here](https://play.google.com/apps/testing/com.lpoo.game).
     2. Download and install the app on your android smartphone.
     
   * ### To install the android app from the .apk file (for the latest version install from the play store)
     1. Download the apk [here](https://drive.google.com/open?id=0B-xX6gDQNtKSZkQ3VG5WQ2liM1E).
     2. Transfer the file to your android smartphone and install it.
     
   * ### To install the desktop app (for Windows, Linux and MacOS)
     1. Download the jar [here](https://drive.google.com/open?id=0B-xX6gDQNtKSZ2hCMWw1NW9fS1E).
     2. Run the file from the terminal or by double-clicking it.
   
   * ### To install the development environment
     1. Simply clone this repository and open it with Android Studio.
     2. To assemble a release apk you will need to change the signing settings to your own, or assemble an unsigned apk.

## Video Tutorial
[![Video Tutorial](http://img.youtube.com/vi/IB0OUY351hI/0.jpg)](http://www.youtube.com/watch?v=IB0OUY351hI "Armadillo - Video Tutorial")

## Package and class diagram (UML)
![class diagram](https://user-images.githubusercontent.com/13498941/27004518-2ebf092a-4e02-11e7-99b1-084c11896527.png)

[Original](https://cloud.githubusercontent.com/assets/13498941/25568250/97bd0156-2df6-11e7-89f4-447b37c0c771.png)  package and class diagram for reference. This was drafted before we began development.

## Design Patterns Used

  * ### Model–view–controller (MVC) - architectural pattern
    <img height="300" alt="MVC Pattern" src="https://cloud.githubusercontent.com/assets/13498941/25565780/c646a81e-2dc5-11e7-9bbd-5a8330b1cbbf.png">
        
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
    ![Factory Pattern in our Project](https://user-images.githubusercontent.com/13498941/27004151-54c2147e-4dfc-11e7-9e40-868e3249bd3b.png)
    
    Participating classes: ViewFactory and B2DFactory.
    
    Why we decided to use it: 
    * Creates objects without exposing the instantiation logic to the client (therefore allowing for higher abstraction and cleaner code).
  
  
  * ### Factory Method
    ![Factory Method](https://cloud.githubusercontent.com/assets/13498941/25565883/fbb6c1c6-2dc7-11e7-8301-0bdbcaa90a28.png)
    
    Participating classes: EntityView and all sub-classes (BallView, PowerUpView, ...).
    
    Why we decided to use it:
    * Allows the subclasses to decide which class to instantiate (useful for when a class knows it should create an object, but not it's type).
    
  * ### Null Object
    Participating classes: NullGameServices.
  
    Why we decided to use it: Prevents constant null checks which would result in no actions being executed.
  
  * ### Update Method
    <img width="450" alt="Update Method" src="https://cloud.githubusercontent.com/assets/13498941/25568393/cdc324f8-2df9-11e7-9e6c-1d0823576018.png">
         
    Participating classes: EntityView and ShapeView.
  
    Why we decided to use it: facilitates updating all view entities in the game. 
    
   * ### GameLoop
     <img width="450" alt="GameLoop Pattern" src="https://cloud.githubusercontent.com/assets/13498941/25568348/a44aab88-2df8-11e7-95f5-3206f001386a.png">

    
     Participating classes: GameController (handles input), GameModel (updates game) and GameScreen (renders).
  
     Why we decided to use it: decouples the progression of game time from user input and processor speed.

 
## Tests Coverage
* 100% class coverage
* 100% method coverage
* 98% line coverage

![code_coverage](https://user-images.githubusercontent.com/13498941/27004102-6e3e222c-4dfb-11e7-89b9-1e63630e8d5a.PNG)

## Other Considerations
* ### Lessons learned - Singleton... and why we decided not to use it
  We had initially planned for the GameModel class to implement the Singleton pattern, but eventually it became more troublesome than the problems it solved. It increased code coupling and made automated testing unnecessarily difficult.

* ### Overall time spent developing:
  We estimate a total of **300 hours** (evenly distributed between the two developers).

* ### Difficulties along the way
  * Frustratingly trying to integrate google play services (and getting it to work on a release build);
  * Releasing an alpha on the play store, while adhearing to Google Play App Signing.
  
  (These are fairly simple once you've done it a couple times, but for first-timers like us it required understanding the inner works of Android Studio)
  
* ### Notes
  If you see any references to 'Spheral', it was the original name of this project. We decided to rename it mid-development to Armadillo to better reflect the art style.
  
* ### Developers:
  Project developed for 'Object Oriented Programming Laboratory', a second year subject @FEUP.
  
  Authors: Andre Cruz and Edgar Carneiro.

* ### Game Art: ###
  A big thanks to **Mariana Silva** for providing most of the art we use in the game.

