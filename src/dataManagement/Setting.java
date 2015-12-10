package dataManagement;

public class Setting<T> {
	private final T m_min;
	private final T m_max;
	private T m_value;
	private final String m_desc;
	
	Setting(T value, T min, T max, String description)
	{
		m_value = value;
		m_min = min;
		m_max = max;
		m_desc = description;
	}
	
	Setting(T value, String description)
	{
		m_value = value;
		m_min = null;
		m_max = null;
		m_desc = description;
	}
	
	public boolean setVal(T value)
	{
		if(value instanceof Integer)
		{	
			Integer f = (Integer)value;
			Integer min = (Integer)m_min;
			Integer max = (Integer)m_max;
			if(f.intValue() >= min.intValue() && f.intValue() <= max.intValue())
			{
				m_value = value;
				return true;
			}
			return false;
		}
		else
		{
			m_value = value;
			return true;
		}
	}
	
	public T getVal()
	{
		return m_value;
	}
	
	public String getDescription()
	{
		return m_desc;
	}
}
