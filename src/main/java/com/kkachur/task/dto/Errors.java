package com.kkachur.task.dto;

import java.util.ArrayList;
import java.util.List;

public class Errors extends ResponseEntity {

    private List<Error> errors;

    public Errors() {
        this.errors = new ArrayList<>();
    }

    public void addError(Error error) {
        this.errors.add(error);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }


    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
