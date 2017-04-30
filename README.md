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
    ![MVC Pattern](https://cloud.githubusercontent.com/assets/13498941/25565767/8fd78eb0-2dc5-11e7-8a9c-a2fb769c0215.png)
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
    Implemented using LibGDX's Listeners: InputListener and ContactListener (as Listeners/Observers), Actor (as Subject).
  
    Why we decided to use it:
    * Stuff 

## GUI Design and Mock-Ups

## List of Tests
