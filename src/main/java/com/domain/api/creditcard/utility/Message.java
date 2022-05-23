package com.domain.api.creditcard.utility;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Message {
	
	private String id;
	
	private String code;
	
	private String title;
	
	private String details;
	
	public Message() {
		
	}

	public Message(String id, String code, String title, String details) {
		
		Objects.requireNonNull(code, "Message Code cannot be null");
		Objects.requireNonNull(title, "Message Title cannot be null");
		
		this.id = id;
		this.code = code;
		this.title = title;
		this.details = details;
	}
	
	
	public Message(String code, String title, String details) {
		
		Objects.requireNonNull(code, "Message Code cannot be null");
		Objects.requireNonNull(title, "Message Title cannot be null");
		
		this.id = UUID.randomUUID().toString();
		this.code = code;
		this.title = title;
		this.details = details;
	}
	
	public static Message create(String id, String code, String title, String details) {
		return  new Message(id, code, title, details);
	}
	
	public static Message create(String code, String title, String details) {
		return  new Message(code, title, details);
	}
	
	public static Message create(String code, String title) {
		return  new Message(code, title, null);
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", code=" + code + ", title=" + title + ", details=" + details + "]";
	}
}
