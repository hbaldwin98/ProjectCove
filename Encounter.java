package projectCove;
/* Hunter Baldwin
 * Encounter details a certain encounter. It includes the particular monster
 * level, the type of encounter, the description of the encounter, and whether it is active or not.
 * These are all manually entered and stored in the player class.
 */
public class Encounter
{
	private String type;
	private String description;
	private int monsterLevel;
	private boolean active;

	Encounter()
	{
		type = "combat";
		description = "none";
		monsterLevel = 1;
		active = true;
	}

	/***
	 * Takes in the type, description of that type, a monster level (if a monster, otherwise set to 0),
	 * and whether that encounter is active or not.
	 */
	Encounter(String type, String description, int monsterLevel, boolean active)
	{
		this.type = type;
		this.description = description;
		this.monsterLevel = monsterLevel;
		this.active = active;
	}

	public String getType()
	{
		return type;
	}

	public String getDescription()
	{
		return description;
	}

	public int getMonsterLevel()
	{
		return monsterLevel;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setMonsterLevel(int monsterLevel)
	{
		this.monsterLevel = monsterLevel;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	@Override
	public String toString()
	{
		return "Encounter [type=" + type + ", description=" + description + ", monsterLevel=" + monsterLevel
				+ ", active=" + active + "]";
	}

}
