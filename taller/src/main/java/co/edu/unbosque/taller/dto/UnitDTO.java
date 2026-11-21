package co.edu.unbosque.taller.dto;

public class UnitDTO {

	private Integer id;
	private String name;
	private String description;
	private String expansion;
	private String age;
	private String createdIn;

	private Integer hitPoints;
	private Integer attack;
	private String range;
	private String accuracy;
	private Double blastRadius;

	public UnitDTO() {
	}

	public UnitDTO(Integer id, String name, String description, String expansion, String age, String createdIn,
			Integer hitPoints, Integer attack, String range, String accuracy, Double blastRadius) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.expansion = expansion;
		this.age = age;
		this.createdIn = createdIn;
		this.hitPoints = hitPoints;
		this.attack = attack;
		this.range = range;
		this.accuracy = accuracy;
		this.blastRadius = blastRadius;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(String createdIn) {
		this.createdIn = createdIn;
	}

	public Integer getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(Integer hitPoints) {
		this.hitPoints = hitPoints;
	}

	public Integer getAttack() {
		return attack;
	}

	public void setAttack(Integer attack) {
		this.attack = attack;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public Double getBlastRadius() {
		return blastRadius;
	}

	public void setBlastRadius(Double blastRadius) {
		this.blastRadius = blastRadius;
	}

	@Override
	public String toString() {
		return "UnitDTO [id=" + id + ", name=" + name + ", description=" + description + ", expansion=" + expansion
				+ ", age=" + age + ", createdIn=" + createdIn + ", hitPoints=" + hitPoints + ", attack=" + attack
				+ ", range=" + range + ", accuracy=" + accuracy + ", blastRadius=" + blastRadius + "]";
	}

}
