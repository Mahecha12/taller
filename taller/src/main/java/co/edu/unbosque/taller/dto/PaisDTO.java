package co.edu.unbosque.taller.dto;

public class PaisDTO {

	private long id;
	private String name;
	private int phonecode;
	private String capital;
	private String latitude;
	private String longitude;
	private String region;
	private String subregion;

	public PaisDTO() {
		// TODO Auto-generated constructor stub
	}

	public PaisDTO(long id, String name, int phonecode, String capital, String latitude, String longitude,
			String region, String subRegion) {
		this.id = id;
		this.name = name;
		this.phonecode = phonecode;
		this.capital = capital;
		this.latitude = latitude;
		this.longitude = longitude;
		this.region = region;
		this.subregion = subRegion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(int phonecode) {
		this.phonecode = phonecode;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubregion() {
		return subregion;
	}

	public void setSubregion(String subRegion) {
		this.subregion = subRegion;
	}


	@Override
	public String toString() {
		return "PaisDTO [id=" + id + ", name=" + name + ", phonecode=" + phonecode + ", capital=" + capital
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", region=" + region + ", subRegion="
				+ subregion + "]";
	}

}
