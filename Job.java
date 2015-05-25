import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Job {
	LinkedHashMap<String, String> mapOutput;
	StringBuffer s;
	public Job()
	{
		mapOutput= new LinkedHashMap<String, String>();
		s= new StringBuffer();
	}
	public LinkedHashMap<String, String> map(String regex, byte[] content, String name)
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content)));
		String str="";
		Pattern pattern = Pattern.compile(regex);
		try {
			while((str=br.readLine())!=null)
			{
				Matcher matcher= pattern.matcher(str);
				if(matcher.find())
				{
					mapOutput.put(str, name);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return mapOutput;
	}
}
