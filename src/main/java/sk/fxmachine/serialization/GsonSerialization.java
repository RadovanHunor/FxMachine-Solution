package sk.fxmachine.serialization;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class GsonSerialization implements Serialization {

    private final Gson gson;

    public GsonSerialization() {
        this.gson = new Gson();
    }

    @Override
    public String serialize(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

}
