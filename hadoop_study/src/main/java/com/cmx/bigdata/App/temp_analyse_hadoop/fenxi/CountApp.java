package com.cmx.bigdata.App.temp_analyse_hadoop.fenxi;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


/**
 * 聚合任务
 * 将上一步ETL任务处理结果进行聚合
 * 按照城市和月份的维度进行聚合,计算出每一城市每一个月的最高最低平均气温和最高最低平均温差
 * 将温度大数据转换成相对较少的数据量
 * 任务的结果导入到MySQL中提供web开发人员做报表展示
 * 
 */

/**
	 *
	 * 上海 上海 2011-01-01 6 2 4 4 多云~雨夹雪 北风 4-5级
	 *
	 * 
	 *
	 *
	 * select max(..),min(...) group by 月份 ，城市
	 *
	 *
	 * 按照城市和月份进行聚合统计（最高气温，最低气温，平均气温，最高温差，最低温差，平均温差）
	 *
	 * 上海 上海 2011 01 6 2 4 6 7 6
	 *
	 * key:城市和月份 进入一个reduce方法的前提： 1、进入同一个reduce任务------分区函数（城市和月份相同的数据得出的值是相同的）
	 * 2、进入同一个reduce方法------分组函数（）
	 *
	 * value:
	 *
	 *
	 *
	 *
	 * 自定义key--------WritableComparable接口--------序列化，反序列化，排序
	 * 自定义value------Writable接口------------------序列化，反序列化
	 *
	 *
	 */
	public class CountApp {
		public static void main(String[] args) throws Exception {
			// 构造Job对象
			Job job = Job.getInstance();

			// 设置任务名，任务类
			job.setJobName("count_by_city_month");
			job.setJarByClass(CountApp.class);

			// 设置输入
			TextInputFormat.setInputPaths(job, new Path(args[0]));// 路径
			job.setInputFormatClass(TextInputFormat.class);// 读取格式

			// 设置map
			job.setMapperClass(CountMapper.class);// 设置map类
			job.setMapOutputKeyClass(MyKey.class);// 设置map输出key
			job.setMapOutputValueClass(MyValue.class);// 设置map输出value

			// 设置reduce
			job.setReducerClass(CountReducer.class);// 设置reduce
			job.setOutputKeyClass(Text.class);// 设置reduce输出key
			job.setOutputValueClass(NullWritable.class);// 设置reduce输出value
			job.setNumReduceTasks(3);

			// 设置输出
			TextOutputFormat.setOutputPath(job, new Path(args[1]));// 设置输出路径
			job.setOutputFormatClass(TextOutputFormat.class);// 写出格式

			// 提交任务
			// job.submit();
			// 提交任务，等待任务结束，打印统计信息
			// 正常结束返回true，非正常结束返回false
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		}
	}
