package com.project.service;

import java.util.List;

import com.project.common.domain.Item;

public interface ItemService {

	public int register(Item item) throws Exception;

	public List<Item> list() throws Exception;

	public String getPreview(Item item) throws Exception;

	public String getPicture(Item item) throws Exception;

	public Object read(Item item) throws Exception;

}
