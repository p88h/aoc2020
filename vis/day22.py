#!/usr/bin/env python3

from collections import deque
from itertools import islice
import curses
import locale

maxw = 18
total = 0
rate = 1
threshold = 100

def paint(win, ofs, direction, values=[]):
    global maxw
    y = int(ofs/maxw)*2 - 1 
    x = 88 + direction * (int(ofs % maxw) * 4 + 8)
    if ofs < len(values):
        win.addstr(y+1, x, "┏──┓")
        win.addstr(y+2, x, "│{:02d}│".format(values[ofs]))
        win.addstr(y+3, x, "┗──┛")
    else:
        if ofs < maxw: win.addstr(y+1, x, "    ")
        win.addstr(y+2, x, "    ")
        win.addstr(y+3, x, "    ")


def display(win, left, lleft, right, lright, turn):
    header.addstr(1, 86, "{:08d}".format(total))
    header.addstr(2, 86, "speed x{}".format(rate))
    header.refresh()
    win.addstr(1, 87, "{:06d}".format(turn))
    for i in range(max(len(left), lleft)):
        paint(win, i, -1, left)
    for i in range(max(len(right), lright)):
        paint(win, i, 1, right)
    win.refresh()


def game(d1, d2, r, l):
    global total, rate, threshold
    history = set()
    myrate = rate
    pl, pr = 0, 0
    
    win = curses.newwin(7, 172, 3 + l * 7, 0)

    while d1 and d2:
        if (tuple(d1), tuple(d2)) in history:
            win.clear()
            win.refresh()
            return True
        turn = len(history)
        if turn % myrate == 0:
            display(win, d1, pl, d2, pr, turn)
            if l < 2: curses.napms(int (10 / (l + 1)))
            pl, pr = len(d1), len(d2)
        total = total + 1
        if turn == threshold:
            threshold = threshold * 2
            rate = rate * 2
        history.add((tuple(d1), tuple(d2)))
        c1, c2 = d1.popleft(), d2.popleft()
        if r and len(d1) >= c1 and len(d2) >= c2:
            ret = game(deque(islice(d1, c1)), deque(islice(d2, c2)), r, l+1)
        else:
            ret = c1 > c2
        if ret:
            d1.extend([c1, c2])
        else:
            d2.extend([c2, c1])
    if l > 0:
        win.clear()
        win.refresh()
    else:
        display(win, d1, pl, d2, pr, turn)
    return len(d1) > 0


p1, p2 = [[int(line) for line in block.splitlines()[1:]]
          for block in open("day22.in").read().split('\n\n')]

locale.setlocale(locale.LC_ALL, 'en_US.UTF-8')
screen = curses.initscr()
curses.curs_set(0)
header = curses.newwin(3, 172, 0, 0)

header.addstr(1, 78, r'*<|:-)')
header.addstr(1, 96, r'(\/) (°,,,,°) (\/)')
header.refresh()
game(deque(p1), deque(p2), True, 0)

curses.napms(2000)
curses.endwin()
