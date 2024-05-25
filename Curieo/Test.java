package Curieo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
    
    public static void main(String[] args) throws IOException{
        Logger log = new Logger();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<String> queries = new ArrayList<>();

        String query;

        while((query = br.readLine())!=null)
            queries.add(query);
        
        List<String> outputs = new ArrayList<>();

        for(String q: queries)
            outputs.add(log.queryHandler(q));

        for(String out: outputs)
            System.out.println(out);

        br.close();

        // RandomGenerator rng = new RandomGenerator();

        // List<String> queries = rng.generateListQueries(300);

        // for(String q:queries){
        //     System.out.println(q);
        // }
    }
}
