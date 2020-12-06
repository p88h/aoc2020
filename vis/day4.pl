#!/usr/bin/perl
use Term::ANSIScreen qw/:color :cursor :screen :keyboard/;
use Time::HiRes qw(usleep);

setmode(1);
cls;

@hair = ('/-----\\', '.^^^^^.', '.!!!!!.', '///-\\\\\\', '#######', '/~~~~~\\');
@brow = ('| _ _ |', '|^- -^|', '| ~ ~ |', '| = = |', '| - - |');
@eyes = ('|     |', '|[] []|', '| o o |', '| O O |', '| @ @ |', '| x x |');
@nose = ('|  ^  |', '|  V  |', '|  &  |', '|  *  |', '|  v  |');
@lips = (' \ o / ', ' \ O / ', ' \ - / ', ' \ ~ / ', ' \[-]/ ');
@neck = (' | . | ', '  | |  ', ' / $ \ ', ' /^w^\  ',' {-+-} ');

$ecm{amb} = 'cyan';
$ecm{blu} = 'blue';
$ecm{brn} = 'red';
$ecm{gry} = 'white';
$ecm{grn} = 'green';
$ecm{hzl} = 'yellow';
$ecm{oth} = 'magenta';

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
  |                   |    [ @|||||||| ]   |
                             $p->{cid} or "???"
  \----------------------------------------/

  VALID: @<<<<<<
         $count
.
  write;
  print "\n               ";

  if ($p->{byr}) {
    $age = 2020-int($p->{byr});
    if ($age < 1) {
      $h = 0;
    } elsif ($age < 10) {
      $h = 1;
    } elsif ($age < 25) {
      $h = 2;
    } elsif ($age < 80) {
      $h = 3;
    } else {
      $h = 5;
    }
  } else {
    $h = 4;
  }

  if ($p->{ecl}) {
    if ($age > 100) {
      $e = 5;
    } else {
      $e = 2+int(rand(3));
    }
    $ec = $ecm{$p->{ecl}};
  } else {
    $e = 0;
    $ec = 'clear';
  }
  if ($p->{hcl} =~ /([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})/) {
    $r = ord(pack("H*",$1));
    $g = ord(pack("H*",$2));
    $b = ord(pack("H*",$3));
    $hc = "\x1b[38;2;"."$r;$g;$b"."m".$hair[$h]."\x1b[0m\n";
  } else {
    $hc = $hair[$h];
  }
  print locate(6,10),$hc;
  color "reset";
  print locate(7,10),$brow[int(rand(4))];
  locate(8,10);
  print "|";
  color $ec;
  print substr($eyes[$e],1,5);
  color "reset";
  print "|";
  print locate(9,10),$nose[int(rand(4))];
  print locate(10,10),$lips[int(rand(4))];
  print locate(11,10),$neck[int(rand(4))];

  if ($status eq "APPROVED") {
     $color = "green";
  } else { 
     $color = "red";
  }
  for $l (0..length($status)) {
    locate(13,10);
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
