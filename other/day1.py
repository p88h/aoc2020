import sys
nums = []
for line in sys.stdin:
    a = int(line)
    for b in nums:
        if (a + b == 2020):
            print "{}x{}={}".format(a,b,a*b)
        for c in nums:
            if c > b and a + b + c == 2020:
                print "{}x{}x{}={}".format(a,b,c,a*b*c)
    nums.append(a)
