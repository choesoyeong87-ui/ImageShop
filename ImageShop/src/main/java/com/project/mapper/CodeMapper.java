package com.project.mapper;

import java.util.List;

import com.project.common.domain.CodeLabelValue;

public interface CodeMapper {

	// 그룹코드 목록 조회 
	public List<CodeLabelValue> getCodeGroupList() throws Exception; 
	// 등록 처리 

	public List<CodeLabelValue> getCodeList(String groupCode)throws Exception; 
	
	
	// 목록 페이지 
	
	
	// 상세 페이지 
	
	
	// 삭제 처리 
	

	// 수정 처리 
	
}
