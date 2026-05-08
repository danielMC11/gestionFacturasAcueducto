package com.altamira.users.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(String sagaId) {
        SseEmitter emitter = new SseEmitter(60_000L); // Timeout de 1 min
        emitters.put(sagaId, emitter);

        emitter.onCompletion(() -> emitters.remove(sagaId));
        emitter.onTimeout(() -> emitters.remove(sagaId));
        return emitter;
    }

    public void sendNotification(String sagaId, String eventName, String message) {
        SseEmitter emitter = emitters.get(sagaId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(message));
                emitter.complete();
            } catch (IOException e) {
                emitters.remove(sagaId);
            }
        }
    }
}