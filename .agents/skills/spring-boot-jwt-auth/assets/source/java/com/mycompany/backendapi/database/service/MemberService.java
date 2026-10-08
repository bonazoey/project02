package com.mycompany.backendapi.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.mycompany.backendapi.database.dao.MemberDao;
import com.mycompany.backendapi.database.entity.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public void join(Member member) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMpassword(passwordEncoder.encode(member.getMpassword()));
		memberDao.insert(member);
	}

	public String login(Member member) {
		// 주어진 mid로 데이터베이스에서 회원 정보 가져오기
		Member dbMember = memberDao.selectByMid(member.getMid());
		if(dbMember == null) return "wrong-mid";
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean result = passwordEncoder.matches(member.getMpassword(), dbMember.getMpassword());
		if(result) {
			return "success";
		} else {
			return "wrong-mpassword";
		}
	}

	public Member getMember(String mid) {
		Member member = memberDao.selectByMid(mid);
		return member;
	}

	public Member modify(Member member) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMpassword(passwordEncoder.encode(member.getMpassword()));
		
		int rows = memberDao.update(member);
		if(rows == 1) {
			Member dbMember = memberDao.selectByMid(member.getMid());
			return dbMember;
		}		
		return null;
	}

	public boolean remove(String mid) {
		int rows = memberDao.delete(mid);
		if(rows == 1) return true;
		else return false;
	}
	
	
}















