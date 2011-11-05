package com.jostrobin.battleships.ui.components;

/**
 * A custom ComboBoxItem class which provides a key and a label because JComboBox only uses a label by default.
 * @author joscht
 *
 */
public class ComboBoxItem
{
	private Object key;
	
	private String label;

	public ComboBoxItem()
	{
	}

	public ComboBoxItem(Object key, String label)
	{
		this.key = key;
		this.label = label;
	}

	public Object getKey()
	{
		return key;
	}

	public void setKey(Object key)
	{
		this.key = key;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	@Override
	public String toString()
	{
		return label;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComboBoxItem other = (ComboBoxItem) obj;
		if (key == null)
		{
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
