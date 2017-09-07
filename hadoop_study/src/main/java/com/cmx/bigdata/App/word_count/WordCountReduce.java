package com.cmx.bigdata.App.word_count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/**
 *reduce类 
 * 
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
	/*
	 * 第一个Text---String---单词-----map的输出key类型,reduce的输入key类型
	 * 第一个IntWritable---int---单词个数----map的输出value类型，reduce的输入value类型
	 * 
	 * 
	 * 第二个Text---String---单词-----reduce的输出key类型
	 * 第二个IntWritable---int---单词个数----reduce的输出value类型
	 * 
	 */
	@Override
	protected void reduce(Text word, Iterable<IntWritable> arg1,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException{
		//word --- Text类型 --- 单词
		//arg1---  Intable<IntWritable> ----集合-----单词个数的集合<1,1,1,1,1,1,1,1>
		//context --- 上下文 --- 将结果写出去
		int sum=0;
		for(IntWritable intWritable:arg1){
			//IntWritable类型转换成 int类型对象
			int i = intWritable.get();
			sum+=i;
		}
		//int 类型转换成intWritable对象
		context.write(word, new IntWritable(sum));
		//reduce 计算传输数据的数量
		context.getCounter("wordcount","reduce").increment(1);
	}
}
