package com.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.common.domain.Item;
import com.project.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Value("${upload.path}")
	private String uploadPath;

	// 상품 등록 페이지
	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void registerForm(Model model) {
		model.addAttribute(new Item());
	}

	// 상품 등록 처리
	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String register(Item item, RedirectAttributes rttr) throws Exception {
		// 클라이언트가 보낸 이미지 파일의 정보 모두 로드가 되었다.
		MultipartFile pictureFile = item.getPicture();
		MultipartFile previewFile = item.getPreview();
		// createdPictureFilename =b26b867d-458d-4738-bf3c-75229c527a3d_kdj1.jpg
		// createdPreviewFilename =b26b867d-458d-4738-bf3c-75229c527a3d_kdj2.jpg
		String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
		String createdPreviewFilename = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
		// 테이블에 저장하기 위한 파일명을 setter
		item.setPictureUrl(createdPictureFilename);
		item.setPreviewUrl(createdPreviewFilename);

		int count = itemService.register(item);

		if (count != 0) {
			rttr.addFlashAttribute("msg", "SUCCESS");
		} else {
			rttr.addFlashAttribute("msg", "FAIL");
		}
		return "redirect:/item/list";
	}

	// 상품 목록 페이지
	@GetMapping("/list")
	public void list(Model model) throws Exception {
		List<Item> itemList = itemService.list();
		model.addAttribute("itemList", itemList);
	}

	// 상품 수정 페이지
	@GetMapping("/modify")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String modifyForm(Item item, Model model) throws Exception {
		model.addAttribute(itemService.read(item));
		return "item/modify";
	}

	// 미리보기 이미지 표시(썸네일)
	@ResponseBody
	@RequestMapping("/display")
	public ResponseEntity<byte[]> displayFile(Item item) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String fileName = itemService.getPreview(item);
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + File.separator + fileName);

			if (mType != null) {
				headers.setContentType(mType);
			}

			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	// 원본이미지
	@ResponseBody
	@RequestMapping("/picture")
	public ResponseEntity<byte[]> pictureFile(Item item) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		// 썸네일 이미지 파일명을디비로부터 가져온다.
		String fileName = itemService.getPicture(item);
		try {
			// jpg 확장자명을 가져오는것
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			// jpg의 미디어타입을 리턴받는다.
			MediaType mType = getMediaType(formatName);
			// httpHader는 브라우저에게 서버가 정보를 담아서 보내주는 객체
			HttpHeaders headers = new HttpHeaders();
			// 파일을 읽는다. inputStream(바이트단위로 읽음)
			// D:/upload/..jpg 파일 위치를 찾아서 파일을 inputStream으로 읽는다
			in = new FileInputStream(uploadPath + File.separator + fileName);

			if (mType != null) {
				// httpHader는 브라우저에게 서버가 정보를 담아서 보내주는 객체에 미디어타입을 세팅
				headers.setContentType(mType);
			}
			// 전송합니다. json방식
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		// 웹 브라우저에게 바이트단위로 이미지를 전송하는것.
		return entity;
	}

	// 파일 확장자로 이미지 형식 확인
	private MediaType getMediaType(String formatName) {
		if (formatName != null) {
			if (formatName.equals("JPG")) {
				return MediaType.IMAGE_JPEG;
			}
			if (formatName.equals("GIF")) {
				return MediaType.IMAGE_GIF;
			}

			if (formatName.equals("PNG")) {
				return MediaType.IMAGE_PNG;
			}
		}
		return null;
	}

	// 상품 이미지파일명과 , 상품이미지데이타를 byte[] 업로드
	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		// 절대로 중복되지않는 아이디를 랜덤생성
		// b26b867d-458d-4738-bf3c-75229c527a3d_kdj.jpg
		UUID uid = UUID.randomUUID();
		String createdFileName = uid.toString() + "_" + originalName;
		// D:/upload/b26b867d-458d-4738-bf3c-75229c527a3d_kdj.jpg 파일생성
		File target = new File(uploadPath, createdFileName);
		FileCopyUtils.copy(fileData, target);
		return createdFileName;
	}
}