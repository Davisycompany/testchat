package com.davisy.service;

import java.util.List;

import com.davisy.entity.ChatParticipants;

public interface ChatParticipantsService {

	public List<ChatParticipants> findAllId(int id);
}
