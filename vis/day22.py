#!/usr/bin/env python3

from collections import deque
from itertools import islice
import curses
import locale

maxw = 20
total = 0
rate = 1
threshold = 100


def paint(level, ofs, direction, values=[]):
    global maxw
    y = level * 7 + int(ofs/maxw)*2 + 2
    x = 88 + direction * (int(ofs % maxw) * 4 + 8)
    if (ofs < len(values)):
        win.addstr(y+1, x, "┏──┓")
        win.addstr(y+2, x, "│{:02d}│".format(values[ofs]))
        win.addstr(y+3, x, "┗──┛")
    else:
        if (ofs < maxw): win.addstr(y+1, x, "    ")
        win.addstr(y+2, x, "    ")
        win.addstr(y+3, x, "    ")


def clear(level, lleft, lright):
    win.addstr(level * 6 + 4, 87, "      ")
    for i in range(lleft + 1): paint(level, i, -1)
    for i in range(lright + 1): paint(level, i, 1)
    win.refresh()


def display(level, left, lleft, right, lright, turn):
    global total
    global rate
    win.addstr(level * 6 + 4, 87, "{:06d}".format(turn))
    win.addstr(1, 86, "{:08d}".format(total))
    win.addstr(2, 86, "speed x{}".format(rate))
    for i in range(max(len(left),lleft)): paint(level, i, -1, left)
    for i in range(max(len(right),lright)): paint(level, i, 1, right)
    win.refresh()


def game(d1, d2, r, l):
    global total, rate, threshold
    history = set()
    myrate = rate
    pl, pr = 0, 0 
    while d1 and d2:
        if (tuple(d1), tuple(d2)) in history:
            clear(l, pl, pr)
            return True
        turn = len(history)
        if turn % myrate == 0:
            display(l, d1, pl, d2, pr, turn)
            pl, pr = len(d1), len(d2)
        total = total + 1
        if turn == threshold:
            threshold = threshold * 2
            rate = rate * 2
        history.add((tuple(d1), tuple(d2)))
        c1, c2 = d1.popleft(), d2.popleft()
        if r and len(d1) >= c1 and len(d2) >= c2:
            win = game(deque(islice(d1, c1)), deque(islice(d2, c2)), r, l+1)
        else:
            win = c1 > c2
        if win:
            d1.extend([c1, c2])
        else:
            d2.extend([c2, c1])
    clear(l, pl, pr)
    return len(d1) > 0


p1, p2 = [[int(line) for line in block.splitlines()[1:]]
          for block in open("day22.in").read().split('\n\n')]

locale.setlocale(locale.LC_ALL, 'en_US.UTF-8')
win = curses.initscr()
curses.curs_set(0)
win.addstr(1, 78, r'*<|:-)')

win.addstr(1, 96, r'(\/) (°,,,,°) (\/)')
game(deque(p1), deque(p2), True, 0)

curses.napms(1000)
curses.endwin()
