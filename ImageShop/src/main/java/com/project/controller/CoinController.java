package com.project.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.security.domain.CustomUser;
import com.project.domain.ChargeCoin;
import com.project.domain.Member;
import com.project.service.CoinService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/coin")
public class CoinController {
	@Autowired
	private CoinService service;

	@Autowired
	private MessageSource messageSource;

	// 코인 충전 페이지
	@GetMapping("/charge")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public void chargeForm(Model model) throws Exception {
		ChargeCoin chargeCoin = new ChargeCoin();
		chargeCoin.setAmount(1000);
		model.addAttribute(chargeCoin);
	}

	// 코인 충전 처리
	@PostMapping("/charge")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String charge(ChargeCoin chargeCoin, RedirectAttributes rttr, Authentication authentication)
			throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		// 인증된 회원정보를 시큐리티로부터 객체를 가져옴
		Member member = customUser.getMember();
		int userNo = member.getUserNo();

		chargeCoin.setUserNo(userNo);
		int count = service.charge(chargeCoin);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "충전성공");
		} else {
			rttr.addFlashAttribute("msg", "충전실패");
		}
		return "redirect:/coin/success";
	}

	// 충전 내역 페이지
	@GetMapping("/list")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public void list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		int userNo = member.getUserNo();
		model.addAttribute("list", service.list(userNo));
	}

	// 코인 충전 성공 페이지
	@GetMapping("/success")
	public String success() throws Exception {
		return "coin/success";
	}
}
