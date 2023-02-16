package sk.fxmachine.rest;

import java.io.IOException;

public interface RestApiAgent {

    String sendGetRequest(String url) throws IOException, RequestException;

    String sendPostRequest(String url, String jsonData) throws IOException, RequestException;

}
