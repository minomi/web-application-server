package webserver;

import util.HttpRequestUtils;

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

    public Request(RequestMethod method, String urlString, Map<String, String> headers, String version) {
        this.method = method;
        this.urlString = urlString;
        this.headers = headers;
        this.version = version;

        if (urlString.contains("?")) {
            this.parameters = HttpRequestUtils.parseQueryString(urlString.split("\\?")[1]);
        }

        this.targetPath = urlString.contains("?")
                ? HttpRequestUtils.targetPathFrom(this.urlString)
                : this.urlString;
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

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
