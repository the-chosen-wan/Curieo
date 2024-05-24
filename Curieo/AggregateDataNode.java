package Curieo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateDataNode {
    Map<String, SingleDataNode> nodeMap;

    public AggregateDataNode(){
        nodeMap = new HashMap<>();
    }

    public Map<String, SingleDataNode> getNodeMap(){return this.nodeMap;}

    public void insertDataNode(String key, SingleDataNode dataNode){
        if(!nodeMap.containsKey(key)){
            nodeMap.put(key, new SingleDataNode(dataNode));
        }

        else{
            SingleDataNode node = nodeMap.get(key);
            node = node.generateNewNode(dataNode);
            nodeMap.put(key,node);
        }

        return;
    }

    public AggregateDataNode generateNewNode(AggregateDataNode newNode){
        if(newNode == null)
            return this;
        
        for(Map.Entry<String, SingleDataNode> entry: newNode.getNodeMap().entrySet()){
            String key = entry.getKey();
            SingleDataNode value = entry.getValue();

            this.insertDataNode(key, value);
        }

        return this;
    }

    public SingleDataNode getSingleDataNode(String key){

        if(!nodeMap.containsKey(key))
            return null;
        
        return this.nodeMap.get(key);
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, SingleDataNode> entry: this.getNodeMap().entrySet()){
            String key = entry.getKey();
            SingleDataNode value = entry.getValue();

            result.append("key " + key + " " + value.toString() + "\n");
        }

        return result.toString();
    }

    public boolean isEmpty(){
        return this.nodeMap.isEmpty();
    }

    public List<SingleDataNode> getNodeList(){
        return nodeMap.values().stream().toList();
    }

    public static AggregateDataNode defaultNode(){
        return new AggregateDataNode();
    }
}
