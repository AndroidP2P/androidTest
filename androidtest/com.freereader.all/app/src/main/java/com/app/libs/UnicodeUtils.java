package com.app.libs;
import java.net.URLDecoder;

public class UnicodeUtils
{
  public static String getUnicode(String str)
  {
	  try
	  {
	  String strUTF8 = URLDecoder.decode(str, "UTF-8");  
	  return strUTF8;
	  }catch(Exception e)
	  {
		  return "";
	  }
  }
}
