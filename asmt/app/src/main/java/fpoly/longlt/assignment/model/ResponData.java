package fpoly.longlt.assignment.model;

public class ResponData<T> {
    private int status;
    private String messenger;
    private T data;
    private String token;

    public ResponData() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponData(int status, String messenger, T data, String token) {
        this.status = status;
        this.messenger = messenger;
        this.data = data;
        this.token = token;
    }
}
