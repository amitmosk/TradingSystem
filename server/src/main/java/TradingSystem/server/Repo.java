package TradingSystem.server;

import TradingSystem.server.Domain.StoreModule.Store.Store;
import javax.persistence.*;
import java.util.List;
public class Repo {

    private static Repo repo = null;
    private static EntityManager em;
    private static String persistence_unit = "TradingSystem";

    public static void setPersistence_unit(String persistence_unit) {
        Repo.persistence_unit = persistence_unit;
    }

    private Repo() {
    }

    public static void set(Repo repo, EntityManager em, EntityTransaction et) {
        Repo.repo = repo;
        Repo.em = em;
    }
    public static Repo getInstance() {
        if (repo == null) {
            repo = new Repo();
        }
        return repo;
    }

    public static EntityManager getEm() {
        if (em == null) {
            //EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistence_unit);
            em = Persistence.createEntityManagerFactory(persistence_unit).createEntityManager();
        }
        return em;
    }

    public static <T> void merge(T obj) {
        EntityTransaction et = null;
        synchronized (getEm()) {
            try {
                et = getEm().getTransaction();
                et.begin();
                getEm().merge(obj);
                et.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (et != null)
                    et.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> void persist(T obj) {
        EntityTransaction et = null;
        EntityManager em = getEm();
        synchronized (em) {
            try {
                et = em.getTransaction();
                et.begin();
                em.persist(obj);
                et.commit();
            } catch (Exception e) {
                if (et != null)
                    et.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isDBEmpty() {
        getInstance();
        getEm();
        String query = "select c from Store c where c.Store is not null";
        TypedQuery<Store> tq = em.createQuery(query, Store.class);
        List<Store> list;
        try {
            list = tq.getResultList();
            if (list.size() == 0)
                return true;
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }
}

