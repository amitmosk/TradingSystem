
const API_PATH = "http://localhost:8080/api"

export const EMPLOYEE_BASE_REST_API_URL = API_PATH + '/eylon';



// ========= Connect ========== //

export const LOGIN_PATH = API_PATH + '/login';
export const LOGOUT_PATH = API_PATH + '/logout';
export const REGISTER_PATH = API_PATH + '/register';

// ========= Store ========== //

export const FIND_STORE_INFORMATION = API_PATH + '/store_information';


export const OPEN_STORE = API_PATH + '/open_store';

export const RATE_STORE = API_PATH + '/rate_store';
export const SEND_QUESTION_TO_STORE = API_PATH + '/send_question_to_store';

export const ADD_PRODUCT_TO_STORE = API_PATH + '/add_product_to_store';

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






// ========= Product ========== //
export const FIND_PRODUCT_INFORMATION = API_PATH + '/product_information';


export const FIND_PRODUCTS_BY_NAME = API_PATH + '/find_product_by_name';
export const FIND_PRODUCTS_BY_CATEGORY = API_PATH + '/find_product_by_caregory';
export const FIND_PRODUCTS_BY_KEYWORDS = API_PATH + '/find_product_by_keywords';


export const ADD_PRODUCT_TO_CART = API_PATH + '/add_product_to_cart';
export const EDIT_PRODUCT_QUANTITY_IN_CART = API_PATH + '/add_product_to_cart';
export const REMOVE_PRODUCT_FROM_CART = API_PATH + '/remove_product_from_cart';

export const ADD_PRODUCT_REVIEW = API_PATH + '/add_product_review';
export const RATE_PRODUCT = API_PATH + '/rate_product';


export const EDIT_PRODUCT_NAME = API_PATH + '/edit_product_name';
export const EDIT_PRODUCT_PRICE = API_PATH + '/edit_product_price';
export const EDIT_PRODUCT_CATEGORY = API_PATH + '/edit_product_category';

export const EDIT_PRODUCT_KEY_WORDS = API_PATH + '/edit_product_key_words';


// ========= Cart ========== //

export const VIEW_USER_CART = API_PATH + '/view_user_cart';

export const BUY_CART = API_PATH + '/buy_cart';

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















// export const STORE_PATH = API_PATH + '/store';
// export const OPEN_STORE_PATH = STORE_PATH + '/openStore';
// export const ADD_ITEM_TO_STORE_PATH = STORE_PATH + '/addItem';
// export const REMOVE_ITEM_FROM_STORE_PATH = STORE_PATH + '/removeItem';
// export const EDIT_ITEM_IN_STORE_PATH = STORE_PATH + '/editItem';
// export function GET_ITEM_IN_STORE_PATH(storeId, itemId) { return  STORE_PATH + `/${storeId}/${itemId}`; }
// export function GET_ALL_ITEMS_IN_STORE_PATH(storeId){ return STORE_PATH + '/getAllItems/' + storeId }
// export function GET_STORE_PERMISSION_FOR_USER_PATH(storeId) { return STORE_PATH + '/storePermissionForUser/' + storeId }
// export const SEARCH_ITEMS_PATH = STORE_PATH + '/search';
// export const SEARCH_STORE_PATH = STORE_PATH + '/searchStore';

// export function STAFF_OF_STORE_PATH(storeId){ return STORE_PATH + `/${storeId}/staff` }
// export function STAFF_PERMISSIONS_OF_STORE_PATH(storeId){ return STORE_PATH + `/${storeId}/staff` }
// export function UPDATE_MANAGER_PERMISSIONS_IN_STORE_PATH(storeId){ return STORE_PATH + `/${storeId}/staff` }

// export function GET_HISTORY_OF_STORE_PATH(storeId){ return STORE_PATH + `/${storeId}/history` }

// export function ADD_POLICY_TO_STORE_PATH(storeId) { return STORE_PATH + `/${storeId}/policy`}
// export function ADD_DISCOUNT_TO_STORE_PATH(storeId) { return STORE_PATH + `/${storeId}/discount`}

// export function ASK_TO_BID_ITEM (storeId) { return STORE_PATH + `/${storeId}/askToBid`}

// export function Get_All_Bid_Waiting (storeId) { return STORE_PATH + `/${storeId}/getAllBids`}

// export function APPROVE_OR_DISAPPROVE_BID (storeId){ return STORE_PATH+`/${storeId}/approveOrDisapproveBid`}


// // ========= Cart ========== //

// export const CART_PATH = API_PATH + '/cart';
// export const ADD_ITEM_TO_CART_PATH = CART_PATH + '/addItem';
// export const EDIT_ITEM_IN_CART_PATH = CART_PATH + '/editItemAmount';
// export const GET_CART_PATH = CART_PATH + '/getCart';
// export const GET_PURCHASE_PRICE_CART_PATH = CART_PATH + "/getPurchasePrice";
// export const PURCHASE_CART_PATH = CART_PATH + "/PurchaseCart";

// // ========= User ========== //

// export const USER_PATH = API_PATH + '/user';
// export const GET_ALL_OWNED_STORES = USER_PATH + '/getALlStoreIds';
// export const GET_ALL_MANAGED_STORES = USER_PATH + '/aLlManagedStoreIds'
// export const GET_USER_BASIC_INFO_PATH = USER_PATH + '/getUserBasicInfo';
// export const GET_USER_PURCHASE_HISTORY_PATH = USER_PATH + '/purchaseHistory'


// export function ADMIN_GET_USER_PURCHASE_HISTORY(userId) {return USER_PATH + `/${userId}/userHistory`}
// export function ADMIN_GET_STORE_PURCHASE_HISTORY(storeId) {return STORE_PATH + `/${storeId}/storeHistory`}
// export const ADMIN_GET_LOGIN_STATS_PATH = API_PATH + '/stats/loginStats'