package dataManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class SettingsManager {
	private final HashMap<String,Setting> l_settings;
	private final String m_startconfig = "autoexec.cfg";
	private final String m_configstorage = "config.cfg";
	private final String m_seperator = "=";
	
	public SettingsManager()
	{
		l_settings = new HashMap<String, Setting >();
		init();
		System.out.println("sucessfull init of Settings");
	}
	
	
	private void init()
	{
		/*Syntax: Integer UND Boolean werte: ** VALUE, MIN_VALUE, MAX_VALUE, DESCRIPTION **
				  Without limits:			 ** VALUE, DESCRIPTION **
		*/
		//TODO: Datei Einlesen & stuff
		//Incoming Konsole
		//Init default stuff
		
		//player
		l_settings.put("cl_p_everynumbertest", new Setting(1, "you could give this one every value of an int"));
		//sound
		l_settings.put("cl_s_sound", new Setting(1, 0, 1, "Sounds are generally on/off"));
		l_settings.put("cl_s_volume", new Setting(10, 0, 20, "loudness of the sound"));
		
		//video
		l_settings.put("cl_v_screen_w", new Setting(620, 500, 4096, "Breite des bildschirms"));
		l_settings.put("cl_v_screen_h", new Setting(620, 500, 4096, "Hoehe des bildschirms"));
		
		
		
		//load Data from file
		loadData(m_startconfig);
		loadData(m_configstorage);
	}
	
	/**
	 * Very dangerous for Strings and integer
	 * @param trigger
	 * @param value
	 */
	public boolean setValue(String trigger, int value)
	{
		if(!l_settings.get(trigger).setVal(value))
		{
			System.out.println("value of '"+trigger+"' unsuccessfully changed!");
			return false;
		}
		System.out.println("value of '"+trigger+"' successfully changed to '"+value+"'!");
		saveData();//save the config after changing by user
		return true;
	}
	
	/**
	 * Only used to update the Settings by the startconfig or by the config storage
	 * @param trigger
	 * @param value
	 * @return
	 */
	private boolean setValueSilent(String trigger, int value)
	{
		return l_settings.get(trigger).setVal(value);
	}
	
	/**
	 * dangerous for strings!
	 * @param trigger
	 * @return
	 */
	public boolean getValueBool(String trigger)
	{
		return l_settings.get(trigger).getVal() != 0;
	}
	
	/**
	 * dangerous for strings!
	 * @param trigger
	 * @return
	 */
	public int getValueInt(String trigger)
	{
		return l_settings.get(trigger).getVal();
	}
	
	/**
	 * Switches Value from true to false, or from false to true
	 * @param trigger
	 */
	public void switchValue(String trigger)
	{
		setValue(trigger, getValueBool(trigger) ? 0 : 1);  
	}
	
	/**
	 * Returns minimal value, max value and the description of a Setting
	 * @param trigger
	 * @return
	 */
	public String help(String trigger)
	{
		Setting tmp = l_settings.get(trigger);
		return "min: "+tmp.m_min+"max: "+tmp.m_max+"\n"+tmp.getDescription();
	}
	
	/**
	 * changes the data from an execute file
	 */
	public void loadData(String filename)
	{
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			//File doesn't exist? generate simply one!
			System.out.println("can't find '"+filename+"'\ncreating new one");
			saveData(filename);
			return;//nothing to do anymore, return!
		}
		while(sc.hasNext())
		{
			String line = sc.nextLine();
			String args[] = line.split(m_seperator);
			if(args.length != 2)
			{
				System.out.println("can't interprete '"+line+"' in '"+filename+"'");
				continue;
			}
			int a = 0;
			try {
				a = Integer.parseInt(args[1].trim());
			} catch(NumberFormatException e)
			{
				System.out.println("can't interprete '"+args[1]+"' to a number");
				continue;
			}
			if(!setValueSilent(args[0].trim(), a))
			{
				System.out.println("the value '"+a+"' of '"+args[0].trim()+"' is out of range!");
			}
		}
		sc.close();
	}
	
	/**
	 * Saves the Settings into the default Storage
	 * autoexec just gets loaded (startsettings) while the 
	 * main config gets loaded and written back!
	 */
	public void saveData()
	{
		saveData(m_configstorage);
	}
	
	private void saveData(String filename)
	{//TODO fix this
		FileWriter f = null;
		try {
			f = new FileWriter(new File(filename), false);
			BufferedWriter bw = new BufferedWriter(f);
			Iterator<Entry<String, Setting>> it = l_settings.entrySet().iterator();
			
		    while (it.hasNext()) {
		        Entry<String, Setting> pair = it.next();
		        //System.out.println(pair.getKey() + " = " + pair.getValue().getVal());
		        bw.write(pair.getKey()+m_seperator+pair.getValue().getVal());
		        bw.newLine();
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Setting 
	{
		private final int m_min;
		private final int m_max;
		private int m_value;
		private final String m_desc;
		
		Setting(int value, int min, int max, String description)
		{
			m_value = value;
			m_min = min;
			m_max = max;
			m_desc = description;
		}
		
		Setting(int value, String description)
		{
			m_value = value;
			m_min = Integer.MIN_VALUE;
			m_max = Integer.MAX_VALUE;
			m_desc = description;
		}
		
		public boolean setVal(int value)
		{
			if(m_value >= m_min && m_value <= m_max)
			{
				m_value = value;
				return true;
			}
			return false;
		}
		
		public int getVal()
		{
			return m_value;
		}
		
		public String getDescription()
		{
			return m_desc;
		}
	}

}
