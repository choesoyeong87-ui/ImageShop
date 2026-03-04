package com.project.service;

import java.util.List;

import com.project.common.domain.Item;
import com.project.domain.Member;
import com.project.domain.UserItem;

public interface UserItemService {
	// 구매 상품 등록 처리 
	public int register(Member member, Item item) throws Exception;
	
	public List<UserItem> list(Member member) throws Exception;

	public UserItem read(UserItem userItem) throws Exception;

	
}
