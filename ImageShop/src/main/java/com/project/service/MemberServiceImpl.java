package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.domain.Member;
import com.project.domain.MemberAuth;
import com.project.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired 
	private MemberMapper mapper;
	
	@Transactional
	@Override
	public int register(Member member) throws Exception {
		// TODO Auto-generated method stub
		int count = mapper.register(member);
		if (count != 0) {
			MemberAuth memberAuth = new MemberAuth();
			memberAuth.setAuth("ROLE_MEMBER");
			mapper.createAuth(memberAuth); 
		}
		return count;
	}
}
