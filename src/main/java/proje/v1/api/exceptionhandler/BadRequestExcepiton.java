package proje.v1.api.exceptionhandler;

public class BadRequestExcepiton extends RuntimeException {

    public BadRequestExcepiton(String message){
        super(message);
    }
}
