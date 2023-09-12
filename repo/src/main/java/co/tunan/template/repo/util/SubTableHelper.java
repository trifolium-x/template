package co.tunan.template.repo.util;

import cn.hutool.core.util.NumberUtil;

/**
 * Created by trifolium on 2021/8/23.
 */
public class SubTableHelper {

    public static int getOrderTableNumber(Long userId) {

        String number = String.valueOf(userId);

        return NumberUtil.parseInt(number.substring(number.length() - 1));
    }

}
