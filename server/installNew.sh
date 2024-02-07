#!/bin/sh

killall linzwiki_dfi

rm linzwiki_dfi.bak
mv linzwiki_dfi linzwiki_dfi.bak

mv learning-spring linzwiki_dfi
chmod 755 linzwiki_dfi
chown linzwikiat.psaserv linzwiki_dfi

echo "now do:   ./linzwiki_dfi >linzwiki_dfi.log &"



