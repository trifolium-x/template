package co.tunan.template.repo;

import lombok.Data;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @title: 分表专用Entity基类, 只支持传入对象的查询或写入，如updateXXX insertXXX 而不是selectIdXxx等
 * Created by trifolium on 2021/8/23.
 */
@Data
public abstract class DynamicTableEntity implements IDynamicTableName {

    @Transient
    private String tableName;

    @Transient
    @Override
    public String getDynamicTableName() {
        if (tableName == null) {

            return this.getClass().getAnnotation(Table.class).name();
        }

        return tableName;
    }
}
