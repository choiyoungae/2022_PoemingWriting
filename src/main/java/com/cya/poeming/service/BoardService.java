package com.cya.poeming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.repository.BoardRepository;
import com.cya.poeming.vo.Board;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
		
	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}
}
