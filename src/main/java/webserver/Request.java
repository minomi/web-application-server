package webserver;

/**
 * IDE : IntelliJ IDEA
 * Created by minho on 2018. 9. 27..
 */
public class Request {
    private RequestMethod method;
    private String resourcePath;
    private String version;

    public Request(RequestMethod method, String resourcePath, String version) {
        this.method = method;
        this.resourcePath = resourcePath;
        this.version = version;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getVersion() {
        return version;
    }
}
