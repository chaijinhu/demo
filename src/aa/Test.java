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
//		String aa = "ԭ��25.9Ԫ����ȯ��ʡ3Ԫ";
//		System.out.println();
//	}
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
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
	    //��Ǯ
	    System.out.println("��Ǯ     "+date[1]);
	    //�Ż�ȯ
	    System.out.println("�Ż�ȯ"+date[1].substring((date[1].indexOf("ʡ")+1),date[1].lastIndexOf("Ԫ")));
	    //����
	    System.out.println("����    "+dealNum);
	    //��ȯ����
	    System.out.println("��ȯ����    "+url);
	    
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
