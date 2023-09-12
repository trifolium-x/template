package com.example.template.repo;

/**
 * @title: 分表专用Example，该Example应传入实例，而不是类
 * Created by trifolium on 2021/8/23.
 * @modified :
 */
public class DynamicTableExample extends BaseExample {

    private DynamicTableEntity dynamicTableEntity;

    public DynamicTableExample(DynamicTableEntity dynamicTableEntity) {
        super(dynamicTableEntity.getClass());
        this.dynamicTableEntity = dynamicTableEntity;
    }

    public DynamicTableExample(DynamicTableEntity dynamicTableEntity, boolean exists) {
        super(dynamicTableEntity.getClass(), exists);
        this.dynamicTableEntity = dynamicTableEntity;
    }

    public DynamicTableExample(DynamicTableEntity dynamicTableEntity, boolean exists, boolean notNull) {
        super(dynamicTableEntity.getClass(), exists, notNull);
        this.dynamicTableEntity = dynamicTableEntity;
    }

    @Override
    public String getDynamicTableName() {
        return dynamicTableEntity.getDynamicTableName();
    }
}
