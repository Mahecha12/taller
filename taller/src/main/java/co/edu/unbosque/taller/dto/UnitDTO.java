package co.edu.unbosque.taller.dto;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;

import co.edu.unbosque.taller.dto.adapter.ListaTextoFlexibleAdapter;
import co.edu.unbosque.taller.dto.adapter.TextoFlexibleAdapter;

/**
 * Unidad de Age of Empires II. Contiene los campos básicos y de combate que se
 * envían al front.
 * <p>
 * Se usan tipos envoltorio (Integer, Double) porque la API omite los campos
 * vacíos o en cero; así llegan como null en lugar de un 0 inventado.
 * </p>
 */
public class UnitDTO {

	// Básicos
	private Integer id;
	private String name;
	private String description;
	private String expansion;
	private String age;
	/** Nombre de la estructura donde se crea (p. ej. "Castle"). */
	private String createdIn;

	// Combate
	private Integer hitPoints;
	private Integer attack;
	/** Armadura cuerpo a cuerpo/perforación, p. ej. "0/1" (la API la envía como objeto). */
	@JsonAdapter(TextoFlexibleAdapter.class)
	private String armor;
	/** Alcance, p. ej. "4" o "2.49". */
	@JsonAdapter(TextoFlexibleAdapter.class)
	private String range;
	@JsonAdapter(ListaTextoFlexibleAdapter.class)
	private List<String> attackBonus;
	@JsonAdapter(ListaTextoFlexibleAdapter.class)
	private List<String> armorBonus;
	/** Precisión en porcentaje, p. ej. "80". */
	@JsonAdapter(TextoFlexibleAdapter.class)
	private String accuracy;
	private Double blastRadius;

	public UnitDTO() {
	}

	public UnitDTO(Integer id, String name, String description, String expansion, String age, String createdIn,
			Integer hitPoints, Integer attack, String armor, String range, List<String> attackBonus,
			List<String> armorBonus, String accuracy, Double blastRadius) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.expansion = expansion;
		this.age = age;
		this.createdIn = createdIn;
		this.hitPoints = hitPoints;
		this.attack = attack;
		this.armor = armor;
		this.range = range;
		this.attackBonus = attackBonus;
		this.armorBonus = armorBonus;
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

	public String getArmor() {
		return armor;
	}

	public void setArmor(String armor) {
		this.armor = armor;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public List<String> getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(List<String> attackBonus) {
		this.attackBonus = attackBonus;
	}

	public List<String> getArmorBonus() {
		return armorBonus;
	}

	public void setArmorBonus(List<String> armorBonus) {
		this.armorBonus = armorBonus;
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
				+ ", armor=" + armor + ", range=" + range + ", attackBonus=" + attackBonus + ", armorBonus="
				+ armorBonus + ", accuracy=" + accuracy + ", blastRadius=" + blastRadius + "]";
	}

}
