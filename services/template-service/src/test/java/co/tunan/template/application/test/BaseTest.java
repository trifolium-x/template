package co.tunan.template.application.test;

import co.tunan.template.application.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title: Created by trifolium on 2021/8/30.
 * @author: trifolium
 * @date: 2019/3/12 17:41
 * @modified :
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public abstract class BaseTest {
}
