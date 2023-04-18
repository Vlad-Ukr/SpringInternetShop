package controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
@Sql(value = {"/shopdbTest_schema.sql","/shopdbTest_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldExpectStatus200AndProductWhenGetRequestWithValidFilter() throws Exception {
        this.mockMvc.perform(get("/category")
                        .param("sorting", "0")
                        .param("limit", "3")
                        .param("nameLike", "b")
                        .param("manufacturer.chekbutton", "DG", "LaCosta")
                        .param("category.chekbutton", "Casual", "Sport")
                        .param("minPrice", "0")
                        .param("maxPrice", "1500"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("//*[@id=16]").exists());
    }

    @Test
    public void shouldExpectEmptyProductListWhenGetRequestWithInvalidFilter() throws Exception{
        this.mockMvc.perform(get("/category")
                .param("sorting", "0")
                .param("limit", "3")
                .param("nameLike", "asf")
                .param("manufacturer.chekbutton", "Adidas")
                .param("category.chekbutton", "Casual")
                .param("minPrice", "0")
                .param("maxPrice", "1500"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("//*[@id=\"emptyList\"]").exists());

    }
    @Test
    public void shouldExpectProductNodesWhenGetRequestWithInvalidSortingFilter() throws Exception {
        this.mockMvc.perform(get("/category")
                        .param("sorting", "sgd")
                        .param("limit", "3")
                        .param("nameLike", "")
                        .param("minPrice", "0")
                        .param("maxPrice", "1500")
                        .param("page","1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("//*[@id=\"14\"]").exists())
                .andExpect(xpath("//*[@id=\"13\"]").exists())
                .andExpect(xpath("//*[@id=\"6\"]").exists());
    }
}
