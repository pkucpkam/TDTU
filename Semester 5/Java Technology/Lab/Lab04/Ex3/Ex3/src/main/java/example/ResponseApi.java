package example;

public class ResponseApi {
    private int statusCode;
    private String message;
    private Object data;

    public ResponseApi() {}

    public ResponseApi(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{statusCode=" + statusCode + ", message=" + message + ", data=" + data + "}";
    }




}
