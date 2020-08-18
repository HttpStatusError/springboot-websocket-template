package com.zqskate.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zqskate.config.WebSocketServer;

/**
 * 模拟服务器有新消息推送
 * 
 * @author soho.chan
 * @date 2020-08-18 15:25
 */
@RestController
public class MessageController {

	@Resource
	private WebSocketServer webSocketServer;
	
	@GetMapping("/send")
	@ResponseBody
	public String sendMessage() {
		webSocketServer.sendMessage("");
		return "success";
	}
	
}
