
package org.drip.param.config;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * Copyright (C) 2011 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>ConfigLoader</i> implements the configuration functionality. It exposes the following:
 * 
 * <br><br>
 * 	<ul>
 * 		<li>
 * 			Parses the XML configuration file and extract the tag pairs information.
 * 		</li>
 * 		<li>
 * 			Retrieve the logger.
 * 		</li>
 * 		<li>
 * 			Load the holiday calendars and retrieve the location holidays.
 * 		</li>
 * 		<li>
 * 			Connect to analytics server and the database server.
 * 		</li>
 * 	</ul>
 * 
 * <br><br>
 *	Depending on the configuration setting, ConfigLoader loads the above from either a file or the specified
 *		database.
 *
 *	<br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param">Param</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/param/config">Config</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class ConfigLoader {
	private static boolean s_bInit = false;
	private static java.sql.Statement s_Statement = null;

	private static final int IntegerTagValue (
		final org.w3c.dom.Element eTag,
		final java.lang.String strTag)
		throws java.lang.Exception {
		if (null == eTag || null == strTag || null == eTag.getElementsByTagName (strTag))
			throw new java.lang.Exception ("Cannot get int value for <" + strTag + ">");

		org.w3c.dom.NodeList nl = eTag.getElementsByTagName (strTag);

		if (null == nl.item (0) || !(nl.item (0) instanceof org.w3c.dom.Element))
			throw new java.lang.Exception ("Cannot get int value for <" + strTag + ">");

		org.w3c.dom.Element elem = (org.w3c.dom.Element) nl.item (0);

		if (null == elem || null == elem.getChildNodes() || null == elem.getChildNodes().item (0) ||
			!(elem.getChildNodes().item (0) instanceof org.w3c.dom.Node))
			throw new java.lang.Exception ("Cannot get int value for <" + strTag + ">");

		org.w3c.dom.Node node = elem.getChildNodes().item (0);

		if (null == node || null == node.getNodeValue())
			throw new java.lang.Exception ("Cannot get int value for <" + strTag + ">");

		return Integer.parseInt (node.getNodeValue());
	}

	private static final boolean BooleanTagValue (
		final org.w3c.dom.Element eTag,
		final java.lang.String strTag)
		throws java.lang.Exception
	{
		if (null == eTag || null == strTag || null == eTag.getElementsByTagName (strTag))
			throw new java.lang.Exception ("Cannot get bool value for <" + strTag + ">");

		org.w3c.dom.NodeList nl = eTag.getElementsByTagName (strTag);

		if (null == nl.item (0) || !(nl.item (0) instanceof org.w3c.dom.Element))
			throw new java.lang.Exception ("Cannot get bool value for <" + strTag + ">");

		org.w3c.dom.Element elem = (org.w3c.dom.Element) nl.item (0);

		if (null == elem || null == elem.getChildNodes() || null == elem.getChildNodes().item (0) ||
			!(elem.getChildNodes().item (0) instanceof org.w3c.dom.Node))
			throw new java.lang.Exception ("Cannot get bool value for <" + strTag + ">");

		org.w3c.dom.Node node = elem.getChildNodes().item (0);

		if (null == node || null == node.getNodeValue())
			throw new java.lang.Exception ("Cannot get bool value for <" + strTag + ">");

		return java.lang.Boolean.parseBoolean (node.getNodeValue());
	}

	private static final java.lang.String StringTagValue (
		final org.w3c.dom.Element eTag,
		final java.lang.String strTag)
	{
		if (null == eTag || null == strTag || null == eTag.getElementsByTagName (strTag)) return null;

		org.w3c.dom.NodeList nl = eTag.getElementsByTagName (strTag);

		if (null == nl.item (0) || !(nl.item (0) instanceof org.w3c.dom.Element)) return null;

		org.w3c.dom.Element elem = (org.w3c.dom.Element) nl.item (0);

		if (null == elem || null == elem.getChildNodes() || null == elem.getChildNodes().item (0) ||
			!(elem.getChildNodes().item (0) instanceof org.w3c.dom.Node))
			return null;

		org.w3c.dom.Node node = elem.getChildNodes().item (0);

		if (null == node || null == node.getNodeValue()) return null;

		return node.getNodeValue();
	}

	private static final int[] IntegerArrayTagValue (
		final org.w3c.dom.Element eTag,
		final java.lang.String strTag)
	{
		if (null == eTag || null == strTag || null == eTag.getElementsByTagName (strTag)) return null;

		org.w3c.dom.NodeList nl = eTag.getElementsByTagName (strTag);

		if (!(nl.item (0) instanceof org.w3c.dom.Element)) return null;

		org.w3c.dom.Element elem = (org.w3c.dom.Element) nl.item (0);

		if (null == elem || null == elem.getChildNodes() || null == elem.getChildNodes().item (0) ||
			!(elem.getChildNodes().item (0) instanceof org.w3c.dom.Node))
			return null;

		java.lang.String strValue = elem.getChildNodes().item (0).getNodeValue();

		if (null == strValue || strValue.isEmpty()) return null;

		java.lang.String[] astrValue = strValue.split (",");

		int[] ai = new int[astrValue.length];

		for (int i = 0; i < astrValue.length; ++i)
			ai[i] = Integer.parseInt (astrValue[i]);

		return ai;
	}

	private static final org.w3c.dom.Document NormalizedXMLDoc (
		final java.lang.String strXMLFile)
	{
		if (null == strXMLFile || strXMLFile.isEmpty()) return null;

		org.w3c.dom.Document doc = null;

		try {
			doc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse (new
				java.io.File (strXMLFile));
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		if (null == doc || null == doc.getDocumentElement()) return null;

		doc.getDocumentElement().normalize();

		return doc;
	}

	/**
	 * Create a LocHolidays object from the XML Document and the Location Tag
	 * 
	 * @param doc XML Document
	 * @param strLoc Location Tag
	 * 
	 * @return LocHolidays
	 */

	public static org.drip.analytics.eventday.Locale LocationHolidays (
		final org.w3c.dom.Document doc,
		final java.lang.String strLoc)
	{
		if (null == doc || null == strLoc) return null;

		org.w3c.dom.NodeList nlLoc = doc.getElementsByTagName (strLoc);

		if (null == nlLoc || null == nlLoc.item (0) || org.w3c.dom.Node.ELEMENT_NODE != nlLoc.item
			(0).getNodeType())
			return null;

		org.drip.analytics.eventday.Locale locHols = new org.drip.analytics.eventday.Locale();

		org.w3c.dom.Element e = (org.w3c.dom.Element) nlLoc.item (0);

		org.w3c.dom.NodeList nlHols = e.getElementsByTagName ("Weekend");

		if (null != nlHols && null != nlHols.item (0) && org.w3c.dom.Node.ELEMENT_NODE == nlHols.item
			(0).getNodeType())
			locHols.addWeekend (IntegerArrayTagValue ((org.w3c.dom.Element) nlHols.item (0), "DaysInWeek"));

		if (null != (nlHols = e.getElementsByTagName ("FixedHoliday"))) {
			for (int j = 0; j < nlHols.getLength(); ++j) {
				if (null == nlHols.item (j) || org.w3c.dom.Node.ELEMENT_NODE != nlHols.item
					(j).getNodeType())
					continue;

				org.w3c.dom.Element elemHol = (org.w3c.dom.Element) nlHols.item (j);

				if (null != elemHol) {
					try {
						locHols.addFixedHoliday (IntegerTagValue (elemHol, "Date"), IntegerTagValue (elemHol,
							"Month"), "");
					} catch (java.lang.Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}	

		if (null != (nlHols = e.getElementsByTagName ("FloatingHoliday"))) {
			for (int j = 0; j < nlHols.getLength(); ++j) {
				if (null == nlHols.item (j) || org.w3c.dom.Node.ELEMENT_NODE != nlHols.item
					(j).getNodeType())
					continue;

				org.w3c.dom.Element elemHol = (org.w3c.dom.Element) nlHols.item (j);

				if (null != elemHol) {
					try {
						locHols.addFloatingHoliday (IntegerTagValue (elemHol, "WeekInMonth"), IntegerTagValue
							(elemHol, "WeekDay"), IntegerTagValue (elemHol, "Month"), BooleanTagValue
								(elemHol, "FromFront"), "");
					} catch (java.lang.Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		return locHols;
	}

	/**
	 * Get the logger location from the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return String representing the logger location's full path
	 */

	public static java.lang.String LoggerLocation (
		final java.lang.String strConfigFile)
	{
		org.w3c.dom.Document doc = NormalizedXMLDoc (strConfigFile);

		if (null == doc) return null;

		org.w3c.dom.NodeList nlLogger = doc.getElementsByTagName ("logger");

		if (null == nlLogger || null == nlLogger.item (0) || org.w3c.dom.Node.ELEMENT_NODE !=
			nlLogger.item (0).getNodeType())
			return null;

		return StringTagValue ((org.w3c.dom.Element) nlLogger.item (0), "Location");
	}

	/**
	 * Connect to the analytics server from the connection parameters set in the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return java.net.Socket
	 */

	public static java.net.Socket ConnectToAnalServer (
		final java.lang.String strConfigFile)
	{
		org.w3c.dom.Document doc = NormalizedXMLDoc (strConfigFile);

		if (null == doc) return null;

		org.w3c.dom.NodeList nlLogger = doc.getElementsByTagName ("analserver");

		if (null == nlLogger || null == nlLogger.item (0) || org.w3c.dom.Node.ELEMENT_NODE !=
			nlLogger.item (0).getNodeType())
			return null;

		try {
			return new java.net.Socket (StringTagValue ((org.w3c.dom.Element) nlLogger.item (0), "host"),
				IntegerTagValue ((org.w3c.dom.Element) nlLogger.item (0), "port"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Initialize the analytics server from the connection parameters set in the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return java.net.ServerSocket
	 */

	public static java.net.ServerSocket InitAnalServer (
		final java.lang.String strConfigFile)
	{
		org.w3c.dom.Document doc = NormalizedXMLDoc (strConfigFile);

		if (null == doc) return null;

		org.w3c.dom.NodeList nlLogger = doc.getElementsByTagName ("analserver");

		if (null == nlLogger || null == nlLogger.item (0) || org.w3c.dom.Node.ELEMENT_NODE !=
			nlLogger.item (0).getNodeType())
			return null;

		try {
			return new java.net.ServerSocket (IntegerTagValue ((org.w3c.dom.Element) nlLogger.item (0),
				"port"), IntegerTagValue ((org.w3c.dom.Element) nlLogger.item (0), "maxconn"));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Load the map of the holiday calendars from the entries set in the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return Map of the holiday calendars
	 */

	public static org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale>
		LoadHolidayCalendars (
			final java.lang.String strConfigFile)
		{
		org.w3c.dom.Document doc = NormalizedXMLDoc (strConfigFile);

		if (null == doc) return null;

		org.drip.analytics.eventday.Locale lhNYB = LocationHolidays (doc, "NYB");

		if (null == lhNYB) return null;

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale> mapHols = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale>();

		mapHols.put ("NYB", lhNYB);

		return mapHols;
	}

	/**
	 * Initialize the Oracle database from the connection parameters set in the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return Connection Statement object
	 */

	public static java.sql.Statement OracleInit (
		final java.lang.String strConfigFile)
	{
		if (s_bInit) return s_Statement;

		s_bInit = true;

		org.w3c.dom.Document doc = NormalizedXMLDoc (strConfigFile);

		if (null == doc) return null;

		org.w3c.dom.NodeList nlDBConn = doc.getElementsByTagName ("dbconn");

		if (null == nlDBConn || null == nlDBConn.item (0) || org.w3c.dom.Node.ELEMENT_NODE !=
			nlDBConn.item (0).getNodeType())
			return null;

		org.w3c.dom.Element elemDBConn = (org.w3c.dom.Element) nlDBConn.item (0);

		try {
			java.lang.Class.forName ("oracle.jdbc.driver.OracleDriver");

			java.lang.String strURL = "jdbc:oracle:thin:@//" + StringTagValue (elemDBConn, "host") + ":" +
				StringTagValue (elemDBConn, "port") + "/" + StringTagValue (elemDBConn, "dbname");

			// java.lang.String strURL = "jdbc:oracle:thin:@//localhost:1521/XE";

			System.out.println ("URL: " + strURL);

			java.sql.Connection conn = java.sql.DriverManager.getConnection (strURL, "hr", "hr");

			System.out.println ("Conn: " + conn);

			conn.setAutoCommit (false);

			return s_Statement = conn.createStatement();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Load the map of the holiday calendars from the database settings set in the XML Configuration file
	 * 
	 * @param strConfigFile XML Configuration file
	 * 
	 * @return Map of the holiday calendars
	 */

	public static final org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale>
		LoadHolidayCalendarsFromDB (
			final java.lang.String strConfigFile)
	{
		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale> mapHols = new
			org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale>();

		java.sql.Statement stmt = OracleInit (strConfigFile);

		if (null == stmt) return null;

		long lStart = System.nanoTime();

		try {
			java.sql.ResultSet rs = stmt.executeQuery ("SELECT Location, Holiday FROM Holidays");

			while (null != rs && rs.next()) {
				java.lang.String strLocation = rs.getString ("Location");

				java.util.Date dtSQLHoliday = rs.getDate ("Holiday");

				if (null != dtSQLHoliday) {
					org.drip.analytics.eventday.Locale lh = mapHols.get (strLocation);

					if (null == lh) lh = new org.drip.analytics.eventday.Locale();

					lh.addStaticHoliday (org.drip.analytics.date.DateUtil.CreateFromYMD
						(org.drip.analytics.date.DateUtil.Year (dtSQLHoliday),
							org.drip.analytics.date.DateUtil.Month (dtSQLHoliday),
								org.drip.analytics.date.DateUtil.Year (dtSQLHoliday)), "");

					mapHols.put (strLocation, lh);
				}
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();

			return null;
		}

		int[] aiWeekend = new int[2];
		aiWeekend[1] = org.drip.analytics.date.DateUtil.SUNDAY;
		aiWeekend[0] = org.drip.analytics.date.DateUtil.SATURDAY;

		for (java.util.Map.Entry<java.lang.String, org.drip.analytics.eventday.Locale> me :
			mapHols.entrySet())
			me.getValue().addWeekend (aiWeekend);

		System.out.println ("Loading hols from DB took " + (System.nanoTime() - lStart) * 1.e-06 +
			" m-sec\n");

		return mapHols;
	}

	public static void main (
		final java.lang.String[] astrArgs)
		throws java.lang.Exception
	{
		java.lang.String strConfigFile = "c:\\Lakshmi\\java\\BondAnal\\Config.xml";

		org.drip.analytics.support.CaseInsensitiveTreeMap<org.drip.analytics.eventday.Locale> mapHols =
			LoadHolidayCalendars (strConfigFile);

		for (java.util.Map.Entry<java.lang.String, org.drip.analytics.eventday.Locale> me :
			mapHols.entrySet())
			System.out.println (me.getKey() + "=" + me.getValue());

		System.out.println ("Logger: " + LoggerLocation (strConfigFile));
	}
}
