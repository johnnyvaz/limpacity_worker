package br.com.limpacity.worker.exception;

public class JsonMapperException extends RuntimeException{
    private static final long serialVersionUID = -7809175714521768328L;

    public JsonMapperException(final String message) {
        super(message);
    }
}
