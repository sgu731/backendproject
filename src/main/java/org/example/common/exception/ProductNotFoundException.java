package org.example.common.exception;

public class ProductNotFoundException
        extends RuntimeException {

    public ProductNotFoundException() {

        super("Product not found");

    }

}