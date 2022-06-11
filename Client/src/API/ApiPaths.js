
const API_PATH = "http://localhost:8080"

export const EMPLOYEE_BASE_REST_API_URL = API_PATH + '/amit';
export const CONNECTION_ERROR="Connection Error";
export const CATCH="CATCH";


// ========= Connect ========== //

export const LOGIN_PATH = API_PATH + '/login';
export const LOGOUT_PATH = API_PATH + '/logout';
export const REGISTER_PATH = API_PATH + '/register';
export const ONLINE_USER_PATH = API_PATH + '/online_user'

// ========= Store ========== //

export const FIND_STORE_INFORMATION = API_PATH + '/find_store_information';


export const OPEN_STORE = API_PATH + '/open_store';

export const RATE_STORE = API_PATH + '/rate_store';
export const SEND_QUESTION_TO_STORE = API_PATH + '/send_question_to_store';

export const ADD_PRODUCT_TO_STORE = API_PATH + '/add_product_to_store';
export const DELETE_PRODUCT_FROM_STORE = API_PATH + '/delete_product_from_store';

export const SET_STORE_PURCHASE_POLICY = API_PATH + '/set_store_purchase_policy';

export const SET_STORE_DISCOUNT_POLICY = API_PATH + '/set_store_discount_policy';

export const SET_STORE_PURCHASE_RULES= API_PATH + '/set_store_purchase_rules';

export const ADD_OWNER= API_PATH + '/add_owner';

export const DELETE_OWNER= API_PATH + '/delete_owner';

export const ADD_MANAGER= API_PATH + '/add_manager';
export const EDIT_MANAGER_PERMISSIONS= API_PATH + '/edit_manager_permissions';

export const DELETE_MANAGER= API_PATH + '/delete_manager';

export const CLOSE_STORE_TEMPORARILY= API_PATH + '/close_store_temporarily';


export const OPEN_CLOSE_STORE= API_PATH + '/open_close_store';

export const VIEW_STORE_MANAGEMENT_INFORMATION= API_PATH + '/view_store_management_information';
export const MANAGER_VIEW_STORE_QUESTIONS= API_PATH + '/manager_view_store_questions';

export const MANAGER_ANSWER_QUESTION= API_PATH + '/manager_answer_question';

export const VIEW_STORE_PURCHASES_HISTORY= API_PATH + '/view_store_purchases_history';
export const CLOSE_STORE_PERMANENTLY= API_PATH + '/close_store_permanently';
export const GET_PRODUCTS_BY_STORE_ID= API_PATH + '/get_products_by_store_id';
export const GET_ALL_STORES= API_PATH + '/get_all_stores';
export const GET_PERMISSIONS= API_PATH + '/get_permissions';
export const ADD_BID= API_PATH + '/add_bid';
export const MANAGER_ANSWER_BID= API_PATH + '/manager_answer_bid';
export const VIEW_BIDS_STATUS= API_PATH + '/view_bids_status';
export const GET_ALL_CATEGORIES= API_PATH + '/get_all_categories';






// ========= Product ========== //
export const FIND_PRODUCT_INFORMATION = API_PATH + '/find_product_information';


export const FIND_PRODUCTS_BY_NAME = API_PATH + '/find_products_by_name';
export const FIND_PRODUCTS_BY_CATEGORY = API_PATH + '/find_products_by_category';
export const FIND_PRODUCTS_BY_KEYWORDS = API_PATH + '/find_products_by_keywords';


export const ADD_PRODUCT_TO_CART = API_PATH + '/add_product_to_cart';
export const EDIT_PRODUCT_QUANTITY_IN_CART = API_PATH + '/edit_product_quantity_in_cart';
export const REMOVE_PRODUCT_FROM_CART = API_PATH + '/remove_product_from_cart';

export const ADD_PRODUCT_REVIEW = API_PATH + '/add_product_review';
export const RATE_PRODUCT = API_PATH + '/rate_product';


export const EDIT_PRODUCT_NAME = API_PATH + '/edit_product_name';
export const EDIT_PRODUCT_PRICE = API_PATH + '/edit_product_price';
export const EDIT_PRODUCT_CATEGORY = API_PATH + '/edit_product_category';
export const EDIT_PRODUCT_QUANTITY = API_PATH + '/edit_product_quantity';

export const EDIT_PRODUCT_KEY_WORDS = API_PATH + '/edit_product_key_words';


// ========= Cart ========== //

export const VIEW_USER_CART = API_PATH + '/view_user_cart';

export const BUY_CART = API_PATH + '/buy_cart';
export const DELETE_PRODUCT_FROM_CART = API_PATH + '/delete_product_from_cart';

// ========= Admin ========== //

export const SEND_QUESTION_TO_ADMIN = API_PATH + '/send_question_to_admin';

export const REMOVE_USER = API_PATH + '/remove_user';
export const ADMIN_VIEW_USERS_QUESTION = API_PATH + '/admin_view_users_questions';
export const ADMIN_ANSWER_USERS_QUESTION = API_PATH + '/admin_answer_users_questions';

export const ADMIN_VIEW_STORE_PURCHASES_HISTORY = API_PATH + '/admin_view_store_purchases_history';
export const ADMIN_VIEW_USER_PURCHASES_HISTORY = API_PATH + '/admin_view_user_purchases_history';
export const GET_MARKET_STATS = API_PATH + '/get_market_stats';




// // ========= User ========== //

export const VIEW_USER_PURCHASE_HISTORY = API_PATH + '/view_user_purchase_history';
export const VIEW_USER_QUESTIONS = API_PATH + '/get_user_questions';
export const GET_USER_EMAIL = API_PATH + '/get_user_email';

export const GET_USER_NAME = API_PATH + '/get_user_name';
export const GET_USER_LAST_NAME = API_PATH + '/get_user_last_name';

export const EDIT_PASSWORD = API_PATH + '/edit_password';
export const EDIT_NAME = API_PATH + '/edit_name';

export const EDIT_LAST_NAME = API_PATH + '/edit_last_name';

export const UNREGISTER = API_PATH + '/unregister';

export const EDIT_NAME_PREMIUM = API_PATH + '/edit_name_premium';

export const EDIT_LAST_NAME_PREMIUM = API_PATH + '/edit_last_name_premium';

export const EDIT_PASSWORD_PREMIUM = API_PATH + '/edit_password_premium';

export const GET_USER_SECURITY_QUESTION = API_PATH + '/get_user_security_question';


export const IMPROVE_SECURITY = API_PATH + '/improve_security';





// // ========= policies ========== //

///DISCOUNT

export const ADD_SIMPLE_CATEGORY_DISCOUNT = API_PATH + '/add_simple_categorey_discount_rule';
export const ADD_SIMPLE_PRODUCT_DISCOUNT = API_PATH + '/add_simple_product_discount_rule';
export const ADD_SIMPLE_STORE_DISCOUNT = API_PATH + '/add_simple_store_discount_rule';


export const ADD_COMPLEX_DISCOUNT = API_PATH + '/add_complex_discount_rule';
export const ADD_COMPLEX_AND_DISCOUNT = API_PATH + '/add_and_discount_rule';
export const ADD_COMPLEX_OR_DISCOUNT = API_PATH + '/add_or_discount_rule';
export const ADD_COMPLEX_MAX_DISCOUNT = API_PATH + '/add_max_discount_rule';
export const ADD_COMPLEX_PLUS_DISCOUNT = API_PATH + '/add_plus_discount_rule';
export const ADD_COMPLEX_XOR_DISCOUNT = API_PATH + '/add_xor_discount_rule';


export const SEND_PREDDICTS = API_PATH + '/send_predicts';
export const GET_DISCOUNT_POLICY = API_PATH + '/get_discount_policy';



///PURCHASE

export const ADD_SIMPLE_PURCHASE = API_PATH + '/add_simple_purchase';
export const ADD_AND_SIMPLE_PURCHASE = API_PATH + '/add_and_purchase_rule';
export const ADD_OR_SIMPLE_PURCHASE = API_PATH + '/add_or_purchase_rule';

//PREDICT
export const ADD_PREDICT = API_PATH + '/add_predict';
