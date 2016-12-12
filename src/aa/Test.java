package aa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {
	public static List<Product> products = Collections.synchronizedList(new ArrayList<Product>());
//	ͼƬURL    //gaitaobao1.alicdn.com/tfscom/i2/TB1OxrSOFXXXXcvXpXXXXXXXXXX_!!0-item_pic.jpg_300x300q90.jpg
//	productName  �����鶯ħ������2�������ͯ���������װ...
//	��Ǯ     ԭ��20.8Ԫ����ȯ��ʡ15Ԫ
//	�Ż�ȯ15
//	����    19017
//	��ȯ����    http://tbb.so/i0PMHR

	public static void main(String[] args) throws IOException {
		 
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20); 
		File f = new File("C:\\Users\\Administrator\\Desktop\\a.txt");
		ArrayList<String> urlDate = initQQDate(f);
		String url =null;
		if(urlDate.size()!=0){
			for(int i=0;i<urlDate.size();i++){
				url = urlDate.get(i);
				fixedThreadPool.execute(new Task((i+1)+"",url));
			}
			
			
			while(true){
				if(Test.products.size()==urlDate.size()){
					break;
				}
				try {
					Thread.sleep(5000l);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			JSONArray jsonArray = JSONArray.fromObject( Test.products ); 
			System.out.println(jsonArray.toString());
		}
		
		
		
		
	}
	
	//ͨ��qq�����ļ��õ���Ʒurl
	private static ArrayList<String> initQQDate(File qqFile) throws IOException{
		ArrayList<String> qqDate = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(qqFile)));
			String line;
			int top =0;
			while ((line=reader.readLine())!=null) {
				if(line.startsWith("����ȯ�µ���ַ��")&&line.contains("http://s.click.taobao.com/")){
					++top;
					qqDate.add(line.replace("����ȯ�µ���ַ��", ""));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("�����¼�ļ������ڣ�");
		} finally{
			reader.close();
			reader=null;
		}
		return qqDate;
	}
	
	

	
	public static void main3(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		String url="http://tbb.so/i0PMHR";
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
	    //����webClient����ز���
	    webClient.getOptions().setJavaScriptEnabled(true);
	    webClient.getOptions().setCssEnabled(false);
	    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	    //webClient.getOptions().setTimeout(50000);
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    //ģ���������һ��Ŀ����ַ
	    HtmlPage rootPage = webClient.getPage(url);
	    System.out.println("Ϊ�˻�ȡjsִ�е����� �߳̿�ʼ��˯�ȴ�");
	    Thread.sleep(3000);//��Ҫ������̵߳ĵȴ� ��Ϊjs����Ҳ����Ҫʱ���
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
	    System.out.println("ȯ��۸�     "+(Integer.valueOf(priceOld)-Integer.valueOf(priceNew))+"");
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
			
			 System.out.println("��ȡ������ҳ��Դ�룬��������Ӧ����Ϊ��"+responseCode);
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
			
			 System.out.println("��ȡ������ҳ��Դ�룬��������Ӧ����Ϊ��"+responseCode);
		}
		reader.close();
		write.close();
	}*/
}
