package com.project.controller;

import java.awt.PageAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.domain.PageRequest;
import com.project.common.domain.Pagination;
import com.project.common.security.domain.CustomUser;
import com.project.domain.Board;
import com.project.domain.Member;
import com.project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService service;

	// 게시글 등록 페이지
	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public void registerForm(Model model, Authentication authentication) throws Exception {
		// 로그인한 사용자 정보 획득
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		Board board = new Board();
		// 로그인한 사용자 아이디를 등록 페이지에 표시
		board.setWriter(member.getUserId());

		model.addAttribute(board);
	}

	// 게시글 등록 처리
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String register(Board board, RedirectAttributes rttr) throws Exception {
		int count = service.register(board);

		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");

		} else {

			rttr.addFlashAttribute("msg", "Regoster Faoied");
		}
		return "redirect:/board/list";
	}

	// 게시글 목록 페이지
	
	@GetMapping("/list")
	public void list(@ModelAttribute("pgrq") PageRequest pageRequest,Model model) throws Exception {
		if(pageRequest.getPage()== 0) {
			pageRequest = new PageRequest();
		}
		//4페이지를 보여주는 기능,디비에가서 31~40 가져온다.
		model.addAttribute("list", service.list(pageRequest));
		//페이지를 보여주는 기능([prev=true]1,2,3,[4],5,6,7,8,9,10[next=true])
		Pagination pagination = new Pagination();
		//현재페이지 4와 한페이지당 보여주는갯수가 10개로 셋팅이 되어있음
		pagination.setPageRequest(pageRequest);
		//리스트의 전체갯수를 셋팅하고 다시계산한다.
		pagination.setTotalCount(service.count());
		//화면에 페이지를 보여주는 정보를 제공한다.
		model.addAttribute("pagination",pagination);
	}

	// 게시글 상세 페이지
	@GetMapping("/read")
	public void read(Board board, @ModelAttribute("pgrq") PageRequest 
			pageRequest, Model model) throws Exception {
		model.addAttribute(service.read(board));
	}

	// 게시글 수정 페이지
	@GetMapping("/modify")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void modifyForm(Board board,@ModelAttribute("pgrq") PageRequest 
			pageRequest, Model model) throws Exception {
		model.addAttribute(service.read(board));
	}

	// 게시글 수정 처리
	@PostMapping("/modify")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String modify(Board board,@ModelAttribute("pgrq")  PageRequest pageRequest,RedirectAttributes rttr) throws Exception {
		int count = service.modify(board);
		
		// RedirectAttributes 객체에 일회성 데이터를 지정하여 전달한다. 
		rttr.addAttribute("page", pageRequest.getPage()); 
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage()); 
		
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/board/list";
	}

	// 게시글 삭제 처리
	@GetMapping("/remove")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public String remove(Board board,PageRequest pageRequest, RedirectAttributes rttr) throws Exception {
		int count = service.remove(board);
		
		// RedirectAttributes 객체에 일회성 데이터를 지정하여 전달한다. 
		rttr.addAttribute("page", pageRequest.getPage()); 
		rttr.addAttribute("sizePerPage", pageRequest.getSizePerPage());
		
		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/board/list";
	}
}