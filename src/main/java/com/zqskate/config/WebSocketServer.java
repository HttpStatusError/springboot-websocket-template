package com.zqskate.config;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * webSocket 接口
 * 
 * @author soho.chan
 * @date 2020-08-18 15:28
 */
@Component
@ServerEndpoint("/websocket")
public class WebSocketServer {
	
	private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
	
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
	
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		log.info("【websocket消息】 有新的连接，总数{}", webSocketSet.size());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
		log.info("【websocket消息】 连接断开，总数{}", webSocketSet.size());
	}

	/**
	 * 接收客户端消息
	 * 
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message) {
		log.info("【websocket消息】 收到客户端发来的消息：{}", message);
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		log.info("【websocket消息】 发送消息：{}", message);
		for (WebSocketServer webSocket : webSocketSet) {
			try {
				webSocket.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
