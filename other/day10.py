import sys
p = {0:1}
for n in map(int, sys.stdin):
    p[n] = 1
m = max(p)
p[m+3] = 1
r = [0, 0, 0, 0]
d = 1
for v in range(m + 2, -1, -1):
    if v in p:
        p[v] = p[v + 1] if (v + 1 in p) else 0
        p[v] += p[v + 2] if (v + 2 in p) else 0
        p[v] += p[v + 3] if (v + 3 in p) else 0
        r[d] += 1
        d = 1
    else:
        d += 1
print(r[1]*r[3])
print(p[0])
