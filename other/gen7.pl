#!/usr/bin/perl

my @pfx = qw(bright clear dark dim dotted drab dull faded light mirrored 
             muted pale plaid posh shiny striped vibrant wavy);
my @col = qw(aqua beige black blue bronze brown chartreuse coral crimson cyan fuchsia 
             gold gray green indigo lavender lime magenta maroon olive orange plum purple 
             red salmon silver tan teal tomato turquoise violet white yellow);

for (map { $c=$_; map { "$_ $c" } @pfx; } @col) {
    if ($prev1 and $prev2) {
        print "$_ bags contain 1 $prev2 bag, 1 $prev1 bag.\n";
    } elsif ($prev1) {
        print "$_ bags contain 1 $prev1 bag.\n";
    } else {
        print "$_ bags contain no other bags.\n";
    }
    $prev2 = $prev1;
    $prev1 = $_;
}
