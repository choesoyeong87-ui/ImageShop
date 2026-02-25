package com.project.mapper;

import java.util.List;

import com.project.domain.Member;
import com.project.domain.MemberAuth;

public interface MemberMapper {

	public int register(Member member) throws Exception;

	public void createAuth(MemberAuth memberAuth) throws Exception;

	public List<Member> list() throws Exception;

	public Member read(Member member) throws Exception;

}
