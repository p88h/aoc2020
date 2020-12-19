#!/usr/bin/perl
%rules={};
sub expand {
    local ($regex) = @_;
    while ($regex =~ m/(?<!\{)\d+/) { $regex =~ s/\b(?<!\{)(\d+)\b/\($rules{$1}\)/g; }
    $regex=~s/[ "]//g;
    while ($regex=~s/\(([a-z]+)\)/$1/g) {}
    return $regex;
}
while (<>) {
    chomp;
    if (m/(\d+): (.*)$/) {
        $rules{$1}=$2;
    } elsif (m/^$/) {
        $regexa = expand($rules{0});
        $rules{8}="42+";
        $rules{11}="42 31 | 42{2} 31{2} | 42{3} 31{3} | 42{4} 31{4} | 42{5} 31{5}";
        $regexb = expand($rules{0});
    } else {
        if (m/^$regexa$/) { $cnt1++; }
        if (m/^$regexb$/) { $cnt2++; }
    }
}
print "$cnt1\n$cnt2\n";