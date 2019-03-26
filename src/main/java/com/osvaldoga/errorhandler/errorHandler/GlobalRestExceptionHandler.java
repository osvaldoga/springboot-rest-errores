package com.osvaldoga.errorhandler.errorHandler;

import com.osvaldoga.errorhandler.error.APIError;
import com.osvaldoga.errorhandler.error.APISubError;
import com.osvaldoga.errorhandler.error.APIValidationError;
import com.osvaldoga.errorhandler.ex.GenericNotFoundException;
import com.osvaldoga.errorhandler.ex.ServerGenericException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Mensaje de error para tipos NOT_FOUND 404.
     *
     * @param ex Tipo de excepcion generica para not found.
     * @return Mensaje generico para indicando el elemento no encontrado.
     */
    @ExceptionHandler({GenericNotFoundException.class})
    public ResponseEntity<APIError> handleNotFoundException(GenericNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(ServerGenericException.class)
    public ResponseEntity<APIError> vehiculoException(ServerGenericException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        APIError apiError = buildErrorTrace(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleAll(Exception ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Error de validacion de un POJO.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        List<APISubError> errores = new LinkedList<>();
        APISubError subError = null;

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();


        for (FieldError e : fieldErrors) {
            subError = APIValidationError
                    .builder()
                    .object(e.getObjectName())
                    .field(e.getField())
                    .rejectedValue(e.getRejectedValue())
                    .message(e.getDefaultMessage())
                    .build();

            errores.add(subError);
        }

        APIError apiError = buildErrorDetalle(httpStatus, ex, request, errores);


        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Metodo o verbo no soportado.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Media Type no soportado.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    // TODO: No entrega resultado esperado, se debe validar.
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        APIError apiError = buildError(httpStatus, ex, request);

        return new ResponseEntity<>(apiError, httpStatus);
    }

    private APIError buildError(HttpStatus httpStatus, Exception ex, WebRequest request) {
        APIError build = APIError.builder()
                .status(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .detail(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI().toString())
                .build();

        return build;
    }

    private APIError buildErrorTrace(HttpStatus httpStatus, Exception ex, WebRequest request) {
        APIError build = APIError.builder()
                .status(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .detail(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI().toString())
                .trace(ExceptionUtils.getStackTrace(ex))
                .build();

        return build;
    }

    private APIError buildErrorDetalle(HttpStatus httpStatus, Exception ex, WebRequest request, List<APISubError> errores) {
        APIError build = APIError.builder()
                .status(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .detail(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI().toString())
                .errores(errores)
                .build();

        return build;
    }


}