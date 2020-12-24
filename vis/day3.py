#!/usr/bin/env python3

import tcod
import time

def main():
    screen_width = 80
    screen_height = 50
    console = tcod.Console(screen_width + 2, screen_height + 2)
    tileset = tcod.tileset.load_tilesheet("dejavu16x16_gs_tc.png", 32, 8, tcod.tileset.CHARMAP_TCOD)
    context = tcod.context.new(columns=console.width, rows=console.height, tileset=tileset)
    console.clear(bg=tcod.white,fg=tcod.black)

    # read the tiles
    mapfile = open('day3.in', 'r')
    lines = mapfile.readlines()

    # compute map width
    mapw = len(lines[0])
    if (lines[0][mapw - 1] == '\n'):
        mapw -= 1
    maph = len(lines)

    # map update function for tiles only
    def paint(dx = 0, dy = 0):
        for y in range(1, screen_height):
            for x in range(1, screen_width):
                ry = y + dy;
                rx = x + dx;
                if (ry < 0 or ry > maph): 
                    console.print(x, y, ' ')
                else:
                    console.print(x, y, lines[ry % maph][rx % mapw])

    # center position
    px = int(screen_width / 2)
    py = int(screen_height / 2)

    # map shift offset, try to keep dx positive
    dx = 0
    while (dx < px):
        dx += mapw
    dx -= px
    dy = -py

    # counter and actual position
    tot = 0
    my = 0
    mx = 0

    # we start paused
    move = False

    while True:
        paint(dx, dy)
        # draw current position
        console.print(px, py, '!')

        # draw 'history'
        for pp in range(1, py+dy):
            ch = lines[py - pp + dy][(px - pp*3 + dx) % mapw]
            if px < pp * 3:
                break
            if ch == '#':
                console.print(px - pp * 3, py - pp, '%')


        if move and my < maph:
            if lines[my][mx] == '#': 
                tot += 1
            dx += 3
            dy += 1
            mx = (mx + 3) % mapw
            my += 1

        time.sleep(0.05)

        console.print(0, 0, "%={}".format(tot))
        context.present(console)

        # check inputs
        for event in tcod.event.get():
            if event.type == "QUIT":
                return True
            if event.type == "KEYDOWN":
                if event.sym == tcod.event.K_ESCAPE:
                    return True
                if event.sym == tcod.event.K_SPACE:
                    move = not move

if __name__ == '__main__':
    main()
