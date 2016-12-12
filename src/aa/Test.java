package aa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {
//	public static void main(String[] args) {
//		String aa = "原价25.9元，抢券立省3元";
//		System.out.println();
//	}
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		String url="http://tbb.so/i0PMHR";
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
	    //设置webClient的相关参数
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    //webClient.getOptions().setTimeout(50000);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    //模拟浏览器打开一个目标网址
	    HtmlPage rootPage = webClient.getPage(url);
	    System.out.println("为了获取js执行的数据 线程开始沉睡等待");
	    Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的
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
	    //价钱
	    System.out.println("价钱     "+date[1]);
	    //优惠券
	    System.out.println("优惠券"+date[1].substring((date[1].indexOf("省")+1),date[1].lastIndexOf("元")));
	    //销量
	    System.out.println("销量    "+dealNum);
	    //抢券链接
	    System.out.println("抢券链接    "+url);
	    
	}
	/*public static void main3(String[] args) throws Exception {
		BufferedReader reader =null;
		BufferedWriter write = null;
		 URL  url =  new URL("http://tbb.so/i0PPGO");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); 
		int responseCode = urlConnection.getResponseCode();
		if(200==responseCode){
			reader = new BufferedReader (new InputStreamReader( urlConnection.getInputStream(),"UTF-8"));
			//write = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(new File("D://test.txt")),"utf-8" )));
			String line;
			while((line = reader.readLine())!=null){
				//write.write(line);
				//write.newLine();
				if(line.contains("mm_119813440_18296586_65138761")){
					line = line.substring((line.indexOf('"')+1),line.lastIndexOf('"'));
					break;
				}else{
					continue;
				}
			}
			System.out.println(line);
			get(line);
			
		}else{
			
			 System.out.println("获取不到网页的源码，服务器响应代码为："+responseCode);
		}
		reader.close();
	}
	
	
	
	
	public static void  get(String urll) throws Exception{
		BufferedReader reader =null;
		BufferedWriter write = null;
		 URL  url =  new URL(urll);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); 
		int responseCode = urlConnection.getResponseCode();
		if(200==responseCode){
			reader = new BufferedReader (new InputStreamReader( urlConnection.getInputStream(),"UTF-8"));
			write = new BufferedWriter((new OutputStreamWriter(new FileOutputStream(new File("D://test.txt")),"utf-8" )));
			String line;
			while((line = reader.readLine())!=null){
				write.write(line);
				write.newLine();
//				if(line.contains("mm_119813440_18296586_65138761")){
//					line = line.substring((line.indexOf('"')+1),line.lastIndexOf('"'));
//					break;
//				}else{
//					continue;
//				}
			}
			write.flush();
		}else{
			
			 System.out.println("获取不到网页的源码，服务器响应代码为："+responseCode);
		}
		reader.close();
		write.close();
	}*/
}
