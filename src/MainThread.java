
public class MainThread implements Runnable {
	// we use this thread to check the following cases:
	// 1. if a dish is ready to be made
	// 2. if an ingredient is below the stock level
	// 3. if an order is ready to be dispatched
	
	// this thread runs constantly 
	
	private StockManagement sm;

	public MainThread(StockManagement sm) {
		this.sm = sm;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sm.checkDish();
				sm.checkIngredients();
				sm.checkIfOrderIsReady();
			} catch (Exception e1) {
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}
