package sk.fxmachine.models;

import java.util.Objects;

public class Data {

    private String title;
    private String body;

    public Data(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Data)) {
            return false;
        }
        Data message = (Data) object;
        return Objects.equals(title, message.title) &&
                Objects.equals(body, message.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body);
    }
}