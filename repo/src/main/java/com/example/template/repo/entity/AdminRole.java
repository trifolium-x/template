package com.example.template.repo.entity;

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
@Table(name = "t_admin_role")
public class AdminRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`type`")
    private String type;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "is_sys_role")
    private Boolean isSysRole;

    @LogicDelete
    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "description")
    private String description;
}