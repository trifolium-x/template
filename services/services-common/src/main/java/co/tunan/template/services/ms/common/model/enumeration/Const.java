package co.tunan.template.services.ms.common.model.enumeration;

/**
 * @title: Const
 * @author: trifolium
 * @date: 2023/1/6
 * @modified :
 */
public interface Const {

    String HTTP_HEADER_AUTH_KEY = "Authorization";
    String REDIS_USER_KEY_PREFIX = "auth:user_info:";
    String REDIS_USER_AUTHORITY_PREFIX = "auth:user_authority:";
    String CURRENT_USER_REQUEST_ATTRIBUTE_KEY = "auth:current_user";

    String REDIS_USER_LOGIN_CAPTCHA_PREFIX = "auth:login_captcha:";

    /**
     * 管理员相关缓存过期时间（单位秒）
     */
    long REDIS_USER_CACHE_EXPIRE = 3200L;

    /**
     *  图片验证码Cookie名称
     */
    String CAPTCHA_SESSION_NAME = "Session-Id";

    /**
     * 项目内饰使用的AES加密的密钥,固定的不可泄露，建议使用自定义配置
     */
    String AES_PRIVATE_KEY = "A1STR1F011UM1113";

    /**
     * JWT 私钥，建议使用自定义配置
     */
    String JWT_PRIVATE_KEY = "HMAC0SHA256TR1F011UM7WGY0UK155ME";
}
