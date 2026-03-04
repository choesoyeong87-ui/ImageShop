package com.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.common.security.domain.CustomUser;
import com.project.domain.Member;
import com.project.domain.UserItem;
import com.project.service.UserItemService;

@Controller
@RequestMapping("/useritem")
public class UserItemController {
	@Autowired
	private UserItemService service;

	@Value("${upload.path}")
	private String uploadPath;

	// 회원 구매 상품 목록(회원장바구니)
	@GetMapping(value = "/list")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void list(Model model, Authentication authentication) throws Exception {
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Member member = customUser.getMember();

		model.addAttribute("list", service.list(member));
	}

	// 구매 상품 보기
	@GetMapping(value = "/read")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public void read(UserItem userItem, Model model) throws Exception {
		model.addAttribute(service.read(userItem));
	}

	// 서버 외장하드에 있는 이미지를 사용자에게 다운로드 시켜서 로컬컴퓨터에 저장한다.(구매 상품 다운 로드)
	@ResponseBody
	@RequestMapping("/download")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	public ResponseEntity<byte[]> download(UserItem _userItem, Authentication authentication) throws Exception {
		UserItem userItem = service.read(_userItem);
		String fullName = userItem.getPictureUrl();
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		try {
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + File.separator + fullName);
			//404e5234-fdc4-4dab-9218-fe7aea891192_20260109_143440.png => 143440.png
			String fileName = fullName.substring(fullName.indexOf("_") + 1);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition","attachment;filename=\"" 
			+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}

		return entity;
	}
}