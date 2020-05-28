package com.hanlei.logwebsocket.springboot;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName WebSocketServer
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/25 3:27 下午
 * @Version 1.0
 */
@ServerEndpoint("/websocket/{id}")
@Component
public class WebSocketServer {

    private Process process;

    private InputStream inputStream;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session) {
        String path = "";
        try {
            path = getPathById(id);
            System.out.println(path);
            // 执行tail -f命令
            process = Runtime.getRuntime().exec("tail -f " + path);
            inputStream = process.getInputStream();
            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogUtil thread = new TailLogUtil(inputStream, session);
            thread.start();
        } catch (IOException e) {
            System.out.println(String.format("read file [%s] error.%s", path, e));
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                System.out.println("close websocket error.:" + e);
            }
        }

        if(process != null){
            process.destroy();
        }
    }

    @OnError
    public void onError(Throwable thr) {
        System.out.println("websocket error." + thr);
    }

    private String getPathById(String id) {
        System.out.println(id);
        return "/Users/hanlei/developerTool/ProjectAndFileAddress/channelsoft/yangguang_record/logs/RecordingMigrate/RecordingMigrate.log";
    }

}