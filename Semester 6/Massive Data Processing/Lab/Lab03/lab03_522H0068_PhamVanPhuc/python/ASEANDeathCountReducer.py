#!/usr/bin/env python3
import sys

current_region = None
total_deaths = 0

for line in sys.stdin:
    line = line.strip()
    region, deaths_str = line.split("\t", 1)

    try:
        deaths = int(deaths_str)
    except ValueError:
        continue

    if current_region == region:
        total_deaths += deaths
    else:
        if current_region:
            print(f"{current_region}\t{total_deaths}")
        current_region = region
        total_deaths = deaths

# In kết quả cuối cùng
if current_region:
    print(f"{current_region}\t{total_deaths}")
