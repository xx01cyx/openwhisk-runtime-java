i = 0
diff_sum = 0

with open('original.log') as file:
    for line in file.readlines():
        i = i + 1
        if i % 3 != 0:
            continue
        diff = int(line.split()[2])
        print("diff", diff)
        diff_sum += diff

avg = diff_sum / 100
print("average", avg)