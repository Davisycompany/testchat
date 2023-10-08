//package com.davisy.controller.chat;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.davisy.config.JwtTokenUtil;
//import com.davisy.controller.FollowController;
//import com.davisy.entity.ChatParticipants;
//import com.davisy.entity.Chats;
//import com.davisy.entity.DataFollows;
//import com.davisy.entity.Follower;
//import com.davisy.entity.User;
//import com.davisy.model.chat.UserModel;
//import com.davisy.model.chat.UserModel.MessageType;
//import com.davisy.service.ChatParticipantsService;
//import com.davisy.service.impl.ChatsServiceImpl;
//import com.davisy.service.impl.FollowServiceImpl;
//import com.davisy.service.impl.MessagesServiceImpl;
//import com.davisy.service.impl.PostImagesServiceImpl;
//import com.davisy.service.impl.PostServiceImpl;
//import com.davisy.service.impl.UserServiceImpl;
//import com.davisy.storage.chat.UserStorage;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@Component
//public class UserChatController {
//	@Autowired
//	JwtTokenUtil jwtTokenUtil;
//	@Autowired
//	UserServiceImpl userServiceImpl;
//	@Autowired
//	FollowServiceImpl followServiceImpl;
//	@Autowired
//	PostImagesServiceImpl postImagesServiceImpl;
//	@Autowired
//	PostServiceImpl postServiceImpl;
//	@Autowired
//	ChatsServiceImpl chatsServiceImpl;
//	@Autowired
//	ChatParticipantsService chatParticipantsService;
//	@Autowired
//	MessagesServiceImpl messagesServiceImpl;
//	public static User staticUser = new User();
//
//	long millis = System.currentTimeMillis();
//	java.sql.Date day = new java.sql.Date(millis);
//
//	@GetMapping("/v1/user/registrationchat")
//	public ResponseEntity<User> register(HttpServletRequest request) {
//		try {
//			String email = jwtTokenUtil.getEmailFromHeader(request);
//			User user = userServiceImpl.findByEmail(email);
//			this.staticUser=user;
//			List<Follower> followers = followServiceImpl.findAll();
//			List<ChatParticipants> listChatParticipants = chatParticipantsService.findAllId(user.getUser_id());
//			List<DataFollows> dataFollows = new ArrayList<>();
//			List<Integer> checkContains = new ArrayList<>();
//			dataFollows.addAll(reloadDataFriends(followers, user));
//			user.setOnline_last_date(null);
//			userServiceImpl.update(user);
//			String type="LEAVE";
//			for (DataFollows data : dataFollows) {
//				UserModel userModel = new UserModel();
//				User us = userServiceImpl.findById(data.getUser_id());
//				if (us.getOnline_last_date() != null) {
//					checkContains.add(us.getUser_id());
//					type="LEAVE";
//				}else {
//					type="JOIN";
//				}
//				UserStorage.getInstance().setUser(us.getUser_id(), userModel(us, user.getUser_id(), type,true,true));
//			}
////			for (ChatParticipants participants : listChatParticipants) {
////				ChatParticipants.Primary primary = participants.getPrimary();
////				User us = userServiceImpl.findById(primary.getUser_id());
////				Chats chats = chatsServiceImpl.findById(primary.getChat_id());
////				boolean checkFriend =false;
////				boolean checkStatus=false;
////				if(chats !=null) {
////					checkFriend=true;
////					checkStatus=true;
////				}
////				if (!checkContains.contains(primary.getUser_id()) && us.getOnline_last_date() != null) {
////					UserStorage.getInstance().setUser(us.getUser_id(), userModel(us, user.getUser_id(), "LEAVE",checkFriend,checkStatus));
////				}
////			}
//			UserStorage.getInstance().setUser(user.getUser_id(), userModel(user, user.getUser_id(), "JOIN",true,true));
//
//			return ResponseEntity.ok().body(user);
//		} catch (Exception e) {
//			System.out.println("Error register in usercontroller: " + e);
//			return ResponseEntity.badRequest().build();
//		}
//
//	}
//
//	@MessageMapping("/fetchAllUsers")
//	@SendTo("/topic/public")
//	public HashMap<Integer, UserModel> fetchAll() {
//		return UserStorage.getInstance().getUsers();
//	}
//
//	public UserModel userModel(User us, int user_id, String type,boolean check,boolean status) {
//		UserModel userModel = new UserModel();
//		if (type.equalsIgnoreCase("LEAVE")) {
//			userModel.setType(MessageType.LEAVE);
//		} else {
//			userModel.setType(MessageType.JOIN);
//		}
//		userModel.setUser_id(us.getUser_id());
//		userModel.setUsername(us.getUsername());
//		userModel.setFullname(us.getFullname());
//		userModel.setEmail(us.getEmail());
//		userModel.setAvatar(us.getAvatar());
//		userModel.setMessageUnRead(messagesServiceImpl.countMessageUnread(us.getUser_id()));
//		userModel.setLastMessage(lastMeassage(String.valueOf(user_id), String.valueOf(us.getUser_id())));
//		userModel.setOnline(us.getOnline_last_date());
//		userModel.setFriend(check);
//		userModel.setStatus(status);
//		return userModel;
//	}
//
//	public String lastMeassage(String fromLogin, String toUser) {
//		try {
//			String message = "";
//			String chatName = "";
//			if (chatsServiceImpl.findChatNames(fromLogin + toUser) == null) {
//				chatName = toUser + fromLogin;
//			} else {
//				chatName = fromLogin + toUser;
//			}
//			if (!"".equals(chatName) && chatsServiceImpl.findChatNames(chatName) != null) {
//				List<Object[]> listMessage = messagesServiceImpl.findListMessage(chatName);
//				if (listMessage.size() > 0) {
//					message = String.valueOf(listMessage.get(listMessage.size() - 1)[1]);
//				}
//			}
//			return message;
//		} catch (Exception e) {
//			System.out.println("error1: " + e);
//			throw e;
//		}
//	}
//
//	public List<DataFollows> reloadDataFriends(List<Follower> followers, User user) {
//		List<DataFollows> dataFollows = new ArrayList<>();
//		for (Follower all : followers) {
//			Follower.Pk pk1 = all.getPk();
//			if (pk1.getUser_id() == user.getUser_id()
//					&& followServiceImpl.checkFriend(pk1.getFollower_id(), pk1.getUser_id())) {
//				dataFollows.add(data(pk1.getFollower_id()));
//			}
//		}
//		return dataFollows;
//	}
//
//	public DataFollows data(int id) {
//		DataFollows data = new DataFollows();
//		User us = userServiceImpl.findById(id);
//		int countPost = postServiceImpl.countPost(id);
//		int countFollower = followServiceImpl.countFollowers(id);
//		int countImg = postImagesServiceImpl.countPostImages(id);
//		data.setUser_id(id);
//		data.setThumb(us.getThumb());
//		data.setAvatar(us.getAvatar());
//		data.setMark(us.getMark());
//		data.setFullname(us.getFullname());
//		data.setIntro(us.getIntro());
//		data.setCountPost(countPost);
//		data.setCountFollower(countFollower);
//		data.setCountImg(countImg);
//		return data;
//	}
//}
