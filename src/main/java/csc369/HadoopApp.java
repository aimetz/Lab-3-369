package csc369;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class HadoopApp {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator",",");
        
        Job job = new Job(conf, "Hadoop example");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

	if (otherArgs.length < 3) {
	    System.out.println("Expected parameters: <job class> [<input dir>]+ <output dir>");
	    System.exit(-1);
	} else if ("CountryVisitorsToPage".equalsIgnoreCase(otherArgs[0])) {

//	    MultipleInputs.addInputPath(job, new Path(otherArgs[1]),
//					KeyValueTextInputFormat.class, UserMessages.LogMapper.class );
//	    MultipleInputs.addInputPath(job, new Path(otherArgs[2]),
//					TextInputFormat.class, UserMessages.CountryMapper.class );

	    job.setReducerClass(RequestCountByCountry.JoinReducer.class);
		job.setMapperClass(RequestCountByCountry.LogMapper.class);

	    job.setOutputKeyClass(RequestCountByCountry.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(RequestCountByCountry.OUTPUT_VALUE_CLASS);
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[3]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));

	} else if ("PageRequestCountSortedByCountry".equalsIgnoreCase(otherArgs[0])) {
	    job.setReducerClass(CountryVisitorsToPage.ReducerImpl.class);
	    job.setMapperClass(CountryVisitorsToPage.MapperImpl.class);
	    job.setOutputKeyClass(CountryVisitorsToPage.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(CountryVisitorsToPage.OUTPUT_VALUE_CLASS);
	    FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	} else if ("CountryVisitorsToPage".equalsIgnoreCase(otherArgs[0])) {
	    job.setReducerClass(CountryVisitorsToPage.ReducerImpl.class);
	    job.setMapperClass(CountryVisitorsToPage.MapperImpl.class);
	    job.setOutputKeyClass(CountryVisitorsToPage.OUTPUT_KEY_CLASS);
	    job.setOutputValueClass(CountryVisitorsToPage.OUTPUT_VALUE_CLASS);
	    FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
	} else {
	    System.out.println("Unrecognized job: " + otherArgs[0]);
	    System.exit(-1);
	}
        System.exit(job.waitForCompletion(true) ? 0: 1);
    }

}
