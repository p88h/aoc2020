#!/usr/bin/perl
while (<>) {
  $b=$_;while (s/(\d+) (\+|\*) (\d+)/"$1 $2 $3"/ee) { s/\((\d+)\)/$1/g } $sum1+=$_;
  while ($b!~m/^\d+$/) {
    $b=~s/(\(|^)([0-9 +*]+)(\)|$)/Z/;$c=$2;
    while ($c=~s/(\d+) \+ (\d+)/$1 + $2/e){};
    while ($c=~s/(\d+) \* (\d+)/$1 * $2/e){};
    $b=~s/Z/$c/;
  } $sum2+=$b;
}
print "$sum1\n$sum2\n";
