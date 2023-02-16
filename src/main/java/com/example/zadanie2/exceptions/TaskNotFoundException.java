package com.example.zadanie2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Task")
public class TaskNotFoundException extends RuntimeException {
}
