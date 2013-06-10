sum = 0.0
for i in (1 .. 100000000) do
  sum += Math.sqrt(i)
end
puts "sum = " + sum.to_s
