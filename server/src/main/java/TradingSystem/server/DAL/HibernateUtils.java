package TradingSystem.server.DAL;

import TradingSystem.server.Domain.Questions.BuyerQuestion;
import TradingSystem.server.Domain.Questions.UserQuestion;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Logger.MarketLogger;
import com.mysql.cj.Session;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HibernateUtils {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ThreadLocal<EntityManager> threadLocal;
    private static String persistence_unit = "TradingSystemTests";
    private static boolean allow_persist = false;
    private static boolean begin_transaction = true;

    static {
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
//        em = emf.createEntityManager();
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

    public static synchronized EntityManager getEntityManager() {
//        EntityManager em = threadLocal.get();

        if (em == null) {
            em = emf.createEntityManager();
            // set your flush mode here
//            threadLocal.set(em);
        }
        return em;
    }

    public static synchronized void closeEntityManager() {
        EntityManager em = threadLocal.get();
        if (em != null) {
            em.close();
            threadLocal.set(null);
        }
    }

    public static synchronized void clear_db() {
//        getEntityManager().createNativeQuery("DROP SCHEMA database").executeUpdate();
//        getEntityManager().createNativeQuery("Create SCHEMA  database").executeUpdate();
        threadLocal.get().clear();
        threadLocal.get().close();
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal.set(null);
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static synchronized void beginTransaction() {
        if (allow_persist && begin_transaction)
            if (!getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().begin();

    }

    public static synchronized void rollback() {
        if (allow_persist && begin_transaction) {
            getEntityManager().getTransaction().rollback();
            em.close();
            em = emf.createEntityManager();
        }
    }

    public static synchronized void commit() {
        if (allow_persist && begin_transaction)
            if (getEntityManager().getTransaction().isActive())
                getEntityManager().getTransaction().commit();
    }

    public static synchronized <T> void persist(T obj) {
        if (allow_persist)
            getEntityManager().persist(obj);
    }

    public static synchronized <T> void remove(T obj) {
        if (allow_persist)
            getEntityManager().remove(obj);
    }

    public static synchronized <T> T merge(T obj) {
        if (allow_persist && begin_transaction) {
            if (!getEntityManager().contains(obj))
                return getEntityManager().merge(obj);
        }
        return obj;
    }

    public static void setBegin_transaction(boolean begin_transaction) {
        HibernateUtils.begin_transaction = begin_transaction;
    }

    public static synchronized int get_uc() {
        if (!allow_persist)
            return 0;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(id),0) as id FROM database.user").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_max_purchase() {
        if (!allow_persist)
            return 0;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(purchase_id),0) as id FROM database.purchase").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_sc() {
        if (!allow_persist)
            return 1;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(store_id),1) as id FROM database.store").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_max_store_purchase_id() {
        if (!allow_persist)
            return 1;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(store_purchase_id),1) as id FROM database.storepurchase").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_max_product_id() {
        if (!allow_persist)
            return 1;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(product_id),1) as id FROM database.product").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_max_bid_id() {
        if (!allow_persist)
            return 1;
        BigInteger res = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.bid").getSingleResult();
        return res.intValue() + 1;
    }

    public static synchronized int get_max_question_id() {
        if (!allow_persist)
            return 1;
        BigInteger bid = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.buyerquestion").getSingleResult();
        BigInteger uid = (BigInteger) getEntityManager().createNativeQuery("SELECT COALESCE(MAX(bid_id),1) as id FROM database.userquestion").getSingleResult();
        return Math.max(bid.intValue(), uid.intValue()) + 1;
    }

    public static synchronized Map<Integer ,Store> stores() {
        products();
        String query = "SELECT store_id FROM database.store";
        Map<Integer, Store> map = new HashMap<>();
        try {
            List<Integer> lst = getEntityManager().createNativeQuery(query).getResultList();
            for (Integer i : lst) {
                Store s = em.find(Store.class, i);
                map.put(i,s);
            }
        } catch (Exception e) {
            MarketLogger.getInstance().add_log(e.getMessage());
            throw new RuntimeException(e);
        }
        return map;
    }

    public static synchronized Map<Integer ,Product> products() {
        String query = "SELECT product_id FROM database.product";
        Map<Integer, Product> map = new HashMap<>();
        try {
            List<Integer> lst = getEntityManager().createNativeQuery(query).getResultList();
            for (Integer i : lst) {
                Product p = em.find(Product.class, i);
                map.put(i,p);
            }
        } catch (Exception e) {
            MarketLogger.getInstance().add_log(e.getMessage());
            throw new RuntimeException(e);
        }
        return map;
    }

    public static synchronized Map<String , User> users() {
        String query = "SELECT id FROM database.user";
        Map<String, User> map = new HashMap<>();
        try {
            List<BigInteger> lst = getEntityManager().createNativeQuery(query).getResultList();
            for (BigInteger i : lst) {
                User u = em.find(User.class, i.longValue());
                map.put(u.user_email(),u);
            }
        } catch (Exception e) {
            MarketLogger.getInstance().add_log(e.getMessage());
            throw new RuntimeException(e);
        }
        return map;
    }

    public static synchronized List<BuyerQuestion> buyerquestions() {
        String query = "select u from database.buyerquestion u where u.id is not null";
        TypedQuery<BuyerQuestion> tq = getEntityManager().createQuery(query, BuyerQuestion.class);
        List<BuyerQuestion> list;
        try {
            list = tq.getResultList();
            return list;
        } catch (NoResultException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized List<UserQuestion> userQuestions() {
        String query = "select u from database.userquestion u where u.id is not null";
        TypedQuery<UserQuestion> tq = getEntityManager().createQuery(query, UserQuestion.class);
        List<UserQuestion> list;
        try {
            list = tq.getResultList();
            return list;
        } catch (NoResultException e) {
            throw new RuntimeException(e);
        }
    }


    //   public static void main(String[] args) {
//        HibernateUtils.clear_db("database")
// ;
//    }
}