package com.agoda.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author <a href="mailto:nazar.bukhari12@gmail.com">Nazar-E-Bukhari</a>
 * @since 8/5/18.
 */
@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS,reason = "Too Many requests")
public class TooManyRequestException extends RuntimeException {
}
