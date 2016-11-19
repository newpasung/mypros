package com.crawler;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.model.Html;
import com.google.common.base.Strings;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class WeiboCrawler extends BreadthCrawler {
	ThreadSession sessionFactory = new ThreadSession();
    String cookie;
    public WeiboCrawler(String crawlPath, boolean autoParse) throws Exception {
        super(crawlPath, autoParse);
        /*获取新浪微博的cookie，账号密码以明文形式传输，请使用小号*/
        cookie = WeiboCN.getSinaCookie("214424199@qq.com", "mima8888");
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setCookie(cookie);
        
        return request.getResponse();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        @SuppressWarnings("deprecation")
		int pageNum = Integer.valueOf(page.getMetaData("pageNum"));
        /*抽取微博*/
        Elements weibos = page.select("div.c");
		Session session = sessionFactory.get().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		String content = null;
        for (Element weibo : weibos) {
			content = content(weibo);
			if (!Strings.isNullOrEmpty(content)) {
				Html html = new Html();
				html.setUrl(page.getUrl());
				html.setContent(content);
				html.setTitle(content(page.getDoc().getElementsByTag("title").first()));
				session.persist(html);
			}
        }
		transaction.commit();
    }

    public static void main(String[] args) throws Exception {
        WeiboCrawler crawler = new WeiboCrawler("weibo_crawler", false);
		crawler.setThreads(5);
        /*对某人微博前5页进行爬取*/
		for (int i = 1; i <= 23; i++) {
			crawler.addSeed(
					new CrawlDatum("http://weibo.cn/lindan?vt=4&page=" + i)
                    .putMetaData("pageNum", i + ""));
        }
        crawler.start(1);
    }

	public String content(Element element) {
		if (element == null) {
			return "";
		}
		return element.text();
	}

}
