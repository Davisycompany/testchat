package com.davisy.service;

import java.util.List;

import com.davisy.entity.Messages;

public interface MessagesService {
	public int countMessageUnread(int id);
	
	public List<Object[]>findListMessage(String chatName);
}
