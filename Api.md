 
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
 * add_admin#<-email>#<-password>#<-name>#<-username>#<-last name>

 2) User:
 * login#<-email>#<-password>
 * edit_name#<-email>#<-new name>
 * edit_last_name>#<-email>#<-new name>
 * edit_password#<-email>#<-old password>#<-new password>
 * improve_security#<-email>#<-password>#<-question>#<-answer>
 * open_store#<-email>#<-store name>

 
 3) Store Owner:
 * add_manager#<-appointer email>#<-to appint email>#<-store id>
 * add_owner#<-appointer email>#<-to appint email>#<-store id>
 * appointment_answer#<-manager email>#<-store id>#<-candidate email>
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
