package com.project.mapper;

import java.util.List;

import com.project.common.domain.Item;

public interface ItemMapper {

	public int register(Item item) throws Exception;

	public List<Item> list() throws Exception;

	public String getPreview(Integer itemId)throws Exception;

	public String getPreview(Item item) throws Exception;

	public String getPicture(Item item) throws Exception;

	public Item read(Item item) throws Exception;


}
