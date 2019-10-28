package proje.v1.api.exceptionhandler;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
