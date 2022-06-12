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
  external_services:<-option1>
  database:<-option2>
  
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
*..\server\Config\system_config.txt*  

The second configuration file contains instructions for initialized data for the market when we load the system with demo.  
This file is a text file who contains instructions in the next format :  
<-instruction name>#<-param1>#<-param2>#<-param3>..  

The configuration file path should be:  
*..\server\Config\instructions_config.txt*  
If one of the instructions will failed logically or because a wrong format,  
All of the instructions in the configuration file will cancelled.  
 
## API:
Available functions (pay attention to copy the instruction name exactly) :
 1) Guest :
 * add_product_to_cart#<-email>#<-store id>#<-product id>#<-quantity>
 * remove_product_from_cart#<-store id>#<-product id>
 * add_bid#<-email>#<-store id>#<-product id>#<-quantity>#<-offer price>
 * send_question_to_admin#<-email>#<-question>
 * send_question_to_store#<-email>#<-store id>#<-question>
 * rate_product#<-email>#<-product id>#<-store id>#<-rate>
 * rate_store#<-email>#<-store id>#<-rate>
 * register#<-email>#<-password>#<-name>#<-username>#<-last name>#<-birth date>

 2) User:
 * login#<-email>#<-password>
 * edit_name#<-email>#<-new name>
 * edit_last_name>#<-email>#<-new name>
 * edit_password#<-email>#<-old password>#<-new password>
 * improve_security#<-email>#<-password>#<-question>#<-answer>
 * open_store#<-email>#<-store name>
 * add_admin#<-email>
 
 3) Store Owner:
 * add_manager#<-appointer email>#<-to appint email>#<-store id>
 * add_owner#<-appointer email>#<-to appint email>#<-store id>
 * remove_owner#<-remover email>#<-to remove email>#<-store id>
 * remove_manager#<-remover email>#<-to remove email>#<-store id>
 * close_store_temporarly#<-manager email>#<-store id>
 * add_product_to_store#<-email>#<-store id>#<-quantity>#<-name>#<-price>#<-category>#<-keywords>
 * delete_product_from_store#<-email>#<-product id>#<-store id>
 * edit_product_name#<-product id>#<-store id>#<-name>
 * open_close_store#<-email>#<-store id>
 * manager_answer_question#<-manager email>#<-store id>#<-question id>#<-email>
 
 4) Admin:
 * close_store_permanently#<-admin email>#<-store id>
 * remove_user#<-admin email>#<-user to remove email>
 * admin_answer_question#<-admin email>#<-question id>#<-answer>
 * logout#<-email>
 
Pay attention that all the instructions should keep this order.
 
## How to use?
 <Here come Tom video & words>
  
## Features:
 1. Statistics about the traffic in our system.
 2. Encrypt passwords with PBKDF2 with Hmac SHA1 Algorithm.
 3. Real-time system & client notifications Web Socket-based (sockJS) & observer design pattern.
 4. Use external systems services according http requests to the external server, implemented with adapter & proxy design patterns.
 5. DB - <GAL>
 
 ## Tests: 
 <Here come Eylon words>

## Contrubutors:
Eylon Sade   
Gal Brown  
Amit Grumet  
Tom Nisim  
Amit Moskovitz  
© Copyright


