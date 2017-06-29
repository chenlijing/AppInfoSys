package cn.appsys.tools;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.core.convert.converter.Converter;

/*实现时间类型转换的工具类*/
public class StringToDateConverter implements Converter<String,Date>{
	private String datePattern;
	public StringToDateConverter(String datePattern){
		this.datePattern=datePattern;
		
	}

	public Date convert(String arg0) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datePattern).parse(arg0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return date;
	}
	

}
