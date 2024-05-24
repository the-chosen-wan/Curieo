package Curieo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static final long MAX = (Long.MAX_VALUE) - 1;
    private static final float EPSILON = (float)5e-7;
    private SegTree segTree;

    public Logger(){
        this.segTree = new SegTree(0,Logger.MAX);
    }

    private String[] parseInsert(String s){
        return s.split(";");
    }

    private String insert(String query){
        String [] parsed = this.parseInsert(query);

        long ts = Long.parseLong(parsed[0]);
        String key = parsed[1];
        float severity = Float.parseFloat(parsed[2]);

        segTree.insert(ts,key,severity);
        return "No output";
    }


    private String[] splitBySpace(String query){
        return query.split(" ");
    }

    private String computeAggregate(AggregateDataNode node){

        if(node.isEmpty())
            return "Min: " + 0.0 + " Max: " + 0.0 + " Mean " + 0.0;
        
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        float sum = 0;
        int count = 0;

        List<SingleDataNode> dataNodes = node.getNodeList();

        for(SingleDataNode dataNode: dataNodes){
            max = Math.max(max,dataNode.getMax());
            min = Math.min(min,dataNode.getMin());
            sum += dataNode.getSum();
            count += dataNode.getCount();
        }

        float average = sum/count;
        return "Min: " + min + " Max: " + max + " Mean " + average;
    }

    private String computeAggregate(SingleDataNode node){
        if(Math.abs(Float.compare(node.getMax(), Float.MIN_VALUE))< Logger.EPSILON)
            return "Min: " + 0.0 + " Max: " + 0.0 + " Mean " + 0.0;
        
        float average = node.getSum()/ node.getCount();
        return "Min: " + node.getMin() + " Max: " + node.getMax() + " Mean " + average;
    }

    private String query(String query){
        String[] parsed = this.splitBySpace(query);

        if(parsed.length == 1){
            String key = parsed[0];

            SingleDataNode aggregate = segTree.querySpecificKey(0, Logger.MAX, key);
            return this.computeAggregate(aggregate);
        }

        if(parsed.length==2){
            AggregateDataNode aggregate;

            String id = parsed[0];
            long ts = Long.parseLong(parsed[1]);

            if(id.equals("BEFORE"))
                aggregate = segTree.queryAllKeys(0, ts-1);
            else
                aggregate = segTree.queryAllKeys(ts+1, Logger.MAX);

            return this.computeAggregate(aggregate);
        }

        if(parsed.length==3){
            SingleDataNode aggregate;

            String id = parsed[0];
            long ts = Long.parseLong(parsed[2]);
            String key = parsed[1];

            if(id.equals("BEFORE"))
                aggregate = segTree.querySpecificKey(0, ts-1, key);
            
            else
                aggregate = segTree.querySpecificKey(ts+1, Logger.MAX, key);

            return this.computeAggregate(aggregate);
        }

        return "";
    }

    public String queryHandler(String query){
        char id = query.charAt(0);
        String actualQuery = query.substring(2);

        if(id=='1')
            return this.insert(actualQuery);
        else
            return this.query(actualQuery);
    }

    public void writeQueriesToFile(List<String> queries, String outFilePath){

        try{
            List<String> outputs = new ArrayList<>();

            for(String s:queries)
                outputs.add(this.queryHandler(s));

            Path outPath = Paths.get(outFilePath);
            Files.write(outPath, outputs);
            return;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readFromFile(String filePath, String outFilePath){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            List<String> outputs = new ArrayList<>();
            while((line = br.readLine())!=null)
                outputs.add(this.queryHandler(line));
            
            Path outPath = Paths.get(outFilePath);
            Files.write(outPath, outputs);

            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
