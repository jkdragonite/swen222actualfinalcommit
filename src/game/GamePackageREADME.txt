- filling in some game objects i need for renderer

Physical/static objects represented in game file represented by text:
Wall = W
Locked Door = D
Unlocked door = d
Object = O
OR
Immovable objects(table/bed/chair?): I
Movable object(that isn't a container-are we going to allow containers to move?): M
Searchable container(like a desk/chest?): S

Players shown by a number (1-4)

e.g.
WWWWWWWWWW
WOOWOWWWOW
WWOWW1WWOW
WWWWWWWWOW
WWWWWWWWWD
WOOWWWWWWW
WOOWWWOOOW
WWWWWWOOOW
WWW2WOOOOW
WWWWDWWWWW

When the data is processed it's put into a 2D array, which can then be compared with the current 2D array. Makes checking for which
object is where/if the player has clicked on an object/whether the player can move to a position a little more manageable? 