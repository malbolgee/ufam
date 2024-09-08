package com.ufam.android.security.exceptions;

import java.lang.Exception;
import java.lang.Throwable;

/**
 * Thrown an exception in case the API is unavailable
 */
public class ApiUnavailableException extends Exception {

    /**
     * Constructs an ApiUnavailableException with a message
     *
     * @param message an error message of the exception cause
     */
    public ApiUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructs an ApiUnavailableException with a message and a Throwable object
     *
     * @param message the detailed message of the exception cause
     * @param error   a Throwable object of the error cause
     */
    public ApiUnavailableException(String message, Throwable error) {
        super(message, error);
    }
}