package com.example.bank_kill.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class RecordDto implements Serializable {
    private final Integer userId;

    private final Integer amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date overdueDate;

    private final Integer overdueTime;

    private final Boolean isblack;
}
