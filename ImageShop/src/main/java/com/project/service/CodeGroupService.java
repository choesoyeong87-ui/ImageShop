package com.project.service;

import java.util.List;

import com.project.domain.CodeGroup;

public interface CodeGroupService {

	//코드그룹 등록 처리 
	public int register(CodeGroup codeGroup) throws Exception; 
	
	//코드그룹 목록 페이지 요청
	public List<CodeGroup> list() throws Exception; 
	//코드그룹 상세 페이지 
	public CodeGroup read(CodeGroup codeGroup) throws Exception; 
	//코드그룹 삭제 처리 
	public int remove(CodeGroup codeGroup) throws Exception; 
	// 코드그룹 수정 처리 
	public int modify(CodeGroup codeGroup) throws Exception;
	} 
