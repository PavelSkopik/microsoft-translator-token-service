package ut.com.skopik.microsoft.translate.dao;

import com.skopik.microsoft.translate.dao.AzureTokenDao;
import com.skopik.microsoft.translate.dao.TokenDao;
import com.skopik.microsoft.translate.model.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ut.com.skopik.microsoft.translate.core.CoreTestConfiguration;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AzureTokenDao.class})
@ContextConfiguration(classes = {CoreTestConfiguration.class})
public class AzureTokenDaoTest {

    @Autowired
    private TokenDao tokenDao;

    @Test
    public void getToken() {
        Token token = tokenDao.getToken();
        assertNotNull(token);
    }

}
