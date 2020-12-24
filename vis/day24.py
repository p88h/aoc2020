#!/usr/bin/env python3

import tcod
import time

screen_width = 200
screen_height = 120
console = tcod.Console(screen_width + 2, screen_height + 2)
tileset = tcod.tileset.load_tilesheet("dejavu8x8_gs_tc.png", 32, 8, tcod.tileset.CHARMAP_TCOD)
context = tcod.context.new(columns=console.width, rows=console.height, tileset=tileset)
console.clear(bg=tcod.white,fg=tcod.black)

black = set()
count = 0

for line in open("day24.in").read().split('\n'):
    x = 100
    y = 60
    pos = 0
    count += 1
    if not line:
        continue
    while pos < len(line):
        if line[pos] == 'n' and line[pos+1] == 'w':
            x -= 1; y -= 1; pos += 2
        elif line[pos] == 'n' and line[pos+1] == 'e':
            x += 1; y -= 1; pos += 2
        elif line[pos] == 's' and line[pos+1] == 'w':
            x -= 1; y += 1; pos += 2
        elif line[pos] == 's' and line[pos+1] == 'e':
            x += 1; y += 1; pos += 2
        elif line[pos] == 'e':
            x += 2; pos += 1
        elif line[pos] == 'w':
            x -= 2; pos += 1
    pos = (x, y)
    if pos in black:
        black.remove(pos)
        console.print(x, y, ".")
    else:
        black.add(pos)
        console.print(x, y, "#")
    console.print(0, 0, "Line: {:04d} Black: {:04d}".format(count, len(black)))
    context.present(console)
    time.sleep(0.05)
    # check inputs
    for event in tcod.event.get():
        if event.type == "QUIT":
            exit(0)

for d in range(100):
    adj = {}
    for (x, y) in black:
        if (x, y) not in adj:
            adj[(x,y)] = 0
        for (dx, dy) in [(-1, -1), (1, -1), (-2, 0), (2, 0), (-1, 1), (1, 1)]:
            adj[(x+dx, y+dy)] = adj[(x+dx, y+dy)] + 1 if (x+dx, y+dy) in adj else 1
    for (x, y) in adj:
        if (x, y) in black and (adj[(x, y)] > 2 or  adj[(x, y)] == 0):
            black.remove((x, y))
            console.print(x, y, ".")
        elif adj[(x, y)] == 2:
            black.add((x, y))
            console.print(x, y, "#")

    console.print(0, 0, " Day: {:04d} Black: {:04d}".format(d + 1, len(black)))
    context.present(console)
    time.sleep(0.1)
    # check inputs
    for event in tcod.event.get():
        if event.type == "QUIT":
            exit(0)

time.sleep(3)
