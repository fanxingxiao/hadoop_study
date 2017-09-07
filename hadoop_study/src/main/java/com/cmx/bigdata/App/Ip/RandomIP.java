package com.cmx.bigdata.App.Ip;


	import java.io.BufferedWriter;
	import java.io.FileWriter;
	import java.util.Random;

	import org.junit.Test;

	public class RandomIP {
		private String pattern="%d.%d.%d.%d %s";
		@Test
		public void test001() throws Exception{
			BufferedWriter writer=new BufferedWriter(new FileWriter("E:/test/LinuxTest/b.txt"));
			for(long i=0;i<1000000;i++){
				String string=String.format(pattern, getId(),getId(),getId(),getId(),getName());
				writer.write(string+"\n");
//				System.out.println(string);
			}
			writer.close();
		}
		Random ran=new Random();
		public int getId(){
			return ran.nextInt(255);
		}
		public String getName(){
//			return ""+(char)(Math.random() * 26 + 'a')+(char)(Math.random() * 26 + 'a')+(char)(Math.random() * 26 + 'a')+(char)(Math.random() * 26 + 'a');
			return ""+(char)(ran.nextInt(26) + 'a')+(char)(ran.nextInt(26) + 'a')+(char)(ran.nextInt(26) + 'a')+(char)(ran.nextInt(26) + 'a');
		}
}
