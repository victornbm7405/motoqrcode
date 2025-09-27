// src/main/java/br/com/fiap/mottu/motoqrcode/config/GlobalModelAttributes.java
package br.com.fiap.mottu.motoqrcode.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("requestPath")
    public String requestPath(HttpServletRequest request) {
        return (request != null) ? request.getRequestURI() : "";
    }
}
