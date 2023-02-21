package csc369;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import static java.nio.file.Files.readAllLines;

public class PageRequestCountSortedCountry {

    public static final Class OUTPUT_KEY_CLASS = Text.class;
    public static final Class OUTPUT_VALUE_CLASS = IntWritable.class;

    // Mapper for User file
    public static class LogMapper extends Mapper<Text, Text, Text, IntWritable> {
        private final IntWritable one = new IntWritable(1);
        //BufferedReader br = new BufferedReader(new FileReader("test.csv"));
        List<String> lines = readAllLines(Paths.get("input_country1/hostname_country.csv"));
        HashMap<String, String> map = new HashMap<String, String>();

        public LogMapper() throws IOException {
        }

        public void make_map() {
            for (String line : lines) {
                String str[] = line.split(",");
                map.put(str[0], str[1]);
            }
        }
        @Override
        protected void map (Text key, Text value,
                            Context context) throws IOException, InterruptedException {
            make_map();
            String[] sa = value.toString().split(" ");
            Text hostname = new Text();
            hostname.set(sa[0]);
            Text country = new Text(map.getOrDefault(String.valueOf(hostname), "Unknown"));
            context.write(country, one);
        }
    }


    //  Reducer: just one reducer class to perform the "join"
    public static class JoinReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text word, Iterable<IntWritable> intOne, Context context) throws IOException, InterruptedException {
            int sum = 0;
            Iterator<IntWritable> itr = intOne.iterator();

            while (itr.hasNext()){
                sum  += itr.next().get();
            }
            result.set(sum);
            context.write(word, result);
        }
    }


}