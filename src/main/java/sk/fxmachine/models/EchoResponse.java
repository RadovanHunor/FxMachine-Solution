package sk.fxmachine.models;

import java.util.Map;
import java.util.Objects;

public class EchoResponse {

    private Map<String, String> args;
    private Map<String, String> headers;
    private Data data;
    private String url;

    public EchoResponse(Map<String, String> args, Map<String, String> headers, String url) {
        this.args = args;
        this.headers = headers;
        this.url = url;
    }

    public Map<String, String> getArgs() {
        return this.args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Data geData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof EchoResponse)) {
            return false;
        }
        EchoResponse echoResponse = (EchoResponse) object;
        return Objects.equals(args, echoResponse.args) &&
                Objects.equals(headers, echoResponse.headers) &&
                Objects.equals(data, echoResponse.data) &&
                Objects.equals(url, echoResponse.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(args, headers, data, url);
    }

}
