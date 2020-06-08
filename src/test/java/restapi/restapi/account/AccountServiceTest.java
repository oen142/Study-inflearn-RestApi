package restapi.restapi.account;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {
        Set<AccountRole> accountRoles = new HashSet<>();
        accountRoles.add(AccountRole.ADMIN);
        accountRoles.add(AccountRole.USER);
        String password = "kim";
        Account account = Account.builder()
                .email("oen142@naver.com")
                .password(password)
                .roles(accountRoles)
                .build();
        this.accountService.saveAccount(account);
        UserDetailsService userDetailsService = (UserDetailsService) accountService;
        UserDetails user = userDetailsService.loadUserByUsername("oen142@naver.com");


        assertThat(passwordEncoder.matches(password, user.getPassword())).isTrue();

    }

    @Test
    public void findByUsernameFail() {
        String username = "random@email.com";

        try {
            accountService.loadUserByUsername(username);
            fail("supposed to be failed");
        } catch (UsernameNotFoundException e) {
            assertThat(e.getMessage()).containsSequence(username);
        }
    }

    @Test
    @Ignore
    public void findByUsernameFailAnother() {
        String username = "random@email.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));
        accountService.loadUserByUsername(username);
    }
}