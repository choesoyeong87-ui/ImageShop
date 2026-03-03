package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.domain.Comment;
import com.project.mapper.CommentMapper;
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentMapper mapper;
	
	@Override
	public List<Comment> list(int boardNo) throws Exception {
		return mapper.list(boardNo);
	}

	@Override
	public int register(Comment comment) throws Exception {
		return mapper.register(comment);
	}

	@Override
	public int remove(Comment comment) throws Exception {
		return mapper.remove(comment);
	}

}
