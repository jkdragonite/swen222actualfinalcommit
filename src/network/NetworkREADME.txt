SENDING DATA: Master Side (Server)
The master's main function is to ensure that the client's changes are pushed to the
main game and that the changes from all clients are reflected in each individual
client game. Thus the Master needs to be able to send updated BitSets as byte 
arrays (a la DJP's Pacman) which mean the game (or board) needs to be able to 
convert to and from/update given an array.


SENDING DATA: Servant Side (Client)
When the Servant sends intercepted player data (i.e. key presses and mouse clicks) to 
it's Master, it firstly sends a character which identifies what information follows, so
that the data is processed correctly. 
As of writing, the character 'k' is for a KEY PRESS event and is followed by a number 
(1-4) which denotes the direction the player wants to move. 
The character 'm' is for a MOUSE CLICK event and is followed by two integers
representing the x and y co-ordinates of the mouse click.  