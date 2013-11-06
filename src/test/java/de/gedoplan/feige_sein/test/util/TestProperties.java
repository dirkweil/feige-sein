package de.gedoplan.feige_sein.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestProperties
{
  public static final Properties TEST_PROP = new Properties();
  static
  {
    try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.properties");)
    {
      TEST_PROP.load(inputStream);
    }
    catch (IOException e)
    {
      // ignore
    }
  }

  public static String getProperty(String key)
  {
    return TEST_PROP.getProperty(key);
  }

  private TestProperties()
  {
  }

  public static String getProperty(String key, String defaultValue)
  {
    return TEST_PROP.getProperty(key, defaultValue);
  }
}
