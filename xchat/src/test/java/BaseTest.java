import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by :Guozhihua
 * Date： 2017/6/27.
 */

@Transactional
@TransactionConfiguration(transactionManager ="transactionManager", defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/spring-config.xml","classpath:/config/spring-mybatis.xml"})
public class BaseTest {
}
