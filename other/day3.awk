{p=p substr($0,1+o%length,1);o+=3}END{print gsub(/#/,"",p)}
