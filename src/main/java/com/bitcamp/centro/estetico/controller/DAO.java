package com.bitcamp.centro.estetico.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import com.bitcamp.centro.estetico.models.Model;
import com.bitcamp.centro.estetico.models.User;
import com.bitcamp.centro.estetico.models.UserCredentials;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public class DAO {
    private static final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    public static <T extends Model> Optional<T> insert(T obj) {
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
            q.from(c);
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
            q.from(c);
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

    public static <T extends Model> T update(T obj) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            T o = session.merge(obj);
            tx.commit();

            return o;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }

        return obj;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Model> void toggle(T obj) {
        Transaction tx = null;
        try (var session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Class<T> c = null;
            if(obj instanceof T) {
                c = (Class<T>) obj.getClass();
            }
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaUpdate<T> cq = cb.createCriteriaUpdate(c);
            Root<T> entity = cq.from(c);

            obj.setEnabled(!obj.isEnabled());

            cq.set(entity.get("isEnabled"), obj.isEnabled());
            cq.where(cb.equal(entity.get("id"), obj.getId()));
            MutationQuery query = session.createMutationQuery(cq);
            query.executeUpdate();

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
}
