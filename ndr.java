// 2. Display no: of drivers- miles-based and hours-based
 // distibuted cache-drivers.csv
// input- timesheet.csv
//jar- ndr1
// dir- ndr1



import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

	//data - student
	public class ndr {
		
		public static class DriverMap extends Mapper<LongWritable,Text,Text,LongWritable>
		   {
		     public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException
		      {	    	  
		    	final LongWritable  num =new  LongWritable(1);
		    	
		    	 String[] data = value.toString().split(",");  
		
				//for(String i : data)
		    	// con.write(new Text(i),num);
		    	
		    	Text nums=new Text(data[5]);
		  
                con.write(nums,num);
		            
		       }
		        
		      
		   }
		
		  public static class DriverReduce extends Reducer<Text,LongWritable,Text,LongWritable>
		   {
			    
			  public void reduce(Text key, Iterable<LongWritable> val,Context context) throws IOException, InterruptedException 
			    	{
			    		
			    	
			    	
			    		 long count=0;
			    	
			      
			      
			    		for(LongWritable i :val)
			    		{
			    			
			    			count=count+i.get();
			    						
			    		}
			    		
			    		context.write(key, new LongWritable(count));
			    		
			    		
			 
			    	}
		   }
		
			    
			    
			    public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException,
				InterruptedException
			{
			    Configuration conf = new Configuration();
				conf.set("mapreduce.output.textoutputformat.separator", ",");
				Job job = Job.getInstance(conf, "Total Marks");
			    job.setJarByClass(ndr.class);
			    job.setMapperClass(DriverMap.class);
			    //job.setCombinerClass(CityReduceClass.class);
			    job.setReducerClass(DriverReduce.class);
			   // job.setMapOutputKeyClass(Text.class);
			    //job.setMapOutputValueClass(FloatWritable.class);
			   job.setNumReduceTasks(1);
			    job.setOutputKeyClass(Text.class);
			    job.setOutputValueClass(LongWritable.class);
			    FileInputFormat.addInputPath(job, new Path(args[0]));
			    FileOutputFormat.setOutputPath(job, new Path(args[1]));
			    System.exit(job.waitForCompletion(true) ? 0 : 1);
			  }
	}
	
	
	
	


