# TradingSystem
Workshop on Software Engineering Project -- 2022
-- Contrubutors:
Eylon Sade 
Gal Brown
Amit Grumet
Tom Nisim
Amit Moskovitz

Config file 
The configuration file contatins instructions for initialized data for the market when we load the system.
This file is a text file who contatins instructions in the next format :
<instruction name>#<param1>#<param2>#<param3>..
The config file path should be:
..\server\Config\start_config.txt

If one of the instructions will failed logicilly or because a wrong format,
All of the instructions in the configuration file will canceld.
 
Available functions (pay attention to copy the instruction name exactly) :
 * register
 * login
 * open_store
 * add_manager
 * add_owner
 * remove_owner
 * remove_manager
 * close_store_temporarly
 * close_store_permanently
  * logout

Pay attention that all the instructions should keep this order.