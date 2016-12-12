package aa;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Task implements Runnable {
	static WebClient webClient =null;
	static{
		 	webClient = new WebClient(BrowserVersion.FIREFOX_45);
		    //设置webClient的相关参数
		    webClient.getOptions().setJavaScriptEnabled(true);
		    webClient.getOptions().setCssEnabled(false);
		    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		    //webClient.getOptions().setTimeout(50000);
		    webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	private String url ;
	private String top;
	Task(String top,String url){
		this.url=url;
		this.top=top;
	}
	@Override
	public void run()  {
		try{
		// TODO Auto-generated method stub
		HtmlPage rootPage =null;
		 //模拟浏览器打开一个目标网址
	    System.out.println("为了获取js执行的数据 线程开始沉睡等待");
	    try {
	    	rootPage = webClient.getPage(url);
			Thread.sleep(5000);
		} catch (InterruptedException | FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}//主要是这个线程的等待 因为js加载也是需要时间的
	    System.out.println("线程结束沉睡");
	    String html = rootPage.asXml();
	    System.out.println(html);
	    Document doc = Jsoup.parse(html, "UTF-8");
	    String picURL = doc.select("div.pic img").first().attr("src");
	    String attr = doc.select("div[mx-view=app/common/share/index]").first().attr("mx-init");
	    String[] date =attr.substring((attr.indexOf('"')+1),attr.lastIndexOf('"')).split("  ");
	    String dealNumHtml = doc.select("span.dealNum").first().html();
	    String dealNum = dealNumHtml.substring(0,dealNumHtml.indexOf("笔成交"));
	    System.out.println("图片URL    "+picURL);
	    System.out.println("productName  "+date[0]);
	    //价钱str
	    System.out.println("价钱     "+date[1]);
	    //原价
	    String priceOld = date[1].substring((date[1].indexOf("价")+1),date[1].indexOf("元"));
	    System.out.println(priceOld);
	    //优惠券
	    String priceNew = date[1].substring((date[1].indexOf("省")+1),date[1].lastIndexOf("元"));
	    System.out.println("优惠券  "+priceNew);
	    if(priceNew.equals("null")){
	    	System.out.println("priceNew.equals(null)");
	    	return ;
	    }
	    //销量
	    System.out.println("销量    "+dealNum);
	    //抢券链接
	    System.out.println("抢券链接    "+url);
	    //券后价格
	    String afterPrice = (Double.valueOf(priceOld)-Double.valueOf(priceNew))+"";
	    System.out.println("券后价格     "+afterPrice);
	    Test.products.add(new Product(url, picURL, date[0], priceOld, priceNew, afterPrice, dealNum, url));
		}catch(Exception e){
			e.printStackTrace();
		}
		 Test.productsSumAdd();
	}

}
