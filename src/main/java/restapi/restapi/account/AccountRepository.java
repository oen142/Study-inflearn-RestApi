package restapi.restapi.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account ,Long> {

    @Query("select a from Account a join fetch a.roles")
    Optional<Account> findByEmail(String username);
}
