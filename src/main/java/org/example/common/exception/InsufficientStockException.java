package org.example.common.exception;

public class InsufficientStockException
        extends RuntimeException {

    public InsufficientStockException() {

        super("Insufficient Stock");

    }

}