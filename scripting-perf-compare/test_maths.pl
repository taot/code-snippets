use strict;

my $sum = 0.0;

for my $i (0 .. 100000000) {
#  print "$i\n";
  $sum += sqrt $i
}

print "sum = $sum\n"
