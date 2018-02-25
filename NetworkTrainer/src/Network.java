import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Network {
	
	private int inputSize, hiddenSize, outputSize;
	
	private Matrix inputWeights, inputBiases, hiddenWeights, hiddenBiases;
	
	public Network(int inputNeurons, int hiddenNeurons, int outputNeurons){
		
		inputWeights = new Matrix(inputNeurons,hiddenNeurons);
		inputBiases = new Matrix(1, hiddenNeurons);
		
		hiddenWeights = new Matrix(hiddenNeurons,outputNeurons);
		hiddenBiases = new Matrix(1, outputNeurons);
		
		double startLimit = 1;
		
		inputWeights.randomize(-startLimit, startLimit);
		inputBiases.randomize(-startLimit, startLimit);
		
		hiddenWeights.randomize(-startLimit, startLimit);
		hiddenBiases.randomize(-startLimit, startLimit);
		
		inputSize = inputNeurons;
		hiddenSize = hiddenNeurons;
		outputSize = outputNeurons;
		
	}
	
	public Network(String s){
		
		String[] chars = s.split(" ");
		
		inputSize = Integer.parseInt(chars[0]);
		hiddenSize = Integer.parseInt(chars[1]);
		outputSize = Integer.parseInt(chars[2]);
		
		int i = 3;
		
		double[][] inputWeightA = new double[inputSize][hiddenSize], inputBiasA = new double[1][hiddenSize], 
				   hiddenWeightA = new double[hiddenSize][outputSize], hiddenBiasA = new double[1][outputSize];
		
		int temp = i;
		
		for(;i < temp + inputSize * hiddenSize; i++){
			
			int x = (i - temp)/inputSize, y = (i-temp)%inputSize;
			
			inputWeightA[y][x] = Double.parseDouble(chars[i]);
			
		}
		
		temp = i;
		
		for( ;i < temp + outputSize * hiddenSize; i++){
			
			int x = (i - temp)/hiddenSize, y = (i-temp)%hiddenSize;
			
			hiddenWeightA[y][x] = Double.parseDouble(chars[i]);
			
		}
		
		temp = i;
		
		for( ;i < temp + hiddenSize; i++){
			
			int x = i - temp, y = 0;
			
			inputBiasA[y][x] = Double.parseDouble(chars[i]);
			
		}
		
		temp = i;
		
		for( ;i < temp + outputSize; i++){
			
			int x = i - temp, y = 0;
			
			hiddenBiasA[y][x] = Double.parseDouble(chars[i]);
			
		}
		
		inputWeights = new Matrix(inputWeightA);
		inputBiases = new Matrix(inputBiasA);
		
		hiddenWeights = new Matrix(hiddenWeightA);
		hiddenBiases = new Matrix(hiddenBiasA);
		
	}
	
	public void trainBatch(Matrix[][] data, int batch, int epochs, double n) {
		
		for(int e = 0; e < epochs; e++) {
			
			List<Matrix[]> temp = Arrays.asList(data);
			
			Collections.shuffle(temp);
			
			data = (Matrix[][])temp.toArray();
			
			for(int b = 0; b < data.length/batch; b++) {
				
				Matrix[] desc = new Matrix[] {new Matrix(1,outputSize),new Matrix(hiddenSize,outputSize),
						new Matrix(1,hiddenSize),new Matrix(inputSize,hiddenSize),};
				
				for(int i = b*batch; i < (b+1)*batch; i++) {
					
					Matrix[] results = test(data[i][0]);
					
					Matrix[] tDesc = stochasticTraining(data[i][0], data[i][1], results[1], results[0], results[2], n/batch);
					
					desc[0] = MatrixMath.subtract(desc[0], tDesc[0]);
					desc[1] = MatrixMath.subtract(desc[1], tDesc[1]);
					desc[2] = MatrixMath.subtract(desc[2], tDesc[2]);
					desc[3] = MatrixMath.subtract(desc[3], tDesc[3]);
					
				}
				
				hiddenBiases = MatrixMath.add(hiddenBiases, desc[0]);
				hiddenWeights = MatrixMath.add(hiddenWeights, desc[1]);
				inputBiases = MatrixMath.add(inputBiases, desc[2]);
				inputWeights = MatrixMath.add(inputWeights, desc[3]);
				
				
			}
			
			
		}
	}
	
	public Matrix[] stochasticTraining(Matrix inputs, Matrix outputs, Matrix delta2, Matrix delta1, Matrix output, double n){
		
		Matrix dJdB2 = MatrixMath.elementMultiplication(Sigmoid.sigmoidPrime(delta2), MatrixMath.subtract(output,outputs));
		Matrix dJdW2 = MatrixMath.multiply(Sigmoid.sigmoid(delta1).getTransposition(), dJdB2);
		
		Matrix dJdB1 = MatrixMath.multiply(Sigmoid.sigmoidPrime(delta1), MatrixMath.dot(dJdB2,hiddenWeights.getTransposition()));
		Matrix dJdW1 = MatrixMath.multiply(inputs.getTransposition(), dJdB1);
		
		//hb,hw,ib,iw
		return new Matrix[] {MatrixMath.multiply(dJdB2, n), MatrixMath.multiply(dJdW2, n),
				MatrixMath.multiply(dJdB1, n),MatrixMath.multiply(dJdW1, n)};
		
	}
	
	public Matrix[] test(Matrix inputs){
		
		Matrix delta1 = MatrixMath.add(MatrixMath.multiply(inputs, inputWeights), inputBiases);
		
		Matrix delta2 = MatrixMath.add(MatrixMath.multiply(Sigmoid.sigmoid(delta1), hiddenWeights), hiddenBiases);
		
		return new Matrix[]{delta1, delta2,(Sigmoid.sigmoid(delta2))};
		
	}
	
	public Matrix simpleTest(Matrix inputs){
		
		Matrix delta1 = MatrixMath.add(MatrixMath.multiply(inputs, inputWeights), inputBiases);
		
		Matrix delta2 = MatrixMath.add(MatrixMath.multiply(Sigmoid.sigmoid(delta1), hiddenWeights), hiddenBiases);
		
		return (Sigmoid.sigmoid(delta2));
		
	}
	
	private double sigs(double input){
		
		int pow = (int)Math.log10(input);
		
		input = (input/Math.pow(10, pow))*1000;
		
		input = (int)input;
		
		input = (input/1000)*Math.pow(10, pow);
		
		return input;
		
	}
	
	public String toString(){
		
		String output = inputSize + " " + hiddenSize + " " + outputSize + " ";
		
		
		for(double d:inputWeights.getSingleArray()){
			output += sigs(d) + " ";
		}
		
		for(double d:hiddenWeights.getSingleArray()){
			output += sigs(d) + " ";
		}
		
		for(double d:inputBiases.getSingleArray()){
			output += sigs(d) + " ";
		}
		
		for(double d:hiddenBiases.getSingleArray()){
			output += sigs(d) + " ";
		}
		
		return output;
		
	}
	
	
}
