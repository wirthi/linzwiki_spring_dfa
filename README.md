
# Deploy
Notes from Christian:

copy the linzwiki.p12 file from the server
note that the relevant scripts are stored here in /server/ as well.

edit resources/application.yml to enable SSL (server.ssl.enabled=true)
./buildNative.sh
then disable SSL again (!)

copy the native binary to server and start there

# Sources

based somewhat on
* https://www.linkedin.com/learning/learning-spring-with-spring-boot-13886371/

