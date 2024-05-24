package Curieo;

public class SingleDataNode {
    
    private float min;
    private float max;
    private float sum;
    private int count;

    public SingleDataNode(float min, float max,float sum, int count){
        this.min = min;
        this.max = max;
        this.sum = sum;
        this.count = count;
    }

    public SingleDataNode(SingleDataNode node){
        this.max = node.getMax();
        this.min = node.getMin();
        this.sum = node.getSum();
        this.count = node.getCount();
    }

    public float getMin(){return this.min;}
    public float getMax(){return this.max;}
    public float getSum(){return this.sum;}
    public int getCount(){return this.count;}

    public SingleDataNode generateNewNode(SingleDataNode s){
        if(s==null)
            return this;
        
        this.min = Math.min(this.min, s.getMin());
        this.max = Math.max(this.max , s.getMax());
        this.sum  = this.sum + s.getSum();
        this.count = this.count + s.getCount();
        return this;
    }

    @Override
    public String toString(){
        return "max " + max + " min " + min + " sum " + sum + " count " + count;
    }
    
    public static SingleDataNode defaultNode(){
        return new SingleDataNode(
            Float.MAX_VALUE,
            Float.MIN_VALUE,
            0,
            0
        );
    }
}
