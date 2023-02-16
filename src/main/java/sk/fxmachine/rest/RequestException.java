package sk.fxmachine.rest;

public class RequestException extends Exception {
    public RequestException(int code) {
        super("Unexpected response code: " + code);
    }
}
