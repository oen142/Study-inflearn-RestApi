package restapi.restapi.index;


import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import restapi.restapi.common.BaseControllerTest;
import restapi.restapi.common.RestDocsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestDocsConfiguration.class)
public class IndexControllerTest extends BaseControllerTest {



    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/api/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.events").exists());
    }
}
