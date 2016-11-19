package com.crawler;
import java.util.List;

import org.jsoup.nodes.Document;

import com.crawler.db.BaseModel;
import com.crawler.model.Html;

import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class CrawlerNews extends BreadthCrawler {

	public CrawlerNews(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
		this.addRegex("http://news.hfut.edu.cn/show-.*html");
		this.addRegex("-.*\\.(jpg|png|gif).*");
		this.addRegex("-.*#.*");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if(page.matchUrl("http://news.hfut.edu.cn/show-.*html")){
			try{
				Document document = page.getDoc();
				String title = page.select("div[id=Article]>h2").first().text();
				String content = page.select("div#artibody",0).text();
				Html html = new Html();
				html.setContent(content);
				html.setTitle(title);
				html.setUrl(page.getUrl());
				html.save();
			}catch (NullPointerException e) {
			}
		}
	}
	
	public static void main(String [] args){
		CrawlerNews crawler = new CrawlerNews("crawl",true);
		crawler.setThreads(20);
		crawler.setTopN(5000);
		crawler.setResumable(false);
		try {
			crawler.start(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String text){
		System.out.println(text);
	}


}
