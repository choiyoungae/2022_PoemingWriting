package com.cya.poeming.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cya.poeming.vo.Board;

@Mapper
public interface BoardRepository {
	
	@Select("""
			SELECT *
			FROM board AS B
			WHERE B.id = #{id}
			AND B.delStatus = 0
			""")
	public Board getBoardById(int id);
}
