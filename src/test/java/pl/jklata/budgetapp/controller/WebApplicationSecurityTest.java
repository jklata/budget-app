package pl.jklata.budgetapp.controller;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.jklata.budgetapp.configuration.SpringSecurityWebAuxTestConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class WebApplicationSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "admin",userDetailsServiceBeanName = "userDetailsService")
    public void givenManagerUser_whenGetFooSalute_thenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(CoreMatchers.containsString("admin")));
    }
}