package com.project.service;

import java.util.List;

import org.jspecify.annotations.Nullable;

import com.project.domain.Comment;

public interface CommentService {

	
	public List<Comment> list(int boardNo) throws Exception;

	public int register(Comment comment) throws Exception;

	public int remove(Comment comment) throws Exception;

}
