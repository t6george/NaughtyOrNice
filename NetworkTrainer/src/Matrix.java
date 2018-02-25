public class Matrix {
	
	private double[][] matrix;
	
	private int width, height;
	
	public Matrix(int height, int width){
		
		matrix = new double[height][width];
		this.width = width;
		this.height = height;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				matrix[y][x] = 0;
			}
		}
		
	}
	
	public Matrix(double[][] matrix){
		
		this.matrix = (double[][])matrix.clone();
		if(matrix.length != 0){
			height= matrix.length;
			width = matrix[0].length;
		}else width = height = 0;
		
	}
	
	public static Matrix rowMatrix(double[] matrixData){
		
		Matrix matrix = new Matrix(1, matrixData.length);
		
		matrix.setRow(0, matrixData);
		
		return matrix;
		
	}
	
	public static Matrix colMatrix(double[] matrixData){
		
		Matrix matrix = new Matrix(matrixData.length, 1);
		
		matrix.setCol(0, matrixData);
		
		return matrix;
		
	}
	
	public double getAttribute(int x, int y){
		return matrix[y][x];
	}
	
	public void setAttribute(int x, int y, double value){
		matrix[y][x] = value;
	}
	
	public double[] getRow(int row){
		return matrix[row];
	}
	
	public void setRow(int row, double[] data){
		matrix[row] = data;
	}
	
	public void printRow(int row){
		
		for(int i = 0; i < width-1; i++){
			System.out.print(matrix[row][i] + ", ");
		}
		
		System.out.println(matrix[row][width-1]);
		
	}
	
	public double[] getCol(int col){
		double[] colData = new double[height];
		
		for(int i = 0; i < height; i++){
			colData[i] = matrix[i][col];
		}
		
		return colData;
	}
	
	public void setCol(int col, double[] rowData){
		
		for(int i = 0; i < height; i++){
			matrix[i][col] = rowData[i];
		}
		
	}
	
	public void printCol(int col){
		
		for(int i = 0; i < height-1; i++){
			System.out.print(matrix[i][col] + ", ");
		}
		
		System.out.println(matrix[height-1][col]);
		
	}
	
	public void printMatrix(){
		
		for(int i = 0; i < height; i++){
			printRow(i);
		}
		
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
		if(matrix.length != 0){
			height= matrix.length;
			width = matrix[0].length;
		}else width = height = 0;
	}
	
	public double[] getSingleArray(){
		
		double[] array = new double[getLength()];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				array[y + x*height] = matrix[y][x];
			}
		}
		
		return array;
		
	}
	
	public Matrix getTransposition(){
		
		double[][] transposition = new double[width][height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				
				transposition[x][y] = matrix[y][x];
				
			}
		}
		
		return new Matrix(transposition);
		
	}
	
	public Matrix deleteRow(int row){
		
		Matrix m = new Matrix(width, height-1);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int usey = (y < row)?y:y-1;
				if(y == row)continue;
				m.setAttribute(x, usey, matrix[y][x]);
			}
		}
		
		return m;
		
	}
	
	public Matrix deleteCol(int col){
		
		Matrix m = new Matrix(width-1, height);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int usex = (x < col)?x:x-1;
				if(y == col)continue;
				m.setAttribute(usex, y, matrix[y][x]);
			}
		}
		
		return m;
		
	}
	
	public void randomize(double min, double max){
		
		double size = (max-min);
		double offset = min;
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				matrix[y][x] = Math.random()*size + offset;
			}
		}
		
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getLength(){
		return width * height;
	}
}
