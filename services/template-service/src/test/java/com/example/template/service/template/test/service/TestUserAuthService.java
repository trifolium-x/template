package com.example.template.service.template.test.service;

import com.example.template.service.template.service.UserAuthService;
import com.example.template.service.template.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @title: TestUserAuthService
 * @author: trifolium.wang
 * @date: 2023/12/7
 * @modified:
 */
public class TestUserAuthService extends BaseTest {

    @Inject
    private UserAuthService userAuthService;

    @Test
    public void testBean() {

        Assert.assertNotNull(userAuthService);
    }
}
