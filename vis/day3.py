#!/usr/bin/env python3

import tcod as libtcod
import time

def main():
    screen_width = 80
    screen_height = 50
    libtcod.console_set_custom_font('dejavu16x16_gs_tc.png', libtcod.FONT_TYPE_GREYSCALE | libtcod.FONT_LAYOUT_TCOD)
    libtcod.console_init_root(screen_width + 2, screen_height + 2, 'tobogan fun', False)
    libtcod.console_set_default_foreground(0, libtcod.white)

    # read the tiles
    mapfile = open('map.file', 'r')
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
                    libtcod.console_put_char(0, x, y, ' ')
                else:
                    libtcod.console_put_char(0, x, y, lines[ry % maph][rx % mapw])

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

    while not libtcod.console_is_window_closed():
        paint(dx, dy, lines)
        # draw current position
        libtcod.console_put_char(0, px, py, '@', libtcod.BKGND_NONE)

        # draw 'history'
        for pp in range(1, py+dy):
            ch = lines[py - pp + dy][(px - pp*3 + dx) % mapw]
            if ch == '#':
                libtcod.console_put_char(0, px - pp * 3, py - pp, 'X', libtcod.BKGND_NONE)
            else:
                libtcod.console_put_char(0, px - pp * 3, py - pp, 'O', libtcod.BKGND_NONE)


        if move and my < maph:
            if lines[my][mx] == '#': 
                tot += 1
            dx += 3
            dy += 1
            mx = (mx + 3) % mapw
            my += 1
        else:
            time.sleep(0.1)

        # print score
        libtcod.console_put_char(0, 0, 0, str(int(tot / 1000) % 10), libtcod.BKGND_NONE)
        libtcod.console_put_char(0, 1, 0, str(int(tot / 100) % 10), libtcod.BKGND_NONE)
        libtcod.console_put_char(0, 2, 0, str(int(tot / 10) % 10), libtcod.BKGND_NONE)
        libtcod.console_put_char(0, 3, 0, str(tot % 10), libtcod.BKGND_NONE)

        libtcod.console_flush()

        # check inputs
        key = libtcod.console_check_for_keypress()
        if key.vk == libtcod.KEY_SPACE:
            move = not move
        if key.vk == libtcod.KEY_ESCAPE:
            return True

if __name__ == '__main__':
    main()
