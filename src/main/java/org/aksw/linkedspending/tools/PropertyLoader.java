package org.aksw.linkedspending.tools;

import lombok.extern.java.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

/**
 * static helper class for loading properties
 */
@Log public final class PropertyLoader
{
	/** properties */
	private static HashMap<String, Properties>	propertiesList	= new HashMap<>();

	private PropertyLoader()	{}

	/**
	 * creates properties from file
	 *
	 * @param filename
	 *            file name
	 * @return properties
	 */
	private static Properties getProperties(String filename)
	{
		if (!propertiesList.containsKey(filename))
		{
			Properties properties = loadProperties(filename);
			propertiesList.put(filename, properties);
		}
		return propertiesList.get(filename);
	}

	/**
	 * loads properties from file
	 *
	 * @param filename
	 *            file name
	 * @return properties
	 */
	private static Properties loadProperties(String filename)
	{
		Properties properties = new Properties();
		InputStream propertiesFile = null;
		try
		{
			propertiesFile = PropertyLoader.class.getClassLoader().getResourceAsStream(filename);
			if (propertiesFile == null)
			{
				log.severe("Could not open " + filename);
			}
			properties.load(propertiesFile);
		}
		catch (IOException e)
		{
			log.severe("Could not load properties from file: " + e.getMessage());
		}
		finally
		{
			if (propertiesFile != null)
			{
				try
				{
					propertiesFile.close();
				}
				catch (IOException e)
				{
					log.warning("Error closing properties file: " + e.getMessage());
				}
			}
		}
		return properties;
	}

	private static final Properties PROPERTIES = PropertyLoader.getProperties("environmentVariables.properties");
	private static final Properties UPLOAD = PropertyLoader.getProperties("upload.properties");

	public static final URL urlDatasets;
	static
	{
		try {urlDatasets	= new URL(PROPERTIES.getProperty("urlDatasets"));} 	catch (MalformedURLException e) {throw new RuntimeException(e);}
	}

	public static final File pathRdf = new File(PROPERTIES.getProperty("pathRdf"));
	public static final File pathJson = new File(PROPERTIES.getProperty("pathJson"));

	public static final String prefixInstance = PROPERTIES.getProperty("prefixInstance");
	public static final String prefixOntology = PROPERTIES.getProperty("prefixOntology");
	public static final String prefixOpenSpending = PROPERTIES.getProperty("prefixOpenSpending");

	public static final String apiUrl = UPLOAD.getProperty("apiUrl");
	public static final String endpoint = UPLOAD.getProperty("endpoint");
	public static final String graph = UPLOAD.getProperty("graph");
	public static final String jdbcUrl = UPLOAD.getProperty("jdbcUrl");
	public static final String jdbcUser = UPLOAD.getProperty("jdbcUser");
	public static final String jdbcPassword = UPLOAD.getProperty("jdbcPassword");

	public static final int minValuesMissingForStop = Integer.valueOf(PROPERTIES.getProperty("minValuesMissingForStop"));
	public static final int maxValuesMissingLogged = Integer.valueOf(PROPERTIES.getProperty("maxValuesMissingLogged"));
	public static final double datasetMissingStopRatio = Integer.valueOf(PROPERTIES.getProperty("datasetMissingStopRatio"));
	public static final int maxModelTriples = Integer.valueOf(PROPERTIES.getProperty("maxModelTriples"));
}