package model;

public class Burrito extends FoodItem{
	private static final int batchPrepTime = 9;
	private static final int batchSize = 2;
	public Burrito(double price, int quantity) {
		super(price, quantity);
	}

	public static int getBatchPrepTime() {
		return batchPrepTime;
	}
	public static int getBatchSize() {
		return batchSize;
	}
}
