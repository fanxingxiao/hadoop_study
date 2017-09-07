package com.cmx.bigdata.App.word_count2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class DictSortPartition extends Partitioner<Text, IntWritable>{

	@Override
	public int getPartition(Text key, IntWritable value, int numPartitions) {
		String word = key.toString().toLowerCase();
		//获取单词首字母,全部转换成小写
		char charAt = word.charAt(0);
		//比较判断
		if(charAt>='a'&&charAt<='g')
		return 0;
		else if(charAt>='h'&&charAt<='n')
			return 1;
		else if(charAt>='o'&&charAt<='t')
			return 2;
		else if(charAt>='u'&&charAt<='z')
			return 3;
		return 4;//以数字,等其他字符开头的存进4
	}

}
