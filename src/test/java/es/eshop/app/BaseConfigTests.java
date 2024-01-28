package es.eshop.app;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseConfigTests {
}
