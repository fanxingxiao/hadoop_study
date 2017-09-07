package com.cmx.bigdata.App.word_count2s;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 *mapper类 
 */
public class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
	/**
	 * 数据的输入
	 * LongWritable---long    行首字节在文件中的偏移量,输入key
	 * Text ----------String  一行文本,输入value
	 * 
	 * 数据的输出
	 * Test ----------String  单词,输出key
	 * IntWritable----int     单词的个数,输出value
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private String place;
	private Set<String> contrast = new HashSet<String>();
	@Override
	protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		Configuration conf = context.getConfiguration();
		place = conf.get("word.case.sensitive","false");
		
		String fileName = new Path(context.getCacheFiles()[0]).getName();
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String tmp = null;
		while((tmp=reader.readLine())!=null){
			String[] split = tmp.split("\\W+");
			for(String contrastWord : split){
				if(!"".equals(contrastWord)){
					contrast.add(contrastWord);
				}
			}
		}
		reader.close();
	}
	@Override
	protected void map(LongWritable key,Text value,Mapper<LongWritable, Text, Text,IntWritable>.Context context) throws IOException, InterruptedException{
		//key --- 偏移量,一般不太用
		//value --- 一行文本
		//context --- 上下文,通过他能将数据写出去
		String line=null;
		if("true".equals(place)){
			 line= value.toString().toLowerCase();//把一个text对象装成String对象
		}else{
			line = value.toString();//把一个text对象装成String对象
		}
		String [] split = line.split("\\W+");//将一行文本切分成单词数组,包含数字
		for(String word:split){
			if(!(contrast.contains(word)))
			//把每一个单词写出去
			context.write(new Text(word),new IntWritable(1));//将一个字符串变成Text类型,将一个int变成IntWritable类型
			//第一个参数是单词,Text类型
			//第二个参数是个数,此处是1,intWritable类型
		}
	}
}
