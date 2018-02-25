import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		
		Scanner in = null;
		try {
			in = new Scanner(new File("data"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<String[][]> data = new ArrayList<String[][]>();
		ArrayList<String> names = new ArrayList<String>();
		
		String[] O = null;
		
		while(in.hasNext()) {
			
			String s = in.nextLine();
			
			if(s.equals("||"))continue;
			
			if(s.startsWith("random"))break;
			
			if(!s.startsWith("0") && !s.startsWith("1")) {
				
				String[] d = s.split(":");
				
				names.add(d[0]);
				O = d[1].split(",");
				
			}else {
				
				String[] I = s.split(",");
				
				data.add(new String[][] {I,O});
				
			}
			
		}
		
		Matrix[][] mat = new Matrix[data.size()][2];
		
		for(int i = 0; i < mat.length; i++) {
			
			mat[i][0] = Matrix.rowMatrix(STD(data.get(i)[0]));
			mat[i][1] = Matrix.rowMatrix(STD(data.get(i)[1]));
			
		}
		
		Network net = new Network(291, 50, 7);
		
		net.trainBatch(mat, 8, 30, 3);
		
		List<Matrix[]> temp = Arrays.asList(mat);
		
		Collections.shuffle(temp);
		
		mat = (Matrix[][])temp.toArray();
		
		
//		double t = 0;
//		
//		for(int i = 0; i < 100; i++) {
//			if(top(net.simpleTest(mat[i][0]),getMax(mat[i][1])))t++;
//			//printMax(net.simpleTest(mat[i][0]));
//			//printMax(mat[i][1]);
//		}
//		
//		System.out.println(t/100);
//		
//		net.inputBiases.printMatrix();
//		System.out.println();
//		net.inputWeights.printMatrix();
//		System.out.println();
//		net.hiddenBiases.printMatrix();
//		System.out.println();
//		net.hiddenWeights.printMatrix();
		
		Scanner reader = new Scanner(System.in);
		
		while(true) {
			
			String s = reader.nextLine();
			
			String get = pull(s);
			
			if(get.equals("...") || !get.startsWith("{\"description\":{\"tags\":[")) {
				System.out.println("no");
				continue;
			}
			//System.out.println(get);
			String[] tags = get.substring(24).split("]")[0].split(",");
			
			for(int n = 0; n < tags.length; n++) {
				tags[n] = tags[n].replaceAll("\"", "");
				if(!tags[n].isEmpty() && !keys.contains(tags[n]))keys.add(tags[n]);
			}
			
			Arrays.sort(tags);
			
		}
		
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
	
	public double[] STD(String[] in) {
		
		double[] out = new double[in.length];
		for(int i = 0; i < in.length; i++)out[i] = Double.parseDouble(in[i]);
		return out;
		
	}
	
	public void printMax(Matrix r) {
		double max = 0;
		int in = 0;
		for(int i = 0; i < 7; i++) {
			if(r.getAttribute(i, 0) > max) {
				max = r.getAttribute(i, 0);
				in = i;
			}
		}
		System.out.println(in + ": "+max);
		//return in;
	}
	
	public int getMax(Matrix r) {
		double max = 0;
		int in = 0;
		for(int i = 0; i < 7; i++) {
			if(r.getAttribute(i, 0) > max) {
				max = r.getAttribute(i, 0);
				in = i;
			}
		}
		//System.out.println(in + ": "+max);
		return in;
	}
	
	public boolean top(Matrix r, int x) {
		
		int lower = 0;
		double check = r.getAttribute(x, 0);
		
		for(int i = 0; i < 7; i++) {
			if(check < r.getAttribute(i, 0))lower++;
		}
		
		return lower < 3;
		
	}
	
	
}
