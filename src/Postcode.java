import java.io.Serializable;

@SuppressWarnings("serial")
public class Postcode implements Serializable {

	// the distance for each postcode
	private int distance;
	// the actual code
	private String postcode;

	public Postcode(String postcode, int distance) {
		this.setDistance(distance);
		this.setPostcode(postcode);
	}

	// getters and setters
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
