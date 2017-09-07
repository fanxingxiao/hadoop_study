package com.cmx.hadoop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

public class HdfsTest {
	@Test
	public void testLs() throws Exception{
		//建立连接(连接网页)
		//获取文件系统的连接对象,需要三个参数
		//URI:core-sire.xml
		//配置对象:new Configuration()
		//用户名:hdfs的用户名  --- linux 用户名
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"),new Configuration(),"centos");
		//查看目录文件的方法filesystem.listststus参数是一个path对象
		//返回值是一个filestatus对象的数组
		FileStatus[] liStatus = fileSystem.listStatus(new Path("/"));
		for(FileStatus fileStatus:liStatus){
			System.out.println(fileStatus);
			System.out.println("块大小:"+fileStatus.getBlockSize());
			System.out.println("访问时间:"+fileStatus.getAccessTime());
			System.out.println("修改时间:"+fileStatus.getModificationTime());
			System.out.println("文件大小:"+fileStatus.getLen());
			System.out.println("所属用户:"+fileStatus.getOwner());
			System.out.println("所属用户组:"+fileStatus.getGroup());
			System.out.println("路径:"+fileStatus.getPath());
			System.out.println("权限:"+fileStatus.getPermission());
			System.out.println("副本数:"+fileStatus.getReplication());
		}
		//递归
//		printLsR(fileSystem,new Path("/"));
		//hdfs dfs -ls /
//		printLs(fileSystem,"/");
	}
	//hdfs dfs -ls /
	public void printLs(FileSystem fileSystem,String path) throws Exception{
		FileStatus[] liStatus = fileSystem.listStatus(new Path(path));
		for(FileStatus fileStatus:liStatus){
			System.out.println(fileStatus);
		}
	}
	//hdfs dfs -ls -R /
	public void printLsR(FileSystem fileSystem,Path path) throws Exception{
		FileStatus[] liStatus = fileSystem.listStatus(path);
		for(FileStatus fileStatus:liStatus){
			System.out.println(fileStatus.getPath());
			if(fileStatus.isDirectory()){
				printLsR(fileSystem, fileStatus.getPath());
			}
		}
	}
	@Test
	public void test003() throws Exception{
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"),new Configuration(),"centos");
		//上传,从本地文件系统传到hdfs系统
		fileSystem.copyFromLocalFile(new Path("e:\\test\\hdfsTest\\apache-maven-3.3.9-bin.zip"), new Path("/"));
		//查看是否上传
		printLsR(fileSystem, new Path("/"));
	}
	@Test
	public void test004() throws Exception{
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"),new Configuration(),"centos");
		//下载,从本地文件系统传到hdfs系统
		fileSystem.copyToLocalFile(new Path("hdfs://centos201:9000/apache-maven-3.3.9-bin.zip"), new Path("e:\\test\\hdfsTest\\apache-maven-3.3.9-bin2.zip"));
	}
	@Test
	public void test005() throws Exception{
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"),new Configuration(),"centos");
		//删除
		fileSystem.delete(new Path("hdfs://centos201:9000/apache-maven-3.3.9-bin.zip"),true);
		//查看是否删除
		printLsR(fileSystem, new Path("/"));
	}
	//流式数据写入,上传
	@Test
	public void test006() throws Exception{
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"),new Configuration(),"centos");
		//创建一个记录然后打开输入流,往数据输出流中写入数据
		FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("hdfs://centos201:9000/apache-maven-3.3.9-bin.zip"));
		FileInputStream fileInputStream = new FileInputStream("e:\\test\\hdfsTest\\apache-maven-3.3.9-bin2.zip");
		// 流式传输的标准代码
				int len = 0;
				byte[] bytes = new byte[4 * 1024];// 4KB
				while ((len = fileInputStream.read(bytes)) >= 0) {
					fsDataOutputStream.write(bytes, 0, len);
				}
				fileInputStream.close();
				fsDataOutputStream.close();
				printLsR(fileSystem, new Path("/"));
			}

			/**
			 * 流式数据写出，下载
			 * 
			 * @throws Exception
			 */
			@Test
			public void test007() throws Exception {
				FileSystem fileSystem = FileSystem.get(new URI("hdfs://centos201:9000"), new Configuration(), "hadoop");
				FSDataInputStream inputStream = fileSystem.open(new Path("hdfs://centos201:9000/apache-maven-3.3.9-bin.zip"));

				FileOutputStream fileOutputStream = new FileOutputStream("e:\\test\\hdfsTest\\apache-maven-3.3.9-bin3.zip");

				// 流式传输的标准代码
				int len = 0;
				byte[] bytes = new byte[4 * 1024];// 4KB
				while ((len = inputStream.read(bytes)) >= 0) {
					fileOutputStream.write(bytes, 0, len);
				}

				fileOutputStream.close();
				inputStream.close();
			}
}
