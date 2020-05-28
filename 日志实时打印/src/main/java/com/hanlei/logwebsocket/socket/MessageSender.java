package com.hanlei.logwebsocket.socket;

import javax.websocket.Session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @ClassName MessageSender
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/25 2:18 下午
 * @Version 1.0
 */
public class MessageSender {

    private volatile static Map<String, Session> sessionMap = new HashMap<String, Session>();


    static final Logger logger = Logger.getLogger(MessageSender.class.getName());

    public void addSession(String key, Session session) {
        sessionMap.put(key, session);
        MsgThread thread = new MsgThread(session);
        thread.start();

    }


    public void removeSession(String key) {
        sessionMap.remove(key);
    }
}

class MsgThread extends Thread {
    private File logfile = new File("/Users/hanlei/developerTool/ProjectAndFileAddress/channelsoft/yangguang_record/logs/RecordingMigrate/RecordingMigrate.log");
    private Session session;

    File file = null;
    RandomAccessFile randomFile = null;

    public MsgThread(Session session) {
        this.session = session;

        file = new File("/Users/hanlei/developerTool/ProjectAndFileAddress/channelsoft/yangguang_record/logs/RecordingMigrate/RecordingMigrate.log");
        try {
            randomFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            long filePointer = this.logfile.length();
            RandomAccessFile file = new RandomAccessFile(logfile, "r");
            while (true) {
                long fileLength = this.logfile.length();
                if (fileLength < filePointer) {
                    file = new RandomAccessFile(logfile, "r");
                    filePointer = 0;
                }
                if (fileLength > filePointer) {
                    file.seek(filePointer);
                    String line = new String(file.readLine().getBytes("iso8859-1"), "utf-8");
                    while (line != null) {
                        this.session.getAsyncRemote().sendText(line);
                        line = null;
                        filePointer = file.getFilePointer();
                    }

                }
                //sleep(500);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}