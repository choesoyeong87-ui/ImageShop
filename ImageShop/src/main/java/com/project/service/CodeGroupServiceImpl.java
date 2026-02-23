package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.CodeGroup;
import com.project.mapper.CodeGroupMapper;

@Service
public class CodeGroupServiceImpl implements CodeGroupService {
	@Autowired 
	private CodeGroupMapper mapper;
	
	@Override
	public int register(CodeGroup codeGroup) throws Exception {
		return mapper.create(codeGroup); 
		
	}

	@Override
	public List<CodeGroup> list() throws Exception {
		return mapper.list(); 
	}

	@Override
	public CodeGroup read(CodeGroup codeGroup) throws Exception {
		return mapper.read(codeGroup);
	}
}
