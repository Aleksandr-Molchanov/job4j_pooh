package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp("", "204");
        if ("POST".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map =
                    topics.get(req.getSourceName());
            if (map != null) {
                for (String k : map.keySet()) {
                    map.get(k).add(req.getParam());
                }
            }
            rsl = new Resp(req.getParam(), "200");
        } else if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map =
                    topics.get(req.getSourceName());
            map.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = map.get(req.getParam()).poll();
            if (text != null) {
                rsl = new Resp(text, "200");
            }
        }
        return rsl;
    }
}