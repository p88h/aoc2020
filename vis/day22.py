#!/usr/bin/env python3

from collections import deque
from itertools import islice
import curses
import locale


def display(deck, direction, level, clear=False):
    maxw = 20
    for i in range(len(deck)+1):
        y = level * 6 + int(i/maxw)*2 + 2
        x = 60 + direction * (int(i % maxw) * 4 + 4)
        if i < len(deck) and not clear:
            win.addstr(y+1, x, "┏──┓")
            win.addstr(y+2, x, "│{:02d}│".format(deck[i]))
            win.addstr(y+3, x, "┗──┛")
        else:
            if clear or i < maxw:
                win.addstr(y+1, x, "    ")
            win.addstr(y+2, x, "    ")
            win.addstr(y+3, x, "    ")
        win.refresh()
        curses.napms(10)


def game(d1, d2, r, l):
    history = set()
    while d1 and d2:
        if (tuple(d1), tuple(d2)) in history:
            display(d1, -1, l, l > 0)
            display(d2, 1, l, l > 0)
            return True
        history.add((tuple(d1), tuple(d2)))
        display(d1, -1, l)
        display(d2, 1, l)
        curses.napms(100)
        c1, c2 = d1.popleft(), d2.popleft()
        if r and len(p1) >= c1 and len(p2) >= c2:
            win = game(deque(islice(d1, c1)), deque(islice(d2, c2)), r, l+1)
        else:
            win = c1 > c2
        if win:
            d1.extend([c1, c2])
        else:
            d2.extend([c2, c1])
    display(d1, -1, l, l > 0)
    display(d2, 1, l, l > 0)
    return len(d1) > 0



p1, p2 = [[int(line) for line in block.splitlines()[1:]]
          for block in open("day22.in").read().split('\n\n')]

locale.setlocale(locale.LC_ALL, 'en_US.UTF-8')
win = curses.initscr()
curses.curs_set(0)
win.addstr(1, 54, r'*<|:-)')

win.addstr(1, 64, r'(\/) (°,,,,°) (\/)')
game(deque(p1), deque(p2), True, 0)

curses.napms(1000)
curses.endwin()
