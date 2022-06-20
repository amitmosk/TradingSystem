package TradingSystem.server.DAL;

import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import com.mysql.cj.Session;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    //   public static void main(String[] args) {
//        HibernateUtils.clear_db("datatests")
// ;
//    }
}