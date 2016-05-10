springboot-yy-cart
==================

A tutorial spring boot app. Implements a cart as a restful micro-service. Works with the mysql database.


Database
--------
App connects to a local mysql-server 5.1+. Developed and tested on Ubuntu 15.04. Make sure to create a new database and permissions as following:
`mysql://yy:yy@localhost/yycart`


Build
-----

The default maven goals can be followed: `mvn clean install compile package` to produce `target/yycart-0.1.0.jar`

There are also integration tests that can be triggered by `mvn package -DskipTests=false`

Run
---

Start a server with `./run-server.sh` in a separate terminal. Tomcat has default settings to occupy  a port 8080. 

A client part `./client.py` will execute a bunch of **http requests** commands, to make sure that one can easily consume the returned JSON with JS frontend.


PS

Implementation is rather rough, but may serve as a boilerplate code for rest microservices. Comments can be sent to eugeny.yakimovitch@gmail.com

wbr,
yy
