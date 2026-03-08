package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.ChargeCoin;
import com.project.domain.Member;
import com.project.domain.PayCoin;
import com.project.mapper.CoinMapper;

@Service
public class CoinServiceImpl implements CoinService {

	@Autowired
	private CoinMapper mapper;

	@Override
	@Transactional
	public int charge(ChargeCoin chargeCoin) throws Exception {
		// 1. 사용자코인충전진행
		int count = mapper.charge(chargeCoin);

		// 2. 코인충전내역서
		if (count != 0)
			return mapper.create(chargeCoin);
		return 0;
	}

	@Override
	public List<ChargeCoin> list(int userNo) throws Exception {
		return mapper.list(userNo);
	}

	

	// 코인 차감
	public int pay(PayCoin payCoin) throws Exception{
		return mapper.pay(payCoin);
	}

	// 구매 내역 등록
	public int createPayHistory(PayCoin payCoin) throws Exception{
		return mapper.createPayHistory(payCoin);
	}

	// 구매 내역 조회
	public List<PayCoin> listPayHistory(Member member) throws Exception{
		return mapper.listPayHistory(member);
	}
}
