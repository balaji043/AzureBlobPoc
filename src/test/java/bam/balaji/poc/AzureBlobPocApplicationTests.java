package bam.balaji.poc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AzureBlobPocApplicationTests {

    @Test
    public void contextLoads() {
        int expected = 0, actual = 0;
        Assert.assertEquals(expected, actual);
    }

}
