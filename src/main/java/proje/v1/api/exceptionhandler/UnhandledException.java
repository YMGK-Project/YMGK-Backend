package proje.v1.api.exceptionhandler;

public class UnhandledException extends RuntimeException {

    public UnhandledException(){
        super("Bir şeyler yanlış gitti tekrar deneyin");
    }
}
