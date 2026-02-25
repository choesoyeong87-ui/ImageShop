package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.config.I18nConfiguration;
import com.project.domain.CodeGroup;
import com.project.service.CodeGroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/codegroup")
//관리자 권한을 가진 사용자만 접근이 가능하다. 
@PreAuthorize("hasRole('ROLE_ADMIN')") 
public class CodeGroupController {

	private final I18nConfiguration i18nConfiguration;

	@Autowired
	private CodeGroupService service;

	CodeGroupController(I18nConfiguration i18nConfiguration) {
		this.i18nConfiguration = i18nConfiguration;
	}

	// 등록 페이지
	@GetMapping("/register")
	public void registerForm(Model model) throws Exception {
		CodeGroup codeGroup = new CodeGroup();
		model.addAttribute(codeGroup);
		// return "codegroup/register";
	}

	// 코드그룹등록 처리요청
	@PostMapping("/register")
	public String register(CodeGroup codeGroup, RedirectAttributes rttr) throws Exception {
		int count = service.register(codeGroup);
		// rttr.addFlashAttribute("msg", "SUCCESS") 세션에 정보를 넘겨준다.
		// codeGroup/list에 이 내용이 model로 딱 한번만 들어간다!
		log.info("codeGroup/regeiset=" + count);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
			return "redirect:/codegroup/list";

		}
		return "redirect:/codegroup/register";
	}

	// 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		log.info("model.getAttribute(msg)=" + model.getAttribute("msg"));
		model.addAttribute("list", service.list());
	}

	// 코드그룹 상세 페이지 요청
	@GetMapping("/read")
	public void read(CodeGroup codeGroup, Model model) throws Exception {
		model.addAttribute(service.read(codeGroup));
	}

	// 코드그룹 삭제 처리 요청
	@PostMapping("/remove")
	public String remove(CodeGroup codeGroup, RedirectAttributes rttr) throws Exception {
		int count = service.remove(codeGroup);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else
			rttr.addFlashAttribute("msg", "FAIL");
		return "redirect:/codegroup/list";
	}

	// 코드그룹 수정페이지요청
	@GetMapping("/modify")
	public void modifyForm(CodeGroup codeGroup, Model model) throws Exception {
		model.addAttribute(service.read(codeGroup));
	}

	// 코드그룹 수정 페이지 요청
	@PostMapping("/modify")
	public String modify(CodeGroup codeGroup, RedirectAttributes rttr) throws Exception {
		int count = service.modify(codeGroup);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/codegroup/list";
	}

}
