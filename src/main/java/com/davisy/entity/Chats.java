package com.davisy.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chats")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name_chats;

	@OneToMany(mappedBy = "chats")
//	@JoinColumn(name = "id")
	List<Messages> messages;
	boolean isFriend;
	boolean status;
}
