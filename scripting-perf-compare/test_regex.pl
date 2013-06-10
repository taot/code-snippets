#! /usr/bin/env perl

use strict;

my $infilename = @ARGV[0];
my $outfilename = @ARGV[1];

#print "infilename=$infilename\n";
#print "outfilename=$outfilename\n";

open(outfile, ">$outfilename");
open(infile, "<$infilename");
#$pattern = /^(\d+)\tMember\((\d+)\)\*Name\((\d+)\)/;

while (<infile>) {
    my($line) = $_;
    chomp($line);
#    print outfile "$line\n";
#    print "Found a member\n" if $line =~ $pattern;
#    print "$line\n";
    if ($line =~ /^(\d+)\tMember\((\d+)\)\*Name\((\d+)\)/) {
        print outfile "==> $1 $2 $3\n";
    }
}

close(infile);
close(outfile);
