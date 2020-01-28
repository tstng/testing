package com.techandsolve.lazyload.exceptions;

public class TechnicalException extends Exception {
    public TechnicalException(String mensaje, Throwable e) {
        super(mensaje,e);
    }
    public TechnicalException(String mensaje) {
        super(mensaje);
    }
}
