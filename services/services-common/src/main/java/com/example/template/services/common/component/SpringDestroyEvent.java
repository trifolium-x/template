package com.example.template.services.common.component;

import com.example.template.common.util.ThreadPoolUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @title: 公共资源关闭
 * 建议所有公共资源关闭，在这里处理
 * @author: trifolium
 * @date: 2023/3/9
 * @modified :
 */
@Component
public class SpringDestroyEvent implements DisposableBean {
    @Override
    public void destroy() {

        ThreadPoolUtil.shutDownNow();
    }

}
