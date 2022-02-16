package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] contents = content.split(System.lineSeparator());
        String[] reqParam = contents[0].split(" ");
        String requestType = reqParam[0];
        String mode = reqParam[1].split("/")[1];
        String name = reqParam[1].split("/")[2];
        String par = "";
        if ("POST".equals(requestType)) {
            par = contents[7];
        }
        if ("GET".equals(requestType) && "topic".equals(mode)) {
            par = reqParam[1].split("/")[3];
        }
        return new Req(requestType, mode, name, par);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}