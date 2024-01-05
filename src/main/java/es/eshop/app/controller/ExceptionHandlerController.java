package es.eshop.app.controller;

import es.eshop.app.exception.BadRequestException;
import es.eshop.app.exception.ForbbidenException;
import es.eshop.app.exception.NotFoundException;
import es.eshop.app.model.ErrorInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ResponseEntity<ErrorInfoDTO> handleNotFoundException(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorInfoDTO.builder()
                .description(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .url(StringUtils.substring(request.getDescription(false), 4))
                .date(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<ErrorInfoDTO> handleBadRequestException(RuntimeException exception,  WebRequest request) {
        return new ResponseEntity<>(ErrorInfoDTO.builder()
                .description(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .url(StringUtils.substring(request.getDescription(false), 4))
                .date(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ForbbidenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    protected ResponseEntity<ErrorInfoDTO> handleForbbidenException(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(ErrorInfoDTO.builder()
                .description(exception.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .url(StringUtils.substring(request.getDescription(false), 4))
                .date(LocalDateTime.now())
                .build(), HttpStatus.FORBIDDEN);
    }

}
