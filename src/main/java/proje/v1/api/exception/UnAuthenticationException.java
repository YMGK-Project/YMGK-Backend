package proje.v1.api.exception;

public class UnAuthenticationException extends RuntimeException {

    public UnAuthenticationException(String message){
        super(message);
    }
}
