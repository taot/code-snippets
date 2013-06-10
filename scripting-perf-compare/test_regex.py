#! /usr/bin/env python2

import re
import sys

infilename = sys.argv[1]
outfilename = sys.argv[2]

infile = open(infilename, 'r')
outfile = open(outfilename, 'w')
p = re.compile('^(\d+)\tMember\((\d+)\)\*Name\((\d+)\)')

for line in infile:
    line = line.rstrip("\r\n")
    m = p.match(line)
    outfile.write("==> " + m.group(1) + " " + m.group(2) + " " + m.group(3) + "\n")

infile.close()
outfile.close()
