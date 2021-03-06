package aa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.SimpleFormatter;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {
	private static int readProductsUrlSum=0;
	public static List<Product> products = Collections.synchronizedList(new ArrayList<Product>());
	public synchronized static void readProductsUrlSumAdd(){
		readProductsUrlSum=readProductsUrlSum+1;
	}
	public static int getReadProductsUrlSum(){
		return readProductsUrlSum;
	}
	//	图片URL    //gaitaobao1.alicdn.com/tfscom/i2/TB1OxrSOFXXXXcvXpXXXXXXXXXX_!!0-item_pic.jpg_300x300q90.jpg
//	productName  正版灵动魔幻陀螺2代发光儿童陀螺玩具套装...
//	价钱     原价20.8元，抢券立省15元
//	优惠券15
//	销量    19017
//	抢券链接    http://tbb.so/i0PMHR

	public static void main(String[] args) throws IOException {
		 
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20); 
		File f = new File("C:\\Users\\Administrator\\Desktop\\b.txt");
		ArrayList<String> urlDate = initQQDate(f);
		String url =null;
		if(urlDate.size()!=0){
			for(int i=0;i<urlDate.size();i++){
				url = urlDate.get(i);
				fixedThreadPool.execute(new Task((i+1)+"",url));
			}
			fixedThreadPool.shutdown();
			
			while(!fixedThreadPool.isTerminated()){
				try {
					Thread.sleep(30000l);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			JSONArray jsonArray = JSONArray.fromObject( Test.products ); 
			BufferedWriter write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("d:/products.json")),"utf-8" ));
			write.write("var products ="+jsonArray.toString());
			write.flush();
			write.close();
			//System.out.println(jsonArray.toString());
			System.out.println("url共有   "+urlDate.size());
			System.out.println("url有效的共有   "+Test.products.size());
			System.out.println("url无效的共有   "+(urlDate.size()-products.size()));
		}else{
			System.out.println("聊天记录中没有url");
		}
		
		
		
		
	}
	
	//通过qq聊天文件得到商品url
	private static ArrayList<String> initQQDate(File qqFile) throws IOException{
		ArrayList<String> qqDate = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(qqFile),"GBK"));
			String line;
			int top =0;
			while ((line=reader.readLine())!=null) {
				if(line.startsWith("【领券下单地址】")&&(line.contains("http://s.click.taobao.com/")||line.contains("http://tbb.so/"))){
					++top;
					qqDate.add(line.replace("【领券下单地址】", ""));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("聊天记录文件不存在！");
		} finally{
			reader.close();
			reader=null;
		}
		return qqDate;
	}
	
	

	public static void main2(String[] args) {
		WebClient	webClient = new WebClient(BrowserVersion.FIREFOX_45);
	    //设置webClient的相关参数
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    //webClient.getOptions().setTimeout(50000);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
		String url="http://tbb.so/m0M5RV";
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
		    //System.out.println(html);
		    Document doc = Jsoup.parse(html, "UTF-8");
		    String picURL=null;
		    String attr=null;
		    String[] date=null;
		    String dealNumHtml=null;
		    try{
			    picURL = doc.select("div.pic img").first().attr("src");
			    attr = doc.select("div[mx-view=app/common/share/index]").first().attr("mx-init");
			    date =attr.substring((attr.indexOf('"')+1),attr.lastIndexOf('"')).split("  ");
			    dealNumHtml = doc.select("span.dealNum").first().html();
		    }catch(Exception e){
		    	return;
		    }
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
		    Test.products.add(new Product(""+(Test.products.size()+1), "https:"+picURL, date[0], priceOld, priceNew, afterPrice, dealNum, url));
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
