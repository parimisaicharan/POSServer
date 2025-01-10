/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * USAGE
 * This class file is used to Handle XML Dates and Calendar
 * 
 * 
 * 
 * 
 */
package ISRetail.Helpers;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


public final class XMLCalendar extends XMLGregorianCalendar
{
  public final static short DATETIME = 0;
  public final static short TIME = 1;
  public final static short DATE = 2;
  public final static short GYEARMONTH = 3;
  public final static short GYEAR = 4;
  public final static short GMONTHDAY = 5;
  public final static short GDAY = 6;
  public final static short GMONTH = 7;
  
  protected static final String [] XML_SCHEMA_TYPES =
    {
      "dateTime",
      "time",
      "date",
      "gYearMonth",
      "gYear",
      "gMonthDay",
      "gDay",
      "gMonth"
    };
  
  public final static int EQUALS = 0;
  public final static int LESS_THAN = -1;
  public final static int GREATER_THAN = 1;
  public final static int INDETERMINATE = 2;
  
  final short dataType;
  final private XMLGregorianCalendar xmlGregorianCalendar;
  private Date date;
  
  static final DatatypeFactory datatypeFactory;
  static
  {
    try
    {
      datatypeFactory = DatatypeFactory.newInstance();
    }
    catch (DatatypeConfigurationException exception)
    {
      throw new RuntimeException(exception);
    }
  }
  
  protected static final DateFormat [] EDATE_FORMATS =
  {
    new SafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    new SafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
    new SafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S"), 
    new SafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S"),
    new SafeSimpleDateFormat("yyyy-MM-dd"),
    new SafeSimpleDateFormat("yyyy-MM-dd")
  };

  static
  {
    EDATE_FORMATS[0].setTimeZone(TimeZone.getTimeZone("GMT"));
    EDATE_FORMATS[3].setTimeZone(TimeZone.getTimeZone("GMT"));    
  }

 /**
 * Constructor
 */
  private XMLCalendar(XMLGregorianCalendar xmlGregorianCalendar, Date date, short dataType)
  {
    this.xmlGregorianCalendar = xmlGregorianCalendar;
    this.date = date;
    this.dataType = dataType;
  }  
 
/**
 * Constructor
 */
  public XMLCalendar(Date date)
  {
    this.xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(XMLCalendar.EDATE_FORMATS[4].format(date));
    this.dataType = 0;
    this.date = date;
  }
  
  
 /**
 * To compare two XML Calendar Values
 */
  public static int compare(XMLCalendar value1, XMLCalendar value2)
  {
    switch (value1.xmlGregorianCalendar.compare(value2.xmlGregorianCalendar))
    {
      case DatatypeConstants.EQUAL:
      {
        return XMLCalendar.EQUALS;
      }
      case DatatypeConstants.LESSER:
      {
        return XMLCalendar.LESS_THAN;
      }
      case DatatypeConstants.GREATER:
      {
        return XMLCalendar.GREATER_THAN;
      }
      default:
      {
        return XMLCalendar.INDETERMINATE;
      }
    }
  }

  @Override
  public boolean equals(Object object)
  {
    return 
      object instanceof XMLCalendar ? 
         xmlGregorianCalendar.equals(((XMLCalendar)object).xmlGregorianCalendar) :
         object instanceof XMLGregorianCalendar && xmlGregorianCalendar.equals(object);
  }

  @Override
  public int hashCode()
  {
    return xmlGregorianCalendar.hashCode();
  }
  
  @Override
  public String toString()
  {
    return xmlGregorianCalendar.toString();
  }

  
 /**
 * To get the Date from the XML Calendar
 */
  public Date getDate()
  {
    if (date == null)
    {
      try
      {
        if (dataType == XMLCalendar.DATETIME)
        {
          try
          {
            date = XMLCalendar.EDATE_FORMATS[0].parse(toXMLFormat());
          }
          catch (Exception e)
          {
            try
            {
              date = XMLCalendar.EDATE_FORMATS[1].parse(toXMLFormat());
            }
            catch (Exception e2)
            {
              try
              {
                date = XMLCalendar.EDATE_FORMATS[2].parse(toXMLFormat());
              }
              catch (Exception e3)
              {
                date = XMLCalendar.EDATE_FORMATS[3].parse(toXMLFormat());
              }
            }
          }
        }
        else if (dataType == XMLCalendar.DATE)
        {
          try
          {
            date = XMLCalendar.EDATE_FORMATS[4].parse(toXMLFormat());
          }
          catch (Exception e)
          {
            date = XMLCalendar.EDATE_FORMATS[5].parse(toXMLFormat());
          }
        }
      }
      catch (Exception e)
      {
       
      }
    }
    return date;
  }

  private static class SafeSimpleDateFormat extends SimpleDateFormat
  {
    private static final long serialVersionUID = 1L;

    public SafeSimpleDateFormat(String pattern)
    {
      super(pattern, Locale.ENGLISH);
    }

    @Override
    public synchronized Date parse(String source) throws ParseException
    {
      return super.parse(source);
    }
  }

  @Override
  public void add(Duration duration)
  {
    xmlGregorianCalendar.add(duration);
    date = null;
  }

  @Override
  public void clear()
  {
    xmlGregorianCalendar.clear();
    date = null;
  }

  @Override
  public Object clone()
  {
    return new XMLCalendar(xmlGregorianCalendar, date, dataType);
  }

  @Override
  public int compare(XMLGregorianCalendar xmlGregorianCalendar)
  {
    return
      this.xmlGregorianCalendar.compare
       (xmlGregorianCalendar instanceof XMLCalendar ? ((XMLCalendar)xmlGregorianCalendar).xmlGregorianCalendar : xmlGregorianCalendar);
  }

  @Override
  public int getDay()
  {
    return xmlGregorianCalendar.getDay();
  }

  @Override
  public BigInteger getEon()
  {
    return xmlGregorianCalendar.getEon();
  }

  @Override
  public BigInteger getEonAndYear()
  {
    return xmlGregorianCalendar.getEonAndYear();
  }

  @Override
  public BigDecimal getFractionalSecond()
  {
    return xmlGregorianCalendar.getFractionalSecond();
  }

  @Override
  public int getHour()
  {
    return xmlGregorianCalendar.getHour();
  }

  @Override
  public int getMinute()
  {
    return xmlGregorianCalendar.getMinute();
  }

  @Override
  public int getMonth()
  {
    return xmlGregorianCalendar.getMonth();
  }

  @Override
  public int getSecond()
  {
    return xmlGregorianCalendar.getSecond();
  }

  @Override
  public TimeZone getTimeZone(int defaultTimeZone)
  {
    return xmlGregorianCalendar.getTimeZone(defaultTimeZone);
  }

  @Override
  public int getTimezone()
  {
    return xmlGregorianCalendar.getTimezone();
  }

  @Override
  public QName getXMLSchemaType()
  {
    return xmlGregorianCalendar.getXMLSchemaType();
  }

  @Override
  public int getYear()
  {
    return xmlGregorianCalendar.getYear();
  }

  @Override
  public boolean isValid()
  {
    return xmlGregorianCalendar.isValid();
  }

  @Override
  public XMLGregorianCalendar normalize()
  {
    return xmlGregorianCalendar.normalize();
  }

  @Override
  public void reset()
  {
    date = null;
    xmlGregorianCalendar.reset();
  }

  @Override
  public void setDay(int day)
  {
    xmlGregorianCalendar.setDay(day);
  }

  @Override
  public void setFractionalSecond(BigDecimal fractionalSecond)
  {
    xmlGregorianCalendar.setFractionalSecond(fractionalSecond);
  }

  @Override
  public void setHour(int hour)
  {
    xmlGregorianCalendar.setHour(hour);
  }

  @Override
  public void setMillisecond(int millisecond)
  {
    xmlGregorianCalendar.setMillisecond(millisecond);
  }

  @Override
  public void setMinute(int minute)
  {
    xmlGregorianCalendar.setMinute(minute);
  }

  @Override
  public void setMonth(int month)
  {
    xmlGregorianCalendar.setMonth(month);
  }

  @Override
  public void setSecond(int second)
  {
    xmlGregorianCalendar.setSecond(second);
  }

  @Override
  public void setTimezone(int offset)
  {
    xmlGregorianCalendar.setTimezone(offset);
  }

  @Override
  public void setYear(BigInteger year)
  {
    xmlGregorianCalendar.setYear(year);
  }

  @Override
  public void setYear(int year)
  {
    xmlGregorianCalendar.setYear(year);
  }

  @Override
  public GregorianCalendar toGregorianCalendar()
  {
    return xmlGregorianCalendar.toGregorianCalendar();
  }

  @Override
  public GregorianCalendar toGregorianCalendar(TimeZone timeZone, Locale locale, XMLGregorianCalendar defaults)
  {
    return xmlGregorianCalendar.toGregorianCalendar(timeZone, locale, defaults);
  }

  @Override
  public String toXMLFormat()
  {
    return xmlGregorianCalendar.toXMLFormat();
  }
}

