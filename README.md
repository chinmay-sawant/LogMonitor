# LogMonitor

Docker based log monitor for PostGres
===============================================

1. Run Command for Docker Container with rabbitmq

docker run -d --name rabbitmq -p 5672:5672 -p 5673:5673 -p 15672:15672 rabbitmq:3.11.9-management


Client 		- 9000
Master 		- 9001
DB-Service 	- 9002


Client 
=========
- Can be deployed on multiple machines
- It will run on client machine periodically and send the error message to rabbitmq for staging

Master 
=========
- To be deployed on 1 machine 
- It will read messages from rabbitmq and save to DB for reading purposes

DB-Service
=========
- To be deployed on 1 machine
- API service which will read messages from DB


22-March-2023
=========

- Added new method to get errors by id
- Added HAETOS support to above method

