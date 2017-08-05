# SimpleRepository

This project will help you to implementing the Repository Pattern just extended some classes.



Your entities needs to extends one of the classes `Identity` or `IdentityAndAudit`. The firts just implements the `Id` attribute the second implements the `Id` and the datetime atributes for audit matters.

The repository

```java
public class PersonRepository extends JpaRepositoryAudit<Person> {
    public PersonRepository() {
        super(Person, "PersistenceName");
    }
}

-- or

public class PersonRepository extends JpaRepository<Person> {

    public PersonRepository() {
        super(Person, "PersistenceName");
    }

}
```
