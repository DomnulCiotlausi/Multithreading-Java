import java.io.IOException;

public class MainThread2 implements Runnable {
	// we use this thread to update all orders
	
	// this thread runs constantly 
	
	private CommunicationLayer cl;

	public MainThread2(CommunicationLayer cl) {
		this.cl = cl;
	}

	@Override
	public void run() {
		while (true) {
			for (Order order : cl.getStockManagement().getAllOrders()) {
				try {
					cl.updateOrder(order);
				} catch (IOException e) {
					System.err.println("Error while updatin onrder nr." + order.getId());
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
