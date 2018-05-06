package projectCove;

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
