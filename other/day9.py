import sys
from itertools import combinations

nums = []
runs = []

# part 1, O(N*P**2)
q = 0
for line in sys.stdin:
    q = int(line)
    if len(nums) >= 25:
        if not any(q == b + c for (b, c) in combinations(nums[-25:], 2)):
            print(q)
            break
    nums.append(q)

# part 2, O(N-ish)
t = 0
i = 0
for j in range(len(nums)):
    t += nums[j]
    while i <= j and t > q:
        t -= nums[i]
        i += 1
    if t == q:
        rng = nums[i:j]
        print("sum({}) = {}\n{}\n".format(rng, q, min(rng)+max(rng)))
