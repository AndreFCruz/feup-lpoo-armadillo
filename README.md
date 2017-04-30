# LPOO1617_T6G5

|Table of Contents|
|:---------------:|
|[Package and class diagram (UML)]()|
|[Design Patterns Used](#expected-design-patterns-used)|
|GUI Design and Mock-Ups |
|List of Tests |


## (Expected) Design Patterns Used

  * ### Model–view–controller (MVC) - architectural pattern
    ![MVC Pattern](https://cloud.githubusercontent.com/assets/13498941/25564480/f6cd9b1a-2dab-11e7-8815-7a04fd35cfd7.png)
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
