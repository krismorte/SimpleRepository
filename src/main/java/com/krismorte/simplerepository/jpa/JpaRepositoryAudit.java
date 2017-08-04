/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krismorte.simplerepository.jpa;


import com.krismorte.simplerepository.audit.AuditRule;
import com.krismorte.simplerepository.audit.AuditableEnitity;
import com.krismorte.simplerepository.design.RepositoryAudit;
import com.krismorte.simplerepository.identity.IdentityAndAudit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class JpaRepositoryAudit<T extends IdentityAndAudit> implements RepositoryAudit<T> {

    private EntityManagerFactory emf;

    private Class<T> type;

    private AuditRule auditRule;

    public JpaRepositoryAudit(Class<T> type, String persistenceUnitName) {
        this.type = type;
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        auditRule = new AuditRule();
    }

    public void audit(T entity) {
        if (entity.getId() == null) {
            auditRule.updateTime((AuditableEnitity) entity);
        } else {
            auditRule.createTime((AuditableEnitity) entity);
        }
    }

    @Override
    public Set<T> get() {
        List<T> resultList = run(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);

            final Root<T> root = criteria.from(type);
            criteria.select(root);

            final TypedQuery<T> query = entityManager.createQuery(criteria);
            return query.getResultList();
        });

        return new HashSet<>(resultList);
    }

    @Override
    public Optional<T> get(String id) {
        return Optional.ofNullable(run(entityManager -> {
            return entityManager.find(type, id);
        }));
    }

    @Override
    public void persist(T entity) {
        runInTransaction(entityManager -> {
            entityManager.merge(entity);
        });
    }

    @Override
    public void persist(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities.forEach(entityManager::merge);
        });
    }

    @Override
    public void remove(T entity) {
        remove(entity.getId());
    }

    @Override
    public void remove(String id) {
        runInTransaction(entityManager -> {
            final T managedEntity = entityManager.find(type, id);
            if (managedEntity != null) {
                entityManager.remove(managedEntity);
            }
        });
    }

    @Override
    public void remove(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities
                    .stream()
                    .map(IdentityAndAudit::getId)
                    .map(id -> entityManager.find(type, id))
                    .filter(Objects::nonNull)
                    .forEach(entityManager::remove);
        });
    }

    @Override
    public void remove(Predicate<T> predicate) {
        remove(get(predicate));
    }

    private <R> R run(Function<EntityManager, R> function) {
        final EntityManager entityManager = emf.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    private void run(Consumer<EntityManager> function) {
        run(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }

    private <R> R runInTransaction(Function<EntityManager, R> function) {
        return run(entityManager -> {
            entityManager.getTransaction().begin();

            final R result = function.apply(entityManager);

            entityManager.getTransaction().commit();

            return result;
        });
    }

    private void runInTransaction(Consumer<EntityManager> function) {
        runInTransaction(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }
}
