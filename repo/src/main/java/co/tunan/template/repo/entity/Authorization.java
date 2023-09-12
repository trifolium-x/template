package co.tunan.template.repo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "t_authorization")
public class Authorization {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "`name`")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "authority")
    private String authority;

    @Column(name = "`type`")
    private Integer type;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private Date createTime;

    @LogicDelete
    @Column(name = "is_del")
    private Boolean isDel;

}