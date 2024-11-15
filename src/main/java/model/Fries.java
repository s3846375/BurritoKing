package model;

public class Fries extends FoodItem{
	private static final int batchPrepTime = 8;
	private static final int batchSize = 5;
	
	public Fries(double price, int quantity) {
		super(price, quantity);
	}

	public static int getBatchPrepTime() {
		return batchPrepTime;
	}
	public static int getBatchSize() {
		return batchSize;
	}
}
