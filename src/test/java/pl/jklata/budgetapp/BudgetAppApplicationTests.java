package pl.jklata.budgetapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = BudgetAppApplication.class)
@ActiveProfiles("test")
public class BudgetAppApplicationTests {

    @Test
    public void contextLoads() {
    }

}

