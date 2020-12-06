#!/usr/bin/perl
use Curses;
use Time::HiRes qw(usleep);

initscr;
$win = new Curses;
$tota = 0;
$tots = 0;
$rec = "0000";
$wait = 100000;

sub blank { 
    @hdr = split(//, "[ ]"x26);
    for $ch ('a'..'z') {
        my $idx = (ord($ch)-97)*3+1;
        $hdr[$idx]=$ch;
    }
    $hdr = join("", @hdr);
    $sep = "[-]"x26;
    $win->addstr(0, 0, "/".("-"x40)."HAL-9000".("-"x40)."\\");
    $win->addstr(1, 0, "| ABC $hdr XYZ |");
    $win->addstr(2, 0, "|     $sep     |");
    my $idx = "000";
    for (1..9) {
        $idx++;
        $win->addstr(2 + $_, 0, "| $idx $sep 000 |");
    }
    $outs = "[ ]"x26;
    $outa = "[x]"x26;
    $win->addstr(12, 0, "|     $sep     |");
    $win->addstr(13, 0, "| ANY $outs 000 |");
    $win->addstr(14, 0, "| ALL $outa 026 |");
    $win->addstr(15, 0,  "\\".("-"x18)."ARSTOTZKA CUSTOMS AND BORDER PROTECTION FORM AOC2020".("-"x18)."/");
    $win->addstr(17, 0, "NEW GROUP. SUM.ANY=$tots SUM.ALL=$tota.          ");
    $win->addstr(18, 0, "                            ");
    $win->addstr(18, 0, "REC $rec ");
    $win->refresh;
    usleep(2*$wait);
}

$/="\n\n";
while (<>) {
    $tota += 26; $rec++;
    blank();
    @lines = split(/\n/, $_);
    @sum = split(//, "[ ]"x26);
    @all = split(//, "[x]"x26);
    for $line (0..$#lines) {
        my $cnt = "000";
        @fmt = split(//, "[ ]"x26);
        for $ch (split //, $lines[$line]) {
            my $idx = (ord($ch)-97)*3+1;
            $fmt[$idx]='x';
            if ($sum[$idx] ne 'x') {
                $sum[$idx]='x';
                $tots++;
            }
            $cnt++;
        }
        my $cnta = "000";
        my $cnts = "000";
        for $ch ('a'..'z') {
            my $idx = (ord($ch)-97)*3+1;
            if ($fmt[$idx] ne 'x' && $all[$idx] eq 'x') {
                $all[$idx] = ' ';
                $tota--;
            }
            if ($sum[$idx] eq 'x') {
                $cnts++;
            }
            if ($all[$idx] eq 'x') {
                $cnta++;
            }
        }
        my $out = join("", @fmt);
        my $outs = join("", @sum);
        my $outa = join("", @all);
        $win->addstr(3+$line, 6,  "$out $cnt");
        $win->addstr(13, 6,  "$outs $cnts");
        $win->addstr(14, 6,  "$outa $cnta");
        $win->addstr(17, 0, "ADD FORM$line. SUM.ANY=$tots SUM.ALL=$tota.     ");
        $win->addstr(18, 0, "REC $rec ".("."x$line));
        $win->refresh;
        usleep($wait);
    }
    $win->addstr(17, 0, "END GROUP. SUM.ANY=$tots SUM.ALL=$tota. CHK OK.    ");
    $win->addstr(18, 0, "REC $rec ".("."x$#lines));
    $win->refresh;
    usleep(3*$wait);
    $wait -= $wait / 100;
}

endwin;
print "SUM.ANY=$tots SUM.ALL=$tota";
usleep(10*$wait);
