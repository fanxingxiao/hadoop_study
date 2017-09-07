package com.souvc.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestIDCardUtil {    
	private static String cid = null;  
	/**    
	 * 根据身份证号求性别    
	 * @throws Exception 
	 * @throws IOException 
	 */      
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("in.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
		while((cid=reader.readLine())!=null){
		if(testCheckCardId()==true){
			List list = testParseGender(cid);
			writer.write(cid+" "+list.get(0).toString()+"\n");
			writer.flush();
		}
		}
		reader.close();
		writer.close();
		} 
//		@Test    
		public static List<String> testParseGender(String cid) throws Exception { 
			List<String> list=new ArrayList<>();
			String gender = IDCardUtil.parseGender(cid);        
			List list1 =testParseAge(cid);
			list.add("性别：" + gender+" "+list1.get(0));  
			return list; 
			}    
		/**    
		 * 判断身份证号是否通过校验    
		 */    
//		@Test    
		public static boolean testCheckCardId() throws Exception {        
			boolean flag = IDCardUtil.checkCardId(cid);
			System.out.println("身份证号是否通过校验：" + flag);
			return flag;
			}    
		/**    
		 * 通过身份证号求得年龄    
		 */    
//		@Test    
		public static List<String> testParseAge(String cid) throws Exception { 
			List<String> list=new ArrayList<>();
			int age = IDCardUtil.parseAge(cid);        
			List list1 =testParseAddress(cid);
			list.add("年龄：" + age+" "+list1.get(0));
			return list;   
			}    
		/**    
		 * 通过身份证号求出生地     
		 */    
//		@Test    
		public static List<String> testParseAddress(String cid) throws Exception {  
			List<String> list=new ArrayList<>();
			String address = IDCardUtil.parseAddress(cid);        
			List list1 =testParseBirthday(cid);
			list.add("出生地：" + address+" "+list1.get(0));
			return list; 
			}        
		/**    
		 * 根据身份证号求出生日期     
		 */    
//		@Test    
		public static List<String> testParseBirthday(String cid) throws Exception {  
			List<String> list=new ArrayList<>();
			String birthday = IDCardUtil.parseBirthday(cid);        
			list.add("出生日期是:" + birthday); 
			return list; 
			}
		}
