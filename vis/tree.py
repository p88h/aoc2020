#!/usr/bin/env python3
import curses
from random import choice

tree=r"""
n==========================n
[  \ /   .    *  .         ]
[ >-*-<     $     .      . ]
[  / \      ! *            ]
[        * /!\I    .    .  ]
[  .  .  I/  O\            ]
[        / o   \           ]
[        ~/ & \~*          ]
[      * /     \I          ]
[      I/ 8  (  \  ,___,   ]
[      /- -@  -@-\ | | |   ]
[      , ) ~#~ ~,  |-+-|   ]
[ . , [+] .<#>.[|] |_|_| , ]
u==========================u
"""

screen = curses.initscr()
curses.start_color()
curses.init_pair(1, curses.COLOR_RED, curses.COLOR_BLACK)
curses.init_pair(2, curses.COLOR_GREEN, curses.COLOR_BLACK)
curses.init_pair(3, curses.COLOR_BLUE, curses.COLOR_BLACK)
curses.init_pair(4, curses.COLOR_YELLOW, curses.COLOR_BLACK)
curses.curs_set(0)
y = 0
for line in tree.splitlines():
    y += 1
    x = 0
    for ch in line:
        x += 1
        if ch in ['[',']','=','@','o','n','u']:
            screen.addstr(y, x, ch, curses.color_pair(3)) 
        elif ch in ['$','O','&','8']:
            screen.addstr(y, x, ch, curses.color_pair(1)) 
        elif ch == '!':
            screen.addstr(y, x, ch, curses.color_pair(2)) 
        elif y <= 5:
            if ch != ' ' and ch != '.':
                screen.addstr(y, x, ch, curses.color_pair(4)) 
            else:
                screen.addstr(y, x, ch)
        else:
            if ch == '*' or ch == '(':
                screen.addstr(y, x, ch, curses.color_pair(4))
            elif ch in ['/', '\\','-','~']:
                screen.addstr(y, x, ch, curses.color_pair(2))
            elif ch in ['#' ,'|','_',',','<','>']:
                screen.addstr(y, x, ch, curses.color_pair(1))
            else:
                screen.addstr(y, x, ch)
        # 
screen.refresh()

for i in range(100):
    y = 0
    for line in tree.splitlines():
        y += 1
        x = 0
        for ch in line:
            x += 1
            if ch == '*':
                screen.addstr(y, x, ch, choice([curses.A_BOLD, 0]) + curses.color_pair(4))
            if ch == '.' and y < 8:
                screen.addstr(y, x, choice([ch, ch, ch, ch, ch, ' ']))
    screen.refresh()
    curses.napms(200)

curses.napms(2000)
curses.endwin()
