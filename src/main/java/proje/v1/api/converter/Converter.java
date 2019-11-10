package proje.v1.api.converter;

public interface Converter<Object,Dto> {

    Dto convert(Object object);
}
