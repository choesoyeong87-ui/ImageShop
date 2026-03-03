package com.project.mapper;

import java.util.List;

import com.project.domain.Comment;

public interface CommentMapper {

	public List<Comment> list(int boardNo) throws Exception;

	public int register(Comment comment) throws Exception;

	public int remove(Comment comment) throws Exception;

}
