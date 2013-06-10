#! /usr/bin/env ruby

infilename = ARGV[0]
outfilename = ARGV[1]
r = /^(\d+)\tMember\((\d+)\)\*Name\((\d+)\)/

File.open(infilename, "r") { |infile|
  File.open(outfilename, "w") { |outfile|
    while (line = infile.gets)
      groups = r.match(line)
      fail "Failed to match line" if groups == nil
      outfile.puts "==> " + groups[1] + " " + groups[2] + " " + groups[3]
    end
  }
}
