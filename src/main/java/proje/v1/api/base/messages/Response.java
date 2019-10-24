package proje.v1.api.base.messages;

public class Response<T> {

    private short statusCode;
    private boolean isSuccess;
    private String message;
    private T result;
}
