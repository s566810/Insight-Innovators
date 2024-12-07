package com.mycompany.app;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        int count = 0;

        // Sum the Facebook likes and count the number of occurrences
        for (IntWritable val : values) {
            sum += val.get();
            count++;
        }

        // Calculate the average Facebook likes for each actor
        if (count > 0) {
            int average = sum / count; // Integer division for average
            result.set(average);
            context.write(key, result); // Emit actor and their average Facebook likes
        }
    }
}
