package co.edu.unbosque.taller.dto;

/**
 * Civilización de Age of Empires II. Solo contiene los campos básicos que se
 * envían al front: id, name, expansion y army_type.
 */
public class CivilizationDTO {

	private Integer id;
	private String name;
	private String expansion;
	private String armyType;

	public CivilizationDTO() {
	}

	public CivilizationDTO(Integer id, String name, String expansion, String armyType) {
		super();
		this.id = id;
		this.name = name;
		this.expansion = expansion;
		this.armyType = armyType;
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

	public String getExpansion() {
		return expansion;
	}

	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}

	public String getArmyType() {
		return armyType;
	}

	public void setArmyType(String armyType) {
		this.armyType = armyType;
	}

	@Override
	public String toString() {
		return "CivilizationDTO [id=" + id + ", name=" + name + ", expansion=" + expansion + ", armyType=" + armyType
				+ "]";
	}

}
