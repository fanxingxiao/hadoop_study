package com.cmx.bigdata.App.temp_analyse_hadoop.fenxi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
/**
 * 自定义key
 * 重写序列化方法，反序列化方法，排序方法
 * 
 * @author zdjy
 *
 */
public class MyKey implements WritableComparable<MyKey>{
	private String  city;
	private String  month;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	/*
	 * 序列化方法,按照定义的顺序写
	 */
	@Override
	public void write(DataOutput out) throws IOException{
		out.writeUTF(city);
		out.writeUTF(month);
	}
	/*
	 *反序列化方法，按照定义的顺序读 
	 */
	@Override
	public void readFields(DataInput in) throws IOException {
		city = in.readUTF();
		month = in.readUTF();
	}
	/*
	 * 排序方法
	 * 排序会影响分组
	 * 
	 * 如果结果相等，那么进入同一个reduce方法，
	 * 
	 */
	@Override
	public int compareTo(MyKey o) {
		String result = city+"\t"+month;
		String result2 = o.getCity()+"\t"+o.getMonth();
		return result.compareTo(result2);
	}
	/*
	 * 默认分区通过key的hashCode的出来的相等
	 * HashPartitioner调用了hashCode方法
	 * 而默认的hashCode方法是Object的hashCode方法
	 */
	@Override
	public int hashCode() {
		return (city+"\t"+month).hashCode();
	}
}
