package proje.v1.api.exceptionhandler.base;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message){
        super(message);
    }
}
