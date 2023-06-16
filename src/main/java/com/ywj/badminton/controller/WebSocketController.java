package com.ywj.badminton.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{username}/{nickname}/{uuid}")
@Component
public class WebSocketController {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("nickname") String nickname,@PathParam("uuid") String uuid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("username", username);
        jsonObject.set("nickname", nickname);
        jsonObject.set("uuid", uuid);
        sessionMap.put(JSONUtil.toJsonStr(jsonObject), session);
        log.info("new user connected,username={}, online users count:{}", username, sessionMap.size());
        sendUsers();
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username,@PathParam("nickname") String nickname,@PathParam("uuid") String uuid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("username", username);
        jsonObject.set("nickname", nickname);
        jsonObject.set("uuid", uuid);
        sessionMap.remove(JSONUtil.toJsonStr(jsonObject));
        sendUsers();
        log.info("One connection is closed,session of username={} was removed, online users count:{}", username, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("server received message from username={},message:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}
        if (toUsername.equals("all")){
            sendAllMessage(message);
        }else {
            Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("from", username);  // from 是 zhang
                jsonObject.set("text", text);  // text 同上面的text
                this.sendMessage(jsonObject.toString(), toSession);
                log.info("send to username={}, message:{}", toUsername, jsonObject.toString());
            } else {
                log.info("failed，can not find session of username={}", toUsername);
            }
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error");
        error.printStackTrace();
    }
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("server send message to[{}],message:{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("server failed to send message", e);
        }
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("server send message to[{}],message:{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("server failed to send message", e);
        }
    }
    private void sendUsers() {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            JSONObject user = JSONUtil.parseObj(key);
            jsonObject.set("username", user.getStr("username"));
            jsonObject.set("nickname", user.getStr("nickname"));
            array.add(jsonObject);
        }
        sendAllMessage(JSONUtil.toJsonStr(result));
    }
}
