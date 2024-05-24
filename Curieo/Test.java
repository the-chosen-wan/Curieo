package Curieo;

import java.util.List;

public class Test {
    
    public static void main(String[] args){
        // SegTree tree = new SegTree(0, Long.MAX_VALUE - 1);
        // tree.insert(1212, "a", 25);
        // tree.insert(121200, "b", 25);
        // tree.insert(1212, "a", 500);
        // tree.insert(121200, "a", 20);
        // System.out.println(tree.querySpecificKey(0, 121199,"a"));
        Logger log = new Logger();
        // log.readFromFile("Curieo/input.txt", "Curieo/output.txt");
        RandomGenerator rng = new RandomGenerator();

        // System.out.println(rng.generateListQueries(100000));
        List<String> queries = rng.generateListQueries(200);

        for(String q:queries){
            System.out.println(log.queryHandler(q));
        }
    }
}
