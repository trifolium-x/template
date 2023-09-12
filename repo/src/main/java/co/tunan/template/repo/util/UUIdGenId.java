package co.tunan.template.repo.util;

import tk.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * Created by trifolium on 2021/8/23.
 */
public class UUIdGenId implements GenId<String> {

    @Override
    public String genId(String s, String s1) {
        return UUID.randomUUID().toString().replace("-","");
    }
}
