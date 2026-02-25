package com.project.service;

import java.util.List;

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

	@Override
	public List<Member> list() throws Exception {
		return mapper.list();
	}

	@Override
	public Member read(Member member) throws Exception {
		return mapper.read(member);
	}

	@Override
	public int modify(Member member) throws Exception {
		// return mapper.modify(member);
		// 회원권한 수정
		mapper.update(member);
		// 회원권한 삭제
		int userNo = member.getUserNo();
		mapper.deleteAuth(userNo);
		//사용자가 선택한 권한 내용을 가져온다.
		List<MemberAuth> authList = member.getAuthList();
		for (int i = 0; i < authList.size(); i++) {
			MemberAuth memberAuth = authList.get(i);
			String auth = memberAuth.getAuth();

			if (auth == null || auth.trim().length() == 0) 
				continue;
			

			// 변경된 회원권한 추가
			memberAuth.setUserNo(userNo);
			mapper.modifyAuth(memberAuth);
		}

	}
}