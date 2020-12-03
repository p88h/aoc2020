while (<>) {
  chomp;s/^(\d+)-(\d+)\s(\w)\:\s(\w+).*$/($3.*){$1,$2}/;
  $cnt2++ if (substr($4,$1-1,1) eq $3) ^ (substr($4,$2-1,1) eq $3);
  $cnt1++ if ($4 =~ m/$_/);
}
print "$cnt1\n$cnt2\n";
