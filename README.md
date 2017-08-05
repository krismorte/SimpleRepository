# SimpleRepository

This project will help you to implementing the Repository Pattern just extended some classes.



Your entities needs to extends one of the classes `Identity` or `IdentityAndAudit`. The firts just implements the Id attribute the second 
implements the Id and the datetime atributes for audit matters.


```java
public class TwitterAccountRepository extends JpaRepositoryAudit<TwitterAccount> {

    public TwitterAccountRepository() {
        super(TwitterAccount.class, PersistenceContext.CONTEXT);
    }

}
```
