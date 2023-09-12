package com.example.template.repo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * mb_auth_role_idx
 */
@Getter
@Setter
@Table(name = "t_auth_role_idx")
public class AuthRoleIdx {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * roleCode
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * authCode
     */
    @Column(name = "auth_code")
    private String authCode;

    /**
     * createBy
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * createTime
     */
    @Column(name = "create_time")
    private Date createTime;
}