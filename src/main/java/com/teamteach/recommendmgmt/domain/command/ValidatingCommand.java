package com.teamteach.recommendmgmt.domain.command;

import javax.validation.*;
import java.util.Set;

public abstract class ValidatingCommand <T> {
    private Validator validator;
    protected boolean valid;

    public ValidatingCommand() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
