package com.cya.poeming.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private int id;
	private String title;
	private String body;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private int hitCount;
	private int goodReactionPoint;
	private int badReactionPoint;
	private int bookmark;
	private int report;
	
	private String extra__writerName;
	private String extra__boardName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	// 날짜&시간
	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br />");
	}
	// 날짜만
	public String getForPrintType2RegDate() {
		return regDate.substring(2, 10);
	}
	// 시간만
	public String getForPrintType3RegDate() {
		return regDate.substring(11, 16);
	}

	public String getForPrintBody() {
		return body.replaceAll("\n", "<br />");
	}
}
