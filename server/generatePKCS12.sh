#!/bin/sh

echo "ATTENTING you first need to update the filename to the latest number. edit this file!"

read dummy

cp /usr/local/psa/var/modules/letsencrypt/etc/archive/linzwiki.at/fullchain22.pem ./fullchain.pem
cp /usr/local/psa/var/modules/letsencrypt/etc/archive/linzwiki.at/privkey22.pem ./privkey.pem

openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out linzwiki.p12 -name tomcat

rm privkey.pem
rm fullchain.pem


