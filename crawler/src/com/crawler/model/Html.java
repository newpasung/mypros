package com.crawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.crawler.db.BaseModel;

@Entity()
@Table(name="html")
public class Html  extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hid")
	private String id;

	@Column(name="url")
	private String url;

	@Column(name="title")
	private String title;
	
	@Column(name="content",length=60000)
	private String content;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
