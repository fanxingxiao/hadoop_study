package com.cmx.bigdata.App.temp_analyse_hadoop.fenxi;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CountMapper extends Mapper<LongWritable,Text,MyKey,MyValue>{
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, MyKey, MyValue>.Context context)
			throws IOException, InterruptedException {
		//上海    上海    2011-01-01      6       2       4       4       多云~雨夹雪 北风 4-5级
		String line = value.toString();
		//上海    上海	2011-01	     6       2       4       4
		String[] split = line.split("[\\s]+");
		//构造mykey
		MyKey myKey = new MyKey();
		myKey.setCity(split[0]+" "+split[1]);
		String[] split2 = split[2].split("-");
		myKey.setMonth(split[0]+" "+split2[1]);
		//构造myvalue
		MyValue myValue = new MyValue();
		myValue.setMax(Integer.parseInt(split[3]));
		myValue.setDiff(Integer.parseInt(split[5]));
		myValue.setMin(Integer.parseInt(split[4]));
		myValue.setAvg(Integer.parseInt(split[6]));
		context.write(myKey, myValue);
		context.getCounter("count_by_city_month", "mapper").increment(1);
	}
}
