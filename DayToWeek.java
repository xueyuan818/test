package finaltest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.opencsv.CSVWriter;

public class DayToWeek {
	public static Date addDate(Date d,long day) throws ParseException {
		long time = d.getTime(); 
		day = day*24*60*60*1000; 
		time+=day; 
		return new Date(time);
	}
	public static void main(String[] args) throws IOException, ParseException{
		File input = new File("data.csv");
		File output=new File("Week_Location.csv");
		
		BufferedReader bufRead = new BufferedReader(new FileReader(input));
		CSVWriter writer = new CSVWriter(new FileWriter(output),',',CSVWriter.NO_QUOTE_CHARACTER);
		
		String ids = null;
		List<String[]> title = new ArrayList<String[]>();
		title.add(new String[]{"Week","Year","Keyword match","Location", "Count"});
		writer.writeAll(title);
		while((ids = bufRead.readLine())!=null){
			String[] str = ids.split(",");
			Date d = new Date(1970-01-01); 
			Date newDate = addDate(d,Integer.parseInt(str[0])-1 ); 
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date start = df.parse("2011-05-02");
			if(newDate.after(start)){
				Calendar c = new GregorianCalendar(); 
				c.setTime(newDate);
				int year = c.get(Calendar.YEAR);
				int week = c.get(Calendar.WEEK_OF_YEAR); 

				List<String[]> data = new ArrayList<String[]>();
				data.add(new String[] {week+"",year+"",str[1],str[2], str[3]});
				writer.writeAll(data);
			}
		}
		writer.flush();
		bufRead.close();
		writer.close();
	}
}

