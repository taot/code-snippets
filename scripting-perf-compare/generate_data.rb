#! /usr/bin/env ruby

count = ARGV[0].to_i

(0 .. count - 1).each { |i|
  puts "#{i}\tMember(#{i})*Name(#{i*2})"
}
