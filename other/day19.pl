#!/usr/bin/perl
%rules={};
sub expand {
    local ($regex) = @_;
    while ($regex =~ m/\d+/) { $regex =~ s/\s*(\d+)\s*/\($rules{$1}\)/g; }
    while ($regex=~s/\(([a-z]+)\)/$1/g) {}
    return $regex;
}
while (<>) {
    chomp;
    if (m/(\d+): \"?(.*?)\"?$/) {
        $rules{$1}=$2;
    } elsif (m/^$/) {
        $regexa = expand($rules{0});
        $rules{8}="42+";
        $rules{11}="?<E> 42 (?&E)* 31";
        $regexb = expand($rules{0});
    } else {
        if (m/^$regexa$/) { $cnt1++; }
        if (m/^$regexb$/) { $cnt2++; }
    }
}
print "$cnt1\n$cnt2\n";
