package co.tunan.template.repo;


import tk.mybatis.mapper.entity.Example;

/**
 * Created by trifolium on 2021/8/23.
 *
 * @version: 1.0
 * @modified :
 */
public class BaseExample extends Example {
    public BaseExample(Class<?> entityClass) {
        super(entityClass);
    }

    public BaseExample(Class<?> entityClass, boolean exists) {
        super(entityClass, exists);
    }

    public BaseExample(Class<?> entityClass, boolean exists, boolean notNull) {
        super(entityClass, exists, notNull);
    }
}
