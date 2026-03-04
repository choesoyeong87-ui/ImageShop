package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.domain.Item;
import com.project.domain.Member;
import com.project.domain.PayCoin;
import com.project.domain.UserItem;
import com.project.exception.NotEnoughCoinException;
import com.project.mapper.CoinMapper;
import com.project.mapper.UserItemMapper;

@Service
public class UserItemServiceImpl implements UserItemService {
	@Autowired
	private UserItemMapper mapper;

	@Autowired
	private CoinMapper coinMapper;

	@Override
	public List<UserItem> list(Member member) throws Exception {
		return mapper.list(member);
	}

	@Override
	public UserItem read(UserItem userItem) throws Exception {
		return mapper.read(userItem);
	}

	// 구매 상품 등록 처리
	@Transactional
	@Override
	public int register(Member member, Item item) throws Exception {
		//사용자정보번호
		int userNo = member.getUserNo();
		int coin = member.getCoin();
		
		//구입한 물건정보번호
		int itemId = item.getItemId();
		int price = item.getPrice();
		String itemName = item.getItemName();
		//장바구니 등록(구입한사용자, 구입한물건정보)
		UserItem userItem = new UserItem();
		userItem.setUserNo(userNo);
		userItem.setItemId(itemId);
		userItem.setItemName(itemName);
		
		if(coin < price) {
			throw new NotEnoughCoinException("코인이 부족합니다");
		}
		//구입한 물건에대한 코인지급
		PayCoin payCoin = new PayCoin();
		payCoin.setUserNo(userNo);
		payCoin.setItemId(itemId);
		payCoin.setAmount(price);
		payCoin.setItemName(itemName);
		coinMapper.pay(payCoin); // 코인 지급
		coinMapper.createPayHistory(payCoin); // 구매 내역 등록
		//장바구니 생성
		return mapper.create(member,item);
	}
}