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

