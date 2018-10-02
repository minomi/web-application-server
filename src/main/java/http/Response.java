package http;

import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 30..
 */
public class Response {
    private int responseCode;
    private String contentType;
    private int contentLength;
    private Cookies cookies;
    private String redirectUrl;
    private byte[] body;

    public Response() {
        responseCode = 200;
        contentType = "";
        cookies = new Cookies();
        body = new byte[]{};
        redirectUrl = "";
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public byte[] getBody() {
        return body;
    }

    public Response setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Response appendBody(byte[] body) {
        byte[] tmpBody = new byte[this.body.length + body.length];
        System.arraycopy(this.body, 0, tmpBody, 0, this.body.length);
        System.arraycopy(body, 0, tmpBody, this.body.length, body.length);
        this.body = tmpBody;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Status Code : ");
        stringBuilder.append(responseCode);
        stringBuilder.append("\n");
        stringBuilder.append("Content Length : ");
        stringBuilder.append(contentLength);
        stringBuilder.append("\n");
        stringBuilder.append("Content Type : ");
        stringBuilder.append(contentType);
        stringBuilder.append("\n");
        stringBuilder.append("Cookie : ");
        stringBuilder.append(cookies);
        stringBuilder.append("\n");
        stringBuilder.append("redirectUrl : ");
        stringBuilder.append(redirectUrl);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
