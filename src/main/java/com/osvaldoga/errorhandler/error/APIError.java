package com.osvaldoga.errorhandler.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class APIError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private Integer status;
    private String message;
    private String detail;
    private String path;
    private String trace;
    private List<APISubError> errores;
}
