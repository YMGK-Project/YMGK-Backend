package proje.v1.api.exception;

public class UnhandledException extends RuntimeException {

    public UnhandledException(){
        super("Bir şeyler yanlış gitti tekrar deneyin");
    }
}
