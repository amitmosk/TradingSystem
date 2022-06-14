package TradingSystem.server.DAL;

import TradingSystem.server.Domain.StoreModule.StoreController;
import com.mysql.cj.Session;
import net.bytebuddy.asm.Advice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {

    private static EntityManagerFactory emf;
    private static ThreadLocal<EntityManager> threadLocal;
    private static String persistence_unit = "TradingSystemTests";
    private static boolean allow_persist = false;
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
        HibernateUtils.persistence_unit = "TradingSystemTests";
        HibernateUtils.allow_persist = true;
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public static void set_normal_use() {
        HibernateUtils.persistence_unit = "TradingSystem";
        HibernateUtils.allow_persist = true;
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public static void set_demo_use() {
        HibernateUtils.persistence_unit = "Demo";
        HibernateUtils.allow_persist = true;
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
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

  public static void clear_db(String schemeName){
        getEntityManager().createQuery("DROP SCHEMA "+schemeName).executeUpdate();
        getEntityManager().createQuery("Create SCHEMA   "+schemeName).executeUpdate();
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static void beginTransaction() {
        if(allow_persist)
            getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        if(allow_persist)
            getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        if(allow_persist)
            getEntityManager().getTransaction().commit();
    }

    public static <T> void persist(T obj){
        if(allow_persist)
            getEntityManager().persist(obj);
    }

    public static void main(String[] args) {
        HibernateUtils.clear_db("datatests");
    }
}