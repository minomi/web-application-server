package http;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 27..
 */
public class Request {
    private RequestMethod method;
    private String urlString;
    private String version;
    private String targetPath;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private Cookies cookies;
    private String accept;

    public Request() {
        urlString = "";
        version = "";
        targetPath = "";
        headers = new HashMap<>();
        parameters = new HashMap<>();
        method = RequestMethod.GET;
        cookies = new Cookies();
        accept = "";
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getVersion() {
        return version;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Request setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public Request setUrlString(String urlString) {
        this.urlString = urlString;
        if (urlString.contains("?")) {
            this.parameters = HttpRequestUtils.parseQueryString(urlString.split("\\?")[1]);
        }
        return this;
    }

    public Request setVersion(String version) {
        this.version = version;
        return this;
    }

    public Request setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public String getAccept() {
        return accept;
    }

    public Request setAccept(String accept) {
        this.accept = accept;
        return this;
    }

    @Override
    public String toString() {
        return "[" +
                "Method : " + method + "\n" +
                "UrlString : " + urlString + "\n" +
                "Parameters : " + parameters + "\n" +
                "Cookies : " + cookies + "\n" +
                "Accept : " + accept + "\n" +
                "]";
    }
}
