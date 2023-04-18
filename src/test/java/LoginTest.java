import com.shop.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest(classes = Application.class)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void shouldExistGreetingsOnLoginPageWhenGetRequestToLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print()).
                andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("id=\"loginButton\"")));
    }

    @Test
    public void shouldRedirectToLoginPageWhenTryGetToLockedPage() throws Exception {
        this.mockMvc.perform(get("/checkout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void shouldReturnForbiddenStatusWhenUserHasBadCredentials() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "password.form.input"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-error"));
    }

    @Test
    @Sql(value = {"/shopdbTest_schema.sql","/shopdbTest_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldRedirectToIndexPageWhenUserHasValidCredentials() throws Exception {
        this.mockMvc.perform(formLogin().user("login", "vladunukr02@gmail.com").password("Vv@12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

}
