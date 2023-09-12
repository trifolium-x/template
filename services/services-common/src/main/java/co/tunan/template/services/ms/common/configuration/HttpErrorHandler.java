package co.tunan.template.services.ms.common.configuration;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: HttpErrorHandler
 * @author: trifolium
 * @date: 2022/1/27
 * @modified :
 */
@RestController
public class HttpErrorHandler implements ErrorController {


    private final ServerProperties serverProperties;

    public HttpErrorHandler(ServerProperties serverProperties) {

        this.serverProperties = serverProperties;
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public String errorJson(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);

        return "error: " + status.value();
    }

}

