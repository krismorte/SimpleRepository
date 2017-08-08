# SimpleRepository

This project will help you to implementing the Repository Pattern just extended some classes.



first of all your entities needs to extends one of these classes `Identity` or `IdentityAndAudit`. 
The firts one implements the JPA `Id` attribute. The second one implements the `Id` plus `createTime` and  `updateTime` atributes for audit matters.

Extends the righ repository

```java
public class PersonRepository extends JpaRepositoryAudit<Person> {
    public PersonRepository() {
        super(Person, "PersistenceName");
    }
}

// OR

public class PersonRepository extends JpaRepository<Person> {

    public PersonRepository() {
        super(Person, "PersistenceName");
    }

}
```
Finally write your own queries

```java
public class PersonRepository extends JpaRepositoryAudit<Person> {
    public PersonRepository() {
        super(Person, "PersistenceName");
    }

    public List<Person> getByName(String personName) {
        return run(entityManager -> {

            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery criteria = criteriaBuilder.createQuery(Proxy.class);

            entityManager.getTransaction().begin();
            final Root<Person> root = criteria.from(Person.class);
            final EntityType<Person> entityModel = root.getModel();

            Predicate predicate1 = criteriaBuilder.equal(root.get(entityModel.getSingularAttribute("name", Boolean.class)), personName);

            criteria.select(root).where(predicate1);

            entityManager.getTransaction().commit();
            final TypedQuery<Proxy> query = entityManager.createQuery(criteria);
            return query.getResultList();
        });
    }
}
```
