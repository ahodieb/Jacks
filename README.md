#Jacks

A java network based multiplayer game, presented as a college project.
The game is an implementation of a famous card game in Egypt and i think its popular in other places as well.


##Documentation:

* At the beginning of the development i planned to separate the game logic, network logic , and gui from each other, to be able 
to extend the game and implement another card games using the same platform . 
But as the project's deadline approached all what mattered was to have something working. 

###Network Module:
* The network module i built seemed to me like a great idea at the time ,it was threaded with had dual channels for simultaneous sending and receiving. i even used its concept in a more advance Python Project and refined it. ['Take a look if u want'][1]
  Added json data headers instead of using 4 Characters Headers and more.
* The Client Network Module had 2 parts (Threads) each responsible for either input or output.
* The input channel had a callback which they send to the received packets.
* This callback concept is used in the whole project . its the way that each part communicates with each other while being separated in operations.
* The sending and receiving must have a lot of bugs but if not stressed they work fine , and i didn't have enough time to test or optimize the performance.
* The Server, Node Connection  are the place where the data started to be processed thus having a lot of the related game data with them.

###Game Logic:
* I implemented something called processors, they are classes that receive headers from the network module and react to them . 
* Some implemented functions to generate random cards and divide them randomly and sort them into 4 sets for the players.
* Made a simple implementation of quicksort . it was made according to the first google result about sorting . just liked it so i implemented it in java. it might be good , bad , useless in this place but it was fun.
* Also has some game logic needed like checking if a player has any cards of the same suit ,or if he won the hand , or if he needs to restart the round 'Dak el dor'.

###GUI:
* The Card is an implementation of Jbutton.
* The Four Images of the suits are cached so every card of the same suit uses the same reference to the same image , i thought it might be very bad in performance loading 15 ( 13 + 3) images every redraw .
* The Side panel has 2 parts , a players name list which indicates whose turn it is . and a simple side chat.
* The Game frame  is where it got a little messy . it was supposed to be purely GUI but a lot of the games logic is put inside of it.
* The Table Layout was a failure , i  wanted to create a custom layout which was similar to the layout in real life . but it was the last thing to be implemented and due to the deadline it was ignored.

###How To Run / Use the code of the game:
####RUN:
* Run the server first adding the -Server flag :  java -jar Jacks.jar -Server
* Run 4 clients or a client at each pc if available : java -jar Jacks.jar
  you will be prompted to input ur name , and server address.
  its advisable due to weird bugs to connect them one by one and not all at once.

####Develope
* Import the game in ur eclipse workspace .
* There is 2 main files . the Server main is just used for fast testing when running in eclipse , it runs the server.



* The game has tons of bugs that i planned to work on but later lost interest because of my involvement in other things.
* Most probably i will not be working on this game any more , but if someone wants to extend it , contribute to it or needs help using it i will be very glad to help. I might even consider working on it again if someone needs more help.
* The Jacks implementation is not 100 % done because the game has a special mode ('Jacks Mode') that has a different logic than the other 4 modes so it was left for later.
  
  
##Software Used:

* [Eclipse IDE][2]
* [Ubuntu][3] ( although its supposed to be platform independent ).


##Online Showcase:

[Youtube Video][4]
  [1]: https://github.com/TheNightPhoenix/PySimpleRPC/tree/master/ThreadedNetwork
  [2]: http://eclipse.org/
  [3]: http://www.ubuntu.com/
  [4]: http://www.youtube.com/watch?v=M_WAp8oaS7E

