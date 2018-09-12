public class Movierating {

    public static class MovieMap extends Mapper<LongWritable,Text,FloatWritable,Text>
       {
         public void map(LongWritable key, Text value, Context con) throws IOException, InterruptedException
          {             
           
             String [] movie = value.toString().split(",");
             
             String code=movie[0]+","+movie[1]+","+movie[2];
             float rating=Float.parseFloat(movie[3]);
             if(rating>4.0f) {
             
             con.write(new FloatWritable(rating),new Text(code));
             }
               
             }
           
         
       }
   
      public static class MovieReduce extends Reducer<IntWritable,Text,IntWritable,IntWritable>
       {
           
          public void reduce(IntWritable k, Iterable<Text> v,Context context) throws IOException, InterruptedException
                {
                   
               
               
                    int count=0;
               
             
             
                    for(Text i :v)
                    {
                       
                        count++;
                                   
                    }
                   
               
                    context.write(k,new IntWritable(count) );
                   
                   
         
                }
       }
   
           
           
            public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException{
            Configuration conf = new Configuration();
            conf.set("mapreduce.output.textoutputformat.separator", ",");
            Job job = Job.getInstance(conf, "Total Marks");
            job.setJarByClass(Movierating.class);
            job.setMapperClass(MovieMap.class);
            //job.setCombinerClass(CityReduceClass.class);
            job.setReducerClass(MovieReduce.class);
           job.setMapOutputKeyClass(FloatWritable.class);
            job.setMapOutputValueClass(Text.class);
           job.setNumReduceTasks(0);
            job.setOutputKeyClass(IntWritable.class);
            job.setOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);
          }
}



