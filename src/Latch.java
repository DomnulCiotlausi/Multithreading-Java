import java.io.Serializable;

@SuppressWarnings("serial")
public class Latch implements Serializable {
	// we latches to notify the other theards
	
	private boolean notified;

	public Latch() {
		this.notified = false;
	}

	public synchronized void latchNotify() {
		notified = true;
		notify();
	}

	public synchronized void latchWait() {
		while (!notified) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
