#!/usr/bin/perl
$/="\n\n";
while (<>) {
 $v++ if (m/((byr|iyr|eyr|hgt|hcl|ecl|pid):.*){7}/s);
 $w++ if (m/((byr:(19[2-9][0-9]|200[0-2])|
              iyr:20(1[0-9]|20)|
              eyr:20(2[0-9]|30)|
              hgt:(1[5-8][0-9]cm|19[0-3]cm|59in|6[0-9]in|7[0-6]in)|
              hcl:\#[0-9a-f]{6}|
              ecl:(amb|blu|brn|gry|grn|hzl|oth)|
              pid:[0-9]{9})\b.*){7}/sx);
}
print "$v\n$w\n";
