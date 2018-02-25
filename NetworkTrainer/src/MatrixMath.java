public class MatrixMath {
	
	public static Matrix add(Matrix matrix1, Matrix matrix2){
		
		Matrix sum = new Matrix(matrix1.getHeight(), matrix1.getWidth());
		
		for(int x = 0; x < sum.getWidth(); x++){
			for(int y = 0; y < sum.getHeight(); y++){
				sum.setAttribute(x, y, matrix1.getAttribute(x, y)+matrix2.getAttribute(x, y));
			}
		}
		
		return sum;
		
	}
	
	public static Matrix subtract(Matrix matrix1, Matrix matrix2){
		
		Matrix quotient = new Matrix(matrix1.getHeight(), matrix1.getWidth());
		
		for(int x = 0; x < quotient.getWidth(); x++){
			for(int y = 0; y < quotient.getHeight(); y++){
				quotient.setAttribute(x, y, matrix1.getAttribute(x, y)-matrix2.getAttribute(x, y));
			}
		}
		
		return quotient;
		
	}
	
	public static double dot(Matrix matrix1, Matrix matrix2){
		
		double[] array1 = matrix1.getSingleArray();
		double[] array2 = matrix2.getSingleArray();
		
		double dot = 0;
		
		for(int i = 0; i < array1.length; i++){
			
			dot += array1[i] * array2[i];
			
		}
		
		return dot;
		
	}
	
	public static Matrix multiply(Matrix matrix1, Matrix matrix2){
		
		Matrix product = new Matrix(matrix1.getHeight(), matrix2.getWidth());
		
		for(int x = 0; x < product.getWidth(); x++){
			for(int y = 0; y < product.getHeight(); y++){
				
				double[] row = matrix1.getRow(y);
				double[] col = matrix2.getCol(x);
				
				double attribute = 0;
				
				for(int i = 0; i < row.length; i++){
					attribute += row[i]*col[i];
				}
				
				product.setAttribute(x, y, attribute);
				
			}
		}
		
		
		return product;
		
	}
	
	public static Matrix elementMultiplication(Matrix matrix1, Matrix matrix2){
		
		Matrix output = new Matrix(matrix1.getHeight(), matrix1.getWidth());
		
		for(int x = 0; x < output.getWidth(); x++){
			for(int y = 0; y < output.getHeight(); y++){
				
				output.setAttribute(x, y, matrix1.getAttribute(x, y)*matrix2.getAttribute(x, y));
				
			}
		}
		
		return output;
		
	}
	
	public static Matrix multiply(Matrix matrix, double scaler){
		
		Matrix product = new Matrix(matrix.getMatrix());
		
		for(int x = 0; x < product.getWidth(); x++){
			for(int y = 0; y < product.getHeight(); y++){
				
				product.setAttribute(x, y, product.getAttribute(x, y)*scaler);
				
			}
		}
		
		return product;
		
	}
	
}
