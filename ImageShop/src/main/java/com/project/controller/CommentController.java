package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.security.domain.CustomUser;
import com.project.domain.Comment;
import com.project.domain.Member;
import com.project.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService service;

	@GetMapping("/register")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void registerForm(Model model, Comment comment, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		comment.setUserId(member.getUserId());

		model.addAttribute(comment);

	}

	@PostMapping("/register")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String register(Comment comment, RedirectAttributes rttr) throws Exception {
		int count = service.register(comment);

		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}

	// 삭제 처리
	@PostMapping("/remove")
	@PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
	public String remove(Comment comment, RedirectAttributes rttr) throws Exception {
		int count = service.remove(comment);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}

	// 수정처리
	@PostMapping("/modify")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String modify(RedirectAttributes rttr, Comment comment) throws Exception {
		log.info("modify" + comment);
		int count = service.update(comment);
		log.info("modify" + count);
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAILED");
		}
		return "redirect:/board/read?boardNo=" + comment.getBoardNo();
	}
}