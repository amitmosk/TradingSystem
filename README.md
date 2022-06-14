# Trading System

Workshop on Software Engineering Project -- 2022

A trading system is a system that enables trading infrastructure between sellers and buyers.  
The system is composite from a collection of stores.  
A store contains identifying details and stock of products, with each product having different characteristics.  
System users visit the market for buying, selling, and managing the stores.  

## Initialization
There are 2 configuration files for our system.  
First, the system configuration file, who choose the way to initialize the external services & database.  
The text in the file should be in the format:

  ```diff
  external_services:<-option1>  
  database:<-option2>
```

1) the external services will contain one of the following values:  
* tests  
* real 

For tests, we will not connect to the external systems and managed the requests locally.  
For real time, we will connect to the external systems.  

2) The database will contain one of the following values:
* tests
* real
* demo

The demo option is for running the application and test it manually, the data will load from data configuration file(see up next).  
The real option is for real-time use of the application, for loading the database.  
The tests option is for clean database.

The configuration file path should be:  
```diff
 ..\server\Config\system_config.txt 
```

The second configuration file contains instructions for initialized data for the market when we load the system with demo.  
This file is a text file who contains instructions in the next format :  
```diff
 <-instruction name>#<-param1>#<-param2>#<-param3>..
```

The configuration file path should be:  
```diff
 ..\server\Config\instructions_config.txt  
```

If one of the instructions will failed logically or because a wrong format,  
All of the instructions in the configuration file will cancelled.  
 
## API:
[API link](https://github.com/amitmosk/TradingSystem/blob/main/Api.md)  

 
Pay attention that all the instructions should keep this order.
 
## How to use?
 <Here come Tom video & words>
  
## Features:
 1. Statistics about the traffic in our system.
 2. Encrypt passwords with PBKDF2 with Hmac SHA1 Algorithm.
 3. Real-time system & client notifications Web Socket-based (sockJS) & observer design pattern.
 4. Use external systems services according http requests to the external server, implemented with adapter & proxy design patterns.
 5. DB - in our system we worked with Hibernate orm libary, Hibernate ORM (or simply Hibernate) is an object–relational mapping tool for  
    the Java programming language. It provides a framework for mapping an object-oriented domain model to a relational database.

## Contrubutors:
Eylon Sade   
Gal Brown  
Amit Grumet  
Tom Nisim  
Amit Moskovitz  
© Copyright


