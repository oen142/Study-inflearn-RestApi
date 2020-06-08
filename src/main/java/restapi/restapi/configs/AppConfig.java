package restapi.restapi.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import restapi.restapi.account.Account;
import restapi.restapi.account.AccountRepository;
import restapi.restapi.account.AccountRole;
import restapi.restapi.account.AccountService;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*@Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Autowired
            AccountRepository accountRepository;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Set<AccountRole> accountSet = new HashSet<>();
                accountSet.add(AccountRole.USER);
                accountSet.add(AccountRole.ADMIN);
                Account kim = Account.builder()
                        .email("oen142@naver.com")
                        .password("kim")
                        .roles(accountSet)
                        .build();
                accountService.saveAccount(kim);
            }
        };
    }*/
}
