package com.bitcamp.centro.estetico.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bitcamp.centro.estetico.models.Model;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.models.UserCredentials;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public class DAO {
    private static final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    public static <T extends Model> Optional<Model> insert(T obj) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            session.persist(obj);

            tx.commit();

            return Optional.ofNullable(obj);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return Optional.empty();
    }

    public static <T extends Model> boolean isEmpty(Class<T> c) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(c);
            Root<T> entity = q.from(c);
            List<T> list = session.createSelectionQuery(q).setMaxResults(1).list();

            tx.commit();

            return list.isEmpty();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return true;
    }

    public static <T extends Model> Optional<T> get(Class<T> c, Long id) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            T m = session.get(c, id);

            tx.commit();

            return Optional.ofNullable(m);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return Optional.empty();
    }

    public static Optional<User> getUser(String username) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<UserCredentials> cq = cb.createQuery(UserCredentials.class);
            Root<UserCredentials> entity = cq.from(UserCredentials.class);

            cq.where(cb.equal(entity.get("username"), username));
            UserCredentials uc = session.createSelectionQuery(cq).getSingleResult();

            User u = uc.getUser();

            tx.commit();

            return Optional.ofNullable(u);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return Optional.empty();
    }

    public static <T extends Model> List<T> getAll(Class<T> c) {
        Transaction tx = null;
        List<T> list = new ArrayList<>();
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(c);
            Root<T> entity = q.from(c);
            list = session.createSelectionQuery(q).list();

            tx.commit();

            return list;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return list;
    }
    
    public static <T extends Model> Vector<T> getAllVector(Class<T> c) {
        return new Vector<T>(getAll(c));
    }

    public static <T extends Model> Optional<T> update(T obj) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            T o = session.merge(obj);
            tx.commit();

            return Optional.of(o);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return Optional.empty();
    }

    public static <T extends Model> void toggle(Class<T> c, Long id) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<T> cq = cb.createCriteriaUpdate(c);
            Root<T> entity = cq.from(c);

            T obj = session.get(c, id);
            tx.commit();

            cq.set("is_enabled", !obj.isEnabled());
            cq.where(cb.equal(entity.get("id"), id));
            session.createMutationQuery(cq);

            tx.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public static <T extends Model> void delete(T obj) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            session.remove(obj);

            tx.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public static <T extends Model> void delete(Class<T> c, Long id) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<T> cq = cb.createCriteriaDelete(c);
            Root<T> entity = cq.from(c);

            cq.where(cb.equal(entity.get("id"), id));

            session.createMutationQuery(cq);

            tx.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
