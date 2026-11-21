package co.edu.unbosque.taller.dto;

public class ChisteDTO {
	private String setup;
	private String delivery;
	private int statuscode;
	 
	
	public ChisteDTO() {
		// TODO Auto-generated constructor stub
	}


	public ChisteDTO(String setup, String delivery, int statuscode) {
		super();
		this.setup = setup;
		this.delivery = delivery;
		this.statuscode = statuscode;
	}


	public String getSetup() {
		return setup;
	}


	public int getStatuscode() {
		return statuscode;
	}


	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}


	public void setSetup(String setup) {
		this.setup = setup;
	}


	public String getDelivery() {
		return delivery;
	}


	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}


	@Override
	public String toString() {
		return "ChisteDTO [setup=" + setup + ", delivery=" + delivery + ", statuscode=" + statuscode + "]";
	}

}
