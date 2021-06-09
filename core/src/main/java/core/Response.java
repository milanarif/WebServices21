package core;

public class Response {

    private Integer code;
    private Integer contentLength;
    private String contentType;
    private String body;

    public Response(Integer code, Integer contentLength, String contentType) {
        this.code = code;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

    public Response(Integer code, Integer contentLength, String contentType, String body) {
        this.code = code;
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.body = body;
    }

    public Response(Integer code, Integer contentLength) {
        this.code = code;
        this.contentLength = contentLength;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", contentLength=" + contentLength +
                ", contentType='" + contentType + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
