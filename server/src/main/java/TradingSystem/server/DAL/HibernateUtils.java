package TradingSystem.server.DAL;

import TradingSystem.server.Domain.Questions.BuyerQuestion;
import TradingSystem.server.Domain.Questions.UserQuestion;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserController;
import com.mysql.cj.Session;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.List;

public class HibernateUtils {

    private static EntityManagerFactory emf;
    private static ThreadLocal<EntityManager> threadLocal;
    private static String persistence_unit = "TradingSystem";
    private static boolean allow_persist = false;
    private static boolean begin_transaction = true;

    static {
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public static void setPersistence_unit(String persistence_unit) {
        HibernateUtils.persistence_unit = persistence_unit;
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public static void set_load_tests_mode() {
        if (!HibernateUtils.persistence_unit.equals("TradingSystemTests")) {
            HibernateUtils.persistence_unit = "TradingSystemTests";
            emf = Persistence.createEntityManagerFactory(persistence_unit);
            threadLocal.set(null);
        }
        HibernateUtils.allow_persist = true;
    }

    public static void set_tests_mode() {
        if (!HibernateUtils.persistence_unit.equals("TradingSystemTests")) {
            HibernateUtils.persistence_unit = "TradingSystemTests";
            emf = Persistence.createEntityManagerFactory(persistence_unit);
            threadLocal.set(null);
        }
        HibernateUtils.allow_persist = false;
    }


    public static void set_normal_use() {
        if (!HibernateUtils.persistence_unit.equals("TradingSystem")) {
            HibernateUtils.persistence_unit = "TradingSystem";
            emf = Persistence.createEntityManagerFactory(persistence_unit);
            threadLocal.set(null);
        }
        HibernateUtils.allow_persist = true;
    }

    public static void set_demo_use() {
        if (!HibernateUtils.persistence_unit.equals("demo")) {
            HibernateUtils.persistence_unit = "demo";
            emf = Persistence.createEntityManagerFactory(persistence_unit);
            threadLocal.set(null);
        }
        HibernateUtils.allow_persist = true;
    }

    public static EntityManager getEntityManager() {
        EntityManager em = threadLocal.get();

        if (em == null) {
            em = emf.createEntityManager();
            // set your flush mode here
            threadLocal.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            em.close();
            threadLocal.set(null);
        }
    }

    public static void clear_db() {
//        getEntityManager().createNativeQuery("DROP SCHEMA datatests").executeUpdate();
//        getEntityManager().createNativeQuery("Create SCHEMA  datatests").executeUpdate();
        threadLocal.get().clear();
        threadLocal.get().close();
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal.set(null);
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static void beginTransaction() {
        if (allow_persist && begin_transaction)
            if (!getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().begin();

    }

    public static void rollback() {
        if (allow_persist && begin_transaction)
            getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        if (allow_persist && begin_transaction)
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().commit();
    }

    public static <T> void persist(T obj) {
        if (allow_persist)
            getEntityManager().persist(obj);
    }

    public static <T> void remove(T obj) {
        if (allow_persist)
            getEntityManager().remove(obj);
    }

    public static <T> void merge(T obj) {
        if(allow_persist && begin_transaction) {
//            T object = getEntityManager().find(obj.getClass(),id);
            getEntityManager().merge(obj);
        }
    }

    public static void setBegin_transaction(boolean begin_transaction) {
        HibernateUtils.begin_transaction = begin_transaction;
    }

    public static int get_uc() {
        if(allow_persist)
            return 0;
        int uc = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(id),0) as id FROM database.user").getSingleResult();
        return uc;
    }

    public static int get_max_purchase() {
        if(allow_persist)
            return 0;
        int purchase_id = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(purchase_id),0) as id FROM database.purchase").getSingleResult();
        return purchase_id+1;
    }

    public static int get_sc(){
        if(allow_persist)
            return 1;
        int store_id = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(store_id),1) as id FROM database.store").getSingleResult();
        return store_id+1;
    }

    public static int get_max_store_purchase_id(){
        if(allow_persist)
            return 1;
        int pid = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(store_purchase_id),1) as id FROM database.storepurchase").getSingleResult();
        return pid+1;
    }

    public static int get_max_product_id(){
        if(allow_persist)
            return 1;
        int pid = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(product_id),1) as id FROM database.product").getSingleResult();
        return pid+1;
    }
    public static int get_max_bid_id(){
        if(allow_persist)
            return 1;
        int bid = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.bid").getSingleResult();
        return bid+1;
    }

    public static int get_max_question_id(){
        if(allow_persist)
            return 1;
        int bid = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.buyerquestion").getSingleResult();
        int uid = (int)getEntityManager().createQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.userquestion").getSingleResult();
        return Math.max(bid,uid)+1;
    }

    public static List<Store> stores() {
        String query = "select s from database.store s where s.store_id is not null";
        TypedQuery<Store> tq = getEntityManager().createQuery(query, Store.class);
        List<Store> list;
        try{
            list = tq.getResultList();
            return list;
        }
        catch(NoResultException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> users() {
        String query = "select u from database.user u where u.id is not null";
        TypedQuery<User> tq = getEntityManager().createQuery(query, User.class);
        List<User> list;
        try{
            list = tq.getResultList();
            return list;
        }
        catch(NoResultException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<BuyerQuestion> buyerquestions() {
        String query = "select u from database.buyerquestion u where u.id is not null";
        TypedQuery<BuyerQuestion> tq = getEntityManager().createQuery(query, BuyerQuestion.class);
        List<BuyerQuestion> list;
        try{
            list = tq.getResultList();
            return list;
        }
        catch(NoResultException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<UserQuestion> userQuestions() {
        String query = "select u from database.userquestion u where u.id is not null";
        TypedQuery<UserQuestion> tq = getEntityManager().createQuery(query, UserQuestion.class);
        List<UserQuestion> list;
        try{
            list = tq.getResultList();
            return list;
        }
        catch(NoResultException e) {
            throw new RuntimeException(e);
        }
    }



    //   public static void main(String[] args) {
//        HibernateUtils.clear_db("datatests")
// ;
//    }
}