package com.project.mapper;

import java.util.List;

import com.project.common.domain.Item;
import com.project.domain.Member;
import com.project.domain.UserItem;

public interface UserItemMapper {

	public List<UserItem> list(Member member) throws Exception;

	public UserItem read(UserItem userItem) throws Exception;

	public int create(Member member, Item item)throws Exception;

}
