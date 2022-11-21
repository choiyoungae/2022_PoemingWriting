package com.cya.poeming.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cya.poeming.vo.Article;
import com.cya.poeming.vo.Attr;
import com.cya.poeming.vo.Board;
import com.cya.poeming.vo.ResultData;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
		
	public Board getBoardById(int id) {
		return boardRepository.getBoardById(id);
	}
}
