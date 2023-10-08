package com.davisy.service;

import java.util.List;

import com.davisy.entity.Chats;

public interface ChatsService {
	List<Chats> findAllChatsUser(String nameChat);
	Chats findChatNames(String name);
	Chats findById(int id);
	public boolean isFriend(int id);
}
