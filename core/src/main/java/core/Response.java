package core;

public class Response {

    private String code;
    private Integer contentLength;
    private String contentType;
    private byte[] body;

    public Response(String code, Integer contentLength, String contentType) {
        this.code = code;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

    public Response(String code, Integer contentLength, String contentType, byte[] body) {
        this.code = code;
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.body = body;
    }

    public Response(String code, Integer contentLength) {
        this.code = code;
        this.contentLength = contentLength;
    }

    public String getCode() {
        return code;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }

}
