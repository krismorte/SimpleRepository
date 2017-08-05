# SimpleRepository

Your entities needs to extends Identity class or IdentityAndAudit class.

public class TwitterAccountRepository extends JpaRepositoryAudit<TwitterAccount> {

    public TwitterAccountRepository() {
        super(TwitterAccount.class, PersistenceContext.CONTEXT);
    }

}
