package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp rsl = new Resp("", "204 No Content");
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            rsl = new Resp("", "200");
        } else if ("GET".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> qu = queue.get(req.getSourceName());
            String text;
            if (qu != null) {
                text = qu.poll();
                if (text != null) {
                    rsl = new Resp(text, "200");
                }
            }
        }
        return rsl;
    }
}