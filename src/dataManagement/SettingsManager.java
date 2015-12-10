package dataManagement;

import java.util.HashMap;

public class SettingsManager {
	private final HashMap<String, Setting<Object> > l_settings;
	
	public SettingsManager()
	{
		l_settings = new HashMap<String, Setting<Object> >();
		init();
	}
	
	
	public void init()
	{
		/*Syntax: Integer UND Boolean werte: ** VALUE, MIN_VALUE, MAX_VALUE, DESCRIPTION **
				  String					 ** VALUE, DESCRIPTION **
		*/
		//TODO: Datei Einlesen & stuff
		//Incoming Konsole
		
		//player
		l_settings.put("cl_p_name", new Setting<Object>("Rudolf", "Name of the main player"));
		
		
		//sound
		l_settings.put("cl_s_sound", new Setting<Object>(1, 0, 1, "Sounds are generally on/off"));
		l_settings.put("cl_s_volume", new Setting<Object>(10, 0, 20, "loudness of the sound"));
		
		//video
		l_settings.put("cl_v_screen_w", new Setting<Object>(620, 500, 4096, "Breite des bildschirms"));
		l_settings.put("cl_v_screen_h", new Setting<Object>(620, 500, 4096, "Hoehe des bildschirms"));
		
		
		
		
	}
	
	/**
	 * dangerous for strings!
	 * @param trigger
	 * @return
	 */
	public boolean getValueBool(String trigger)
	{
		return (int)l_settings.get(trigger).getVal() != 0;
	}
	
	/**
	 * dangerous for strings!
	 * @param trigger
	 * @return
	 */
	public int getValueInt(String trigger)
	{
		return (int)l_settings.get(trigger).getVal();
	}
	
	/**
	 * dangerous for int!
	 * @param trigger
	 * @return
	 */
	public String getValueString(String trigger)
	{
		return (String)l_settings.get(trigger).getVal();
	}
	
	public String help(String trigger)
	{
		return l_settings.get(trigger).getDescription();
	}
}
