import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Main {
	
	//profesionalperson
	//neuralnetwork
	
	public static void main(String[] args) {
		
	}
	
	public Main() {
		
	}
	
	public HashMap<String, Integer> getMap() throws FileNotFoundException{
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		Scanner in = new Scanner(new File("hash"));
		
		while(in.hasNextLine()) {
			String[] s = in.nextLine().split(":");
			map.put(s[0], Integer.valueOf(s[1]));
		}
		
		return map;
		
	}
	
	public String pull(String name) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder("https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze");

			builder.setParameter("visualFeatures", "Description");
			//builder.setParameter("details", "{string}");
			builder.setParameter("language", "en");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", "38c5c60008b945778b4a2639e87f78d2");

			// Request body
			StringEntity reqEntity = new StringEntity("{\"url\":\"" + name + "\"}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			return "...";
		}
		return "...";
	}
	
}
