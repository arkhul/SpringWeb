package com.crud.tasks.domain;

import com.crud.tasks.config.AdminConfig;
import lombok.*;

@Getter
@Builder
public class Mail {
    private final String mailTo;
    private final String message;
    private final String subject;
    private final String toCc;
}