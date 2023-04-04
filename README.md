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
- Added HAETOS to above method

![errorsPage](https://user-images.githubusercontent.com/28671172/229720039-221179b6-e03b-4461-838b-30b19cf621b7.png)


![MicrosoftTeams-image (1)](https://user-images.githubusercontent.com/28671172/229725837-6fac6d16-1c6f-45b1-abb5-8008053095d9.png)
