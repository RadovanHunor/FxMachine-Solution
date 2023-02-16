package sk.fxmachine.serialization;

public interface Serialization {
    public String serialize(Object object);

    public <T> T deserialize(String json, Class<T> clazz);
}
