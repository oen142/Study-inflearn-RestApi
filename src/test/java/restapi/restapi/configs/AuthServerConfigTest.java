package restapi.restapi.configs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import restapi.restapi.account.Account;
import restapi.restapi.account.AccountRole;
import restapi.restapi.account.AccountService;
import restapi.restapi.common.BaseControllerTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        Set<AccountRole> accountRoleSet = new HashSet<>();
        accountRoleSet.add(AccountRole.ADMIN);
        accountRoleSet.add(AccountRole.USER);

        String username = "oen142@naver.com";
        String password = "kim";
        Account kim = Account.builder()
                .email(username)
                .password(password)
                .roles(accountRoleSet).build();
        accountService.saveAccount(kim);

        String clientId = "myApp";
        String clientSecret = "pass";

        mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}