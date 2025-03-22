#!/usr/bin/env python3
import sys

for line in sys.stdin:
    line = line.strip()
    fields = line.split("\t")
    
    if len(fields) >= 4:
        country = fields[0]
        region = fields[1]
        deaths_str = fields[7]  

        if region.strip().lower() == "south-east asia":
            try:
                deaths = int(float(deaths_str.replace(",", "")))
                print(f"{region}\t{deaths}")
            except ValueError:
                print(f"Error parsing deaths: {deaths_str}", file=sys.stderr)
