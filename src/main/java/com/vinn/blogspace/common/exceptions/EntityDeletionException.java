package com.vinn.blogspace.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityDeletionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String entityName;
    private final String associationName;

    public EntityDeletionException(String entityName, String associationName) {
        super(String.format("Cannot delete %s because it is associated with %s.", entityName, associationName));
        this.entityName = entityName;
        this.associationName = associationName;
    }
}
