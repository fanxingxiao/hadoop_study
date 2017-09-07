package com.cmx.bigdata.App.word_count2;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * application
 * 入口类，只有main方法
 * 
 * 
 * @author zdjy
 * 在集群上运行程序:
 * 1.上传jar文件和spring.txt文件
 * 2.创建in/out目录
 * 3.打开进程
 * 4.hadoop jar ... .jar com.cmx.bigdata.App.word_count.Application ../in  ../out
 *
 */
public class Application {
	public static void main(String[] args) throws Exception {
		//main方法的参数是一个字符数组,他的第一个值就是第一个参数,第二个值就是第二个参数
		//args[0]/args[1]
		
		//构造Job对象
		Job job=Job.getInstance();
		
		//设置任务名,任务类
		job.setJobName("word_count");
		job.setJarByClass(Application.class);
		
		//设置输入
//		TextInputFormat .setInputPaths(job, new Path("E:/test/word_count/in"));//路径
		TextInputFormat.setInputPaths(job, new Path(args[0]));//路径
		job.setInputFormatClass(TextInputFormat.class);
		
		//设置map
		job.setMapperClass(WordCountMapper.class);//设置map类
		job.setMapOutputKeyClass(Text.class);//设置map输出key
		job.setMapOutputValueClass(IntWritable.class);//设置map输出value
		
		//设置reduce
		job.setReducerClass(WordCountReduce.class);
		job.setOutputKeyClass(Text.class);//设置reduce输出key
		job.setOutputValueClass(IntWritable.class);//设置reduce输出value
		
		//设置输出
//		TextOutputFormat.setOutputPath(job, new Path("E:\\test\\word_count\\out\\"+System.currentTimeMillis()));//设置输出路径
		TextOutputFormat.setOutputPath(job, new Path(args[1]+"/"+System.currentTimeMillis()));//设置输出路径
		job.setOutputFormatClass(TextOutputFormat.class);//写出格式
		
		//提交任务
//		job.submit();
		//提交任务,等待任务结束,打印统计信息
		//正常结束返回true,非正常解释返回false
		System.out.println(job.waitForCompletion(true)?0:1);
	}
}
