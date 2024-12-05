package be.pxl.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnautherizedException extends RuntimeException{
    public UnautherizedException(String message) {
        super(message);
    }
}
