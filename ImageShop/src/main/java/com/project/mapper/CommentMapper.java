package com.project.mapper;

import java.util.List;

import com.project.domain.Comment;

public interface CommentMapper {

	public List<Comment> list(int boardNo) throws Exception;

}
