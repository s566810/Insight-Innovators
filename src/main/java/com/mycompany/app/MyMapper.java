package com.mycompany.app;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
    private Text actor = new Text();
    private IntWritable facebookLikes = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] columns = value.toString().split("\t"); // Assuming the dataset uses tab as the delimiter

        if (columns.length > 21) { // Ensure there are enough columns to extract data
            try {
                // Extract Facebook likes for actor 1, actor 2, and actor 3
                int actor1Likes = Integer.parseInt(columns[7].trim());
                int actor2Likes = Integer.parseInt(columns[24].trim());
                int actor3Likes = Integer.parseInt(columns[5].trim());

                // Emit actor names with their respective Facebook likes
                actor.set("Actor1");
                facebookLikes.set(actor1Likes);
                context.write(actor, facebookLikes);

                actor.set("Actor2");
                facebookLikes.set(actor2Likes);
                context.write(actor, facebookLikes);

                actor.set("Actor3");
                facebookLikes.set(actor3Likes);
                context.write(actor, facebookLikes);
            } catch (NumberFormatException e) {
                // Handle invalid data (e.g., missing or non-numeric Facebook likes)
            }
        }
    }
}
