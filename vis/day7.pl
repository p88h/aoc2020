#!/usr/bin/perl
use Term::ANSIScreen qw/:color :cursor :screen :keyboard/;
use Time::HiRes qw(usleep);

setmode(1);
cls;

my %boxes = ( bright => '[*]', clear => '[O]', dark => '{-}', dim => '{#}', dotted => '[:]', drab => '[&]',
    dull => '[~]', faded => '{+}', light => '<=>', mirrored => '[M]', muted => '[m]', pale => 'IWI', 
    plaid => '[/]', posh => '[$]', shiny => '{*}', striped => '[=]', vibrant => '<*>', wavy => '[~]' );

my %colors = ( aqua => '00FFFF', beige => 'F5F5DC', black => '000000', blue => '0000FF', bronze => 'CDF732',
    brown => 'A52A2A', chartreuse => '7FFF00', coral => 'FF7F50', crimson => 'DC143C', cyan => '00FFFF',
    fuchsia => 'FF00FF', gold => 'FFD700', gray => '808080', green => '008000', indigo => '4B0082', 
    lavender => 'E6E6FA', lime => 'BFFF00', magenta => 'FF00FF', maroon => '800000', olive => '808000',
    orange => 'FFA500', plum => 'DDA0DD', purple => '800080', red => 'FF0000', salmon => 'FF8C69',
    silver => 'C0C0C0', tan => 'D2B48C', teal => '008080', tomato => 'FF6347', turquoise => '40E0D0',
    violet => 'EE82EE',  white => 'FFFFFF', yellow => 'FFFF00' );

for (keys %colors) {
    $colors{$_} =~ m/^([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$/i;
    $r = ord(pack("H*",$1));
    $g = ord(pack("H*",$2));
    $b = ord(pack("H*",$3));
    $rgb{$_} = "$r;$g;$b";
} 

sub bag {
    my ($box, $color) = split / /, $_[0];
    return "\x1b[38;2;".$rgb{$color}."m".$boxes{$box}."\x1b[0m ";
}

my %rules = ();

while (<>) {
    @_ = split / /, $_;
    #print bag($_[0], $_[1])." => ";
    my $bag = $_[0]." ".$_[1];
    for $idx (1..(scalar @_)/4 - 1) {
        #print $_[4*$idx]." x ".bag($_[4*$idx+1],$_[4*$idx+2])." ";    
        $rules{$bag} .= $_[4*$idx]." ".$_[4*$idx+1]." ".$_[4*$idx+2].";";
    } 
    #print "\n";
}

@old =  ();
@bags = ("shiny gold");
$W = 34;
my $p = 0;

while (@bags) {
    my $out = "";
    my $bag = shift @bags;
    my $n = $p + 1;
    my $t = $n + scalar @bags;
    my $px = $p % $W, $py = int($p / $W);
    my $nx = $n % $W, $ny = int($n / $W);
    print locate(1 + $py, 1 + $px * 4),bag($bag);
    #color reset;
    print locate(1 + $ny, 1 + $nx * 4),"<<<";
    #print "open $bag (contains: $rules{$bag})\n";    
    for (split(/;/, $rules{$bag})) {
        m/^(\d+) (.*)$/;
        for (1..$1) { 
            #print "add $2\n";
            $t++; 
            my $tx = $t % $W, $ty = int($t / $W);
            print locate(1 + $ty, 1 + $tx * 4),bag($2);
            push (@bags, $2); 
            usleep(5000);
        }
    }
    $t--;
    push (@old, $bag);
    print locate(39, 0),"$t\n";
    $p = $n;
    usleep(5000);
}
usleep(500000);
cls;