package TradingSystem.server.DAL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {

    private static final EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> threadLocal;
    private static String persistence_unit = "TradingSystemTests";
    static {
        emf = Persistence.createEntityManagerFactory(persistence_unit);
        threadLocal = new ThreadLocal<EntityManager>();
    }

    public static void setPersistence_unit(String persistence_unit) {
        HibernateUtils.persistence_unit = persistence_unit;
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

    public static void closeEntityManagerFactory() {
        emf.close();
    }

    public static void beginTransaction() {
        if(!persistence_unit.equals("TradingSystemTests"))
            getEntityManager().getTransaction().begin();
    }

    public static void rollback() {
        if(!persistence_unit.equals("TradingSystemTests"))
            getEntityManager().getTransaction().rollback();
    }

    public static void commit() {
        if(!persistence_unit.equals("TradingSystemTests"))
            getEntityManager().getTransaction().commit();
    }

    public static <T> void persist(T obj){
        if(!persistence_unit.equals("TradingSystemTests"))
            getEntityManager().persist(obj);
    }
}