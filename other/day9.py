import sys
from itertools import combinations

# part 1, O(N*P**2)
nums = []
for q in map(int, sys.stdin):
    if len(nums) >= 25 and not any(q == b + c for (b, c) in combinations(nums[-25:], 2)):
        break
    nums.append(q)

# part 2, O(N-ish)
t, i = 0, 0
for j in range(len(nums)):
    t += nums[j]
    while i <= j and t > q:
        t -= nums[i]
        i += 1
    if t == q:
        rng = nums[i:j]
        print("{} = sum({})\n{}\n".format(q, rng, min(rng) + max(rng)))