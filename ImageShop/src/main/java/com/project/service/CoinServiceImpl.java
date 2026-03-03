package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.ChargeCoin;
import com.project.mapper.CoinMapper;

@Service
public class CoinServiceImpl implements CoinService {
	
	@Autowired 
	private CoinMapper mapper;

	@Override
	public int charge(ChargeCoin chargeCoin) throws Exception {
		return mapper.charge(chargeCoin);
	}

	@Override
	public List<ChargeCoin> list(int userNo) throws Exception {
		return mapper.list(userNo);
	} 
}
