package sk.fxmachine.rest;

import java.io.IOException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SimpleRestApiAgent implements RestApiAgent {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private final OkHttpClient client;

    public SimpleRestApiAgent() {
        this.client = new OkHttpClient()
                .newBuilder()
                .build();
    }

    @Override
    public String sendGetRequest(String url) throws IOException, RequestException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return executeRequest(request);
    }

    @Override
    public String sendPostRequest(String url, String jsonData) throws IOException, RequestException {
        RequestBody requestBody = RequestBody.create(jsonData, MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return executeRequest(request);
    }

    private String executeRequest(Request request) throws IOException, RequestException {
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        throw new RequestException(response.code());
    }

}
