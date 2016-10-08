
rendering notes

in hexadecimal base, the codes outline the item and the direction of it 
the first digit 0-3 denotes which direction (N -> W)
the second digit 0-f is unique to the type of item

together the two digit code is used to pull the gamesprite from the hashmap
of spritecodes - values so there is no contains() or something along those
lines - using a java hashmap means that sprite retrieval operates on approx.
constant time. the sprites arent exactly masterpieces - the character sprites 
come from a base inspired by the webcomic Homestuck - but i am not much of 
an artist so i suppose that's about the best you can get with this kind of 
timeline.

//----------------------------------------------------------------//

lookup for the item codes

	NORTHFACING / DEFAULT
00 : blackhat character
01 : greenhat character
02 : purplehat character
03 : yellowhat character
04 : bookshelf-front
05 : desk - front
06 : chair - front
07 : table - front 
08 : bed - front 
09 : computer - front
0a : box 
0b : tallbox 
0c : key

	EASTFACING
14 : bookshelf - side A 
15 : desk - side
16 : chair - side A
17 : table - side
18 : bed - side A
19 : computer - side A 

	SOUTHFACING
24 : bookshelf - back
25 : desk - back
26 : chair - back 
27 : table - back
28 : bed - back
29 : computer - back

	WESTFACING	
34 : bookshelf - side B
35 : desk - side B
36 : chair - side B
37 : table - side B
38 : bed - side B
39 : computer - side B

//----------------------------------------------------------------//

below is my pseudocode/notes for the rendering algoritm as implmented 
(last updated: 8/10/16)

spriteset = map <string, image>
levels - 2d array of GameObjects with 'top' -> 'back'
otherwise they are rendered as plain tiles
(or rendered *on* the tiles?)

constants:
stage - the rotated array of GameObjects
width - get from location/stage
depth - get from location/stage
left - left bound
top - top bound
offsetX - the amount to come 'out' every row
offsetY - the amount to come 'down' every row
scale - goes along with offset
tPoint - pointer to current tile
tile - tile game object

for tile x : row
    for tile y : column
        tPoint = stage [x][y]
        renderTile(x, y)
        if tile has object
            ob = tile.getObject()
            renderObject(x, y, ob)

---

renderTile(int x, int y, GameObject ob)

    //actually the bottom left.
    rowLeft = left + (offsetX * depth-y)
    rowTop = top + (offsetY * depth-y)

    
---

