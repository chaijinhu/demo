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
		    //����webClient����ز���
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
		// TODO Auto-generated method stub
		HtmlPage rootPage =null;
		 //ģ���������һ��Ŀ����ַ
	    System.out.println("Ϊ�˻�ȡjsִ�е����� �߳̿�ʼ��˯�ȴ�");
	    try {
	    	rootPage = webClient.getPage(url);
			Thread.sleep(5000);
		} catch (InterruptedException | FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}//��Ҫ������̵߳ĵȴ� ��Ϊjs����Ҳ����Ҫʱ���
	    System.out.println("�߳̽�����˯");
	    String html = rootPage.asXml();
	    System.out.println(html);
	    Document doc = Jsoup.parse(html, "UTF-8");
	    String picURL = doc.select("div.pic img").first().attr("src");
	    String attr = doc.select("div[mx-view=app/common/share/index]").first().attr("mx-init");
	    String[] date =attr.substring((attr.indexOf('"')+1),attr.lastIndexOf('"')).split("  ");
	    String dealNumHtml = doc.select("span.dealNum").first().html();
	    String dealNum = dealNumHtml.substring(0,dealNumHtml.indexOf("�ʳɽ�"));
	    System.out.println("ͼƬURL    "+picURL);
	    System.out.println("productName  "+date[0]);
	    //��Ǯstr
	    System.out.println("��Ǯ     "+date[1]);
	    //ԭ��
	    String priceOld = date[1].substring((date[1].indexOf("��")+1),date[1].indexOf("Ԫ"));
	    System.out.println(priceOld);
	    //�Ż�ȯ
	    String priceNew = date[1].substring((date[1].indexOf("ʡ")+1),date[1].lastIndexOf("Ԫ"));
	    System.out.println("�Ż�ȯ  "+priceNew);
	    //����
	    System.out.println("����    "+dealNum);
	    //��ȯ����
	    System.out.println("��ȯ����    "+url);
	    //ȯ��۸�
	    String afterPrice = (Integer.valueOf(priceOld)-Integer.valueOf(priceNew))+"";
	    System.out.println("ȯ��۸�     "+afterPrice);
	    Test.products.add(new Product(url, picURL, date[0], priceOld, priceNew, afterPrice, dealNum, url));
	}

}
