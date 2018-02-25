import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Main {
	
	//317 inputs
	
	String[] names = new String[] {
			
	};
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		ArrayList<String> keys = new ArrayList<String>();
		
		String[][][] tagData = new String[Data.data.length][][];
		
		for(int i = 0; i < Data.data.length; i++) {
			
			tagData[i] = new String[Data.data[i].length][];
			
			for(int k = 0; k < Data.data[i].length; k++) {
				
				long time = System.currentTimeMillis();
				
				String s = Data.data[i][k];
				
				if(s.isEmpty()) {
					tagData[i][k] = new String[]{};
					continue;
				}
				
				String get = pull(s);
				
				if(get.equals("...") || !get.startsWith("{\"description\":{\"tags\":[")) {
					tagData[i][k] = new String[]{};
					continue;
				}
				//System.out.println(get);
				String[] tags = get.substring(24).split("]")[0].split(",");
				
				for(int n = 0; n < tags.length; n++) {
					tags[n] = tags[n].replaceAll("\"", "");
					if(!tags[n].isEmpty() && !keys.contains(tags[n]))keys.add(tags[n]);
				}
				
				Arrays.sort(tags);
				
				tagData[i][k] = tags;
				
				try {
					Thread.sleep((3100 - (System.currentTimeMillis() - time))>0?(3100 - (System.currentTimeMillis() - time)):0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			//System.out.println("Done Group");
			
		}
		
		Collections.sort(keys);
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for(int i = 0; i < keys.size(); i++) {
			map.put(keys.get(i),i);
		}
		
		int[][][] fin = new int[Data.data.length][][];
		
		for(int i = 0; i < fin.length; i++) {
			fin[i] = new int[tagData[i].length][];
			for(int k = 0; k < tagData[i].length; k++) {
				
				fin[i][k] = new int[keys.size()];
				
				for(String s:tagData[i][k]) {
					if(s.isEmpty())continue;
					fin[i][k][map.get(s)] = 1;
				}
				
			}
		}
		
		BufferedWriter b = null;
		
		try {
			b = new BufferedWriter(new FileWriter("./data"));
			
			for(int i = 0; i < fin.length; i++) {
				
				if(i > 0)b.write("||\n");
				
				b.write(Data.indeces[i] + ":");
				
				for(int k = 0; k < Data.ind[i].length; k++) {
					if(k>0)b.write(",");
					b.write(""+Data.ind[i][k]);
				}
				
				b.write("\n");
				
				for(int k = 0; k < fin[i].length; k++) {
					
					for(int j = 0; j < fin[i][k].length; j++) {
						
						if(j > 0)b.write(",");
						b.write(""+fin[i][k][j]);
						
					}
					b.write("\n");
					
				}
				
			}
			
			b.flush();
			
			b.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			b = new BufferedWriter(new FileWriter("./hash"));
			
			for(int i = 0; i < keys.size(); i++) {
				
				b.write(keys.get(i) + ":" + i + "\n");
				
			}
			
			b.flush();
			
			b.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println((keys.size()));
		
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
