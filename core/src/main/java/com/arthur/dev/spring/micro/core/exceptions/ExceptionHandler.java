package com.arthur.dev.spring.micro.core.exceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
public class ExceptionHandler {
    public static ResponseEntity handle(Exception ex) {
        if(ex instanceof HttpStatusCodeException)
            return
                ResponseEntity
                    .status(((HttpStatusCodeException) ex).getStatusCode())
                    .body(((HttpStatusCodeException) ex).getStatusText());

        log.error("INTERNAL ERROR", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno no servidor");
    }
}
