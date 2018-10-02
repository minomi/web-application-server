package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import http.Cookies;
import http.Request;
import http.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.*;

public class HttpRequestUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);
    /**
     * @param queryString은
     *            URL에서 ? 이후에 전달되는 field1=value1&field2=value2 형식임
     * @return
     */
    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }

    /**
     * @param 쿠키
     *            값은 name1=value1; name2=value2 형식임
     * @return
     */
    public static Map<String, String> parseCookies(String cookies) {
        return parseValues(cookies, ";");
    }

    private static Map<String, String> parseValues(String values, String separator) {
        if (Strings.isNullOrEmpty(values)) {
            return Maps.newHashMap();
        }

        String[] tokens = values.split(separator);
        return Arrays.stream(tokens).map(t -> getKeyValue(t, "=")).filter(p -> p != null)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    static Pair getKeyValue(String keyValue, String regex) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split(regex);
        if (tokens.length != 2) {
            return null;
        }

        return new Pair(tokens[0], tokens[1]);
    }

    public static Pair parseHeader(String header) {
        return getKeyValue(header, ": ");
    }

    public static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key.trim();
            this.value = value.trim();
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Pair [key=" + key + ", value=" + value + "]";
        }
    }

    public static Request parseRequestFrom(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        List<String> headerLines = getHeaderLines(bufferedReader);
        String[] splitFirstLine = headerLines.get(0).split(" ");
        RequestMethod method = RequestMethod.valueOf(splitFirstLine[0]);

        String urlString = splitFirstLine[1];
        String version = splitFirstLine[2];

        if (urlString.equals("/")) {
            urlString = WebServer.WELCOME_FILE;
        }

        Map<String, String> headers = new HashMap<>();
        headerLines.subList(1, headerLines.size())
                .forEach(headerLine -> {
                    Pair pair = HttpRequestUtils.parseHeader(headerLine);
                    headers.put(pair.getKey(), pair.getValue());
        });

        Request request = new Request().setMethod(method)
                                        .setUrlString(urlString)
                                        .setHeaders(headers)
                                        .setVersion(version);

        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            Map<String, String> params = parseQueryString(IOUtils.readData(bufferedReader, contentLength));
            request.setParameters(params);
        }

        if (headers.containsKey("Cookie")) {
            Cookies cookies = new Cookies(parseCookies(headers.get("Cookie")));
            request.setCookies(cookies);
        }

        String accept = headers.getOrDefault("Accept", "text/html");
        request.setAccept(accept);

        return request;
    }

    private static List<String> getHeaderLines(BufferedReader bufferedReader) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        String temp;
        while (!(temp = bufferedReader.readLine()).equals("")) {
            lines.add(temp);
        }
        return lines;
    }

    public static String parseContentType(Request request) {
        String charSet = " charset=UTF-8";
        if (request.getAccept().startsWith("text/css")) {
            return "text/css;" + charSet;
        }
        return "text/html;" + charSet;
    }

}
