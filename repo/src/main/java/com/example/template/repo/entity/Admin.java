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
@Table(name = "t_admin")
public class Admin {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`name`")
    private String name;

//    @Column(name = "user_name")
    private String userName;

    @Column(name = "`password`")
    private String password;

    @Column(name = "role_codes")
    private String roleCodes;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @Column(name = "is_super")
    private Boolean isSuper;

    @LogicDelete
    @Column(name = "is_del")
    private Boolean isDel;

    @Column(name = "ext")
    private String ext344;
}