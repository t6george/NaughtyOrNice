public class Sigmoid {
	
	public static Matrix sigmoid(Matrix input){
		
		Matrix output = new Matrix(input.getHeight(), input.getWidth());
		
		for(int y = 0; y < input.getHeight(); y++){
			for(int x = 0; x < input.getWidth(); x++){
				
				output.setAttribute(x, y, sigmoid(input.getAttribute(x,y)));
				
			}
		}
		
		return output;
		
	}
	
	public static Matrix sigmoidPrime(Matrix input){
		
		Matrix output = new Matrix(input.getHeight(), input.getWidth());
		
		for(int y = 0; y < input.getHeight(); y++){
			for(int x = 0; x < input.getWidth(); x++){
				
				output.setAttribute(x, y, sigmoidPrime(input.getAttribute(x,y)));
				
			}
		}
		
		return output;
		
	}
	
	public static double sigmoid(double input){
		
		return 1/(1 + Math.exp(-input));
		
	}
	
	public static double sigmoidPrime(double input){
		
		return sigmoid(input)*(1-sigmoid(input));
		
	}
	
}
