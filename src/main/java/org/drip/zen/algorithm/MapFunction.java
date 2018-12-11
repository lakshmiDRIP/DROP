
package org.drip.zen.algorithm;

import java.util.HashMap;

public class MapFunction {

	static String[] states = {
		"CA",
		"CO",
		"FL",
		"MA",
		"MD",
		"NJ",
		"NY",
		"PA",
		"TX",
		"WA"
	};

	static String[] capitals = {
		"Sacramento",
		"Denver",
		"Tallahasseee",
		"Baltimore",
		"Boston",
		"Trenton",
		"Albany",
		"Harrisburg",
		"Austin",
		"Seattle"
	};

	static String StateCapital (String stateCode) {
		int stateIndex = -1;

		for (int i = 0; i < states.length; i = i + 1)
		{
			if (states[i].equalsIgnoreCase (stateCode))
			{
				stateIndex = i;
				break;
			}
		}

		String capitalCity = capitals[stateIndex];
		return capitalCity;
	}

	static HashMap<String, String> StateCapitalMap()
	{
		HashMap<String, String> capitalsMap = new HashMap<String, String>();

		for (int i = 0; i < states.length; i = i + 1)
		{
			capitalsMap.put (states[i], capitals[i]);
		}

		return capitalsMap;
	}

	public static final void main (String[] input)
	{
		String state = "NJ";

		System.out.println ("\t" + state + " => " + StateCapital (state));

		HashMap<String, String> mapOfStateCapitals = StateCapitalMap();

		System.out.println ("\t" + state + " => " + mapOfStateCapitals.get (state));
	}
}
