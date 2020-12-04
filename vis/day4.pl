#!/usr/bin/perl
use Term::ANSIScreen qw/:color :cursor :screen :keyboard/;
use Time::HiRes qw(usleep);

setmode(1);
cls;

sub display {
  my ($p, $status, $count) = @_;
  locate(2,1);savepos;
  format STDOUT =
  /----------------------------------------\
  |                   |                    |
  |     ARSTOTZKA     |     @<<<<<<<<<     |
                            $p->{pid}
  |                   |                    |
  |                   |  Y.O.B. : @<<<<    |
                              $p->{byr}
  |                   |  ISSUED : @<<<<    |
                              $p->{iyr}
  |                   |   VALID : @<<<<    |
                              $p->{eyr}
  |                   |                    |
  |                   |  HEIGHT : @<<<<<<  |
                              $p->{hgt}
  |                   |    HAIR : @<<<<<<< |
                              $p->{hcl}
  |                   |    EYES : @<<<<<<< |
                              $p->{ecl}
  |                   |                    |
  |   [ @|||||||| ]   |                    |
        $p->{cid} or "???"
  \----------------------------------------/

  VALID: @<<<<<<
         $count
.
  write;
  print "\n               ";
  if ($status eq "APPROVED") {
     $color = "green";
  } else { 
     $color = "red";
  }
  for $l (0..length($status)) {
    locate(8,10);
    color $color;
    print substr($status,0,$l),"\n";
    locate(18,1);
    print "\n";
    usleep(33000);
  }
  color "white";
}

$/="\n\n";
while (<>) {
  my $p, $s, $c;
  if (m/((byr:(19[2-9][0-9]|200[0-2])|
          iyr:20(1[0-9]|20)|
          eyr:20(2[0-9]|30)|
          hgt:(1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in)|
          hcl:\#[0-9a-f]{6}|
          ecl:(amb|blu|brn|gry|grn|hzl|oth)|
          pid:[0-9]{9})\b.*){7}/sx) {
    $s = "APPROVED";
    $c++;
  } else {
    $s = " DENIED ";
  }
  while (s/(byr|iyr|eyr|hgt|hcl|ecl|pid|cid):(#?.+?)\b//s) {
    $p->{$1}=$2;
  }
  display($p, $s, $c);
  usleep(100000);
}
