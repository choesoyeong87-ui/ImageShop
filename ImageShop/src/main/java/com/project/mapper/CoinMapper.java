package com.project.mapper;

import java.util.List;

import com.project.domain.ChargeCoin;

public interface CoinMapper {

	public int charge(ChargeCoin chargeCoin) throws Exception;

	public List<ChargeCoin> list(int userNo) throws Exception;

}
