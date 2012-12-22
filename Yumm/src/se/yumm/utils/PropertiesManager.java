package se.yumm.utils;

public class PropertiesManager {
	private static PropertiesManager m_instance = null;
	
	public int m_windowWidth;
	public int m_windowHeight;
	public int m_maxItems;
	
	private PropertiesManager(){}
	
	public static synchronized PropertiesManager GetInstance()
	{
		if(m_instance == null)
		{
			m_instance = new PropertiesManager();
			
		}
		return m_instance;
	}
}
