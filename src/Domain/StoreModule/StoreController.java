package Domain.StoreModule;

import java.util.HashMap;
import java.util.LinkedList;

public class StoreController {
    private HashMap<Integer, Store> stores;
    private int store_ids;
    private static StoreController instance = null;

    public static StoreController get_instance()
    {
        if (instance == null)
            instance = new StoreController();
        return instance;
    }
    private StoreController() {
        this.store_ids = 0;
        this.stores = new HashMap<Integer, Store>();
    }


    /**
     *
     * @param store_id represent the store we asked to close
     * @param user_id the user who asked to close the store
     * @throws if the user is not store founder OR the store or the user are not exist.
     * @return false if the store was already close, and true if we close the store temporarily
     */
    public boolean close_store_temporarily(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        return store.close_store_temporarily(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param store_id represent the store we asked to re-open
     * @param user_id the user who asked to re-open the store
     * @throws if the user is not store founder OR the store or the user are not exist.
     * @return false if the store was already open, and true if the store were re-open
     */
    public boolean open_close_store(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        return store.open_close_store(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param user_id the user who ask to change the permissions.
     * @param manager_id the user who we ask to change his permissions.
     * @param store_id this method is according specific store.
     * @param permissions a list with all the permissions that we would like give the user.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalArgumentException the manager isn't appointed by user,
     * @throws IllegalArgumentException if the user asking change his own permissions.
     */
    public void edit_manager_specific_permissions(int user_id, int manager_id, int store_id, LinkedList<StorePermission> permissions){
        Store store = this.is_valid_store(store_id);
        store.set_permissions(user_id, manager_id, permissions);
        // TODO: 22/04/2022 : update DB @ write to logger

    }

    /**
     *
     * @param user_id who ask to view store information,
     * @param store_id information of a specific store,
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return an object with managers & permissions data.
     */
    public StoreManagersInfo view_store_management_information(int user_id, int store_id) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        return store.view_store_management_information(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id questions from a specific store,
     * @param user_id who ask to view store questions,
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return an object with store's questions.
     */
    public HashMap<Integer, Question> view_store_questions(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        return store.view_store_questions(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id
     * @param user_id the manager who wants to answer the question
     * @param question_id a specific question that the user get from view_store_questions
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @param answer the answer of the store manager to the user question.
     */
    public void answer_question(int store_id, int user_id, int question_id, String answer) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        store.answer_question(store_id, user_id, question_id, answer);
        // TODO: 22/04/2022 : write to logger & DB
    }

    /**
     *
     * @param store_id the store that we want to get all the purchases history
     * @param user_id the manager
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return a list with all the purchases history
     */
    public HashMap<Integer, Purchase> view_store_purchases_history(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.is_valid_store(store_id);
        return store.view_store_purchases_history(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id the store who have to close permanently
     * @param user_id admin
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return true if the store was open and now we close it
     */
    public boolean close_store_permanently(int store_id, int user_id)
    {
        // TODO: have to check that the user is admin
        Store store = this.is_valid_store(store_id);
        return store.close_store_permanently(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param store_id
     * @throws if the store not exist
     * @return the store
     */
    private Store is_valid_store(int store_id) {
        if (this.stores.containsKey(store_id))
            return this.stores.get(store_id);
        throw new IllegalArgumentException("the store is not exist");
    }


    // tom

    public String find_store_information(int store_id) throws Exception {
        if (!stores.containsKey(store_id))
        {
            throw new Exception("store does not exist");
        }
        return stores.get(store_id).get_information();
    }

    public String find_product_information(int product_id) throws Exception {
        int store_id_of_the_product = is_product_exist(product_id);
        if (store_id_of_the_product == -1)
        {
            throw new Exception("Product does not exist");
        }
        return stores.get(store_id_of_the_product).get_product_information(product_id);
    }

    public int is_product_exist(int product_id)
    {
        // return store id of the product or -1 if the product does not exist
        int store_id_of_the_product=-1;
        for (Store s:stores.values()) {
            if (s.is_product_exist(product_id))
            {
                store_id_of_the_product = s.getStore_id();
                break;
            }
        }
        return store_id_of_the_product;
    }

}

