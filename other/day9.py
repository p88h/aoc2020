import sys
nums = []
runs = []
preamble = 25
invalid = 0
# part 1
for line in sys.stdin:
    a = int(line)
    l = len(nums)
    if invalid == 0 and l >= preamble:
        found = False
        for b in range(preamble):
            for c in range(b):
                if a == nums[l - b - 1] + nums[l - c -1]:
                    found = True
        if not found:
            invalid = a
            print(a)
            break
    nums.append(a)
# part 2
for a in range(len(nums)):
    for r in range(len(runs)):
        runs[r] += nums[a]
        if runs[r] == invalid:
            rng = nums[r:a]
            print("sum({}) = {}\n{}\n".format(rng,invalid,min(rng)+max(rng)))
    runs.append(nums[a])
