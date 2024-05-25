package Curieo;

public class SegTree {
    private long tl;
    private long tr;

    private AggregateDataNode data;

    private SegTree left;
    private SegTree right;

    public SegTree(long tl, long tr){
        this.tl = tl;
        this.tr = tr;
        this.data = new AggregateDataNode();
        this.left = null;
        this.right = null;
    }

    public long getTl(){return this.tl;}
    public long getTr(){return this.tr;}
    public AggregateDataNode getData(){return this.data;}
    public SegTree getLeft(){return this.left;}
    public SegTree getRight(){return this.right;}

    @Override
    public String toString(){return this.data.toString();}

    public void insert(long ts, String key, float severity){
        SingleDataNode newNode = new SingleDataNode(severity,severity,severity,1);
        if(tl==tr){
            this.data.insertDataNode(key, newNode);
            return;
        }

        long mid = tl + (tr-tl)/2;

        if(ts<=mid){
            //build left
            if(this.left == null){
                this.left = new SegTree(tl, mid);
            }
            this.left.insert(ts, key, severity);
        }

        else{
            //build right
            if(this.right==null){
                this.right = new SegTree(mid+1, tr);
            }
            this.right.insert(ts, key, severity);
        }
        this.data.insertDataNode(key, newNode);

        return;
    }

    public void _queryAllKeys(long l, long r, AggregateDataNode result){
        if(l>r){
            return;
        }

        //entire range is required
        if(tl==l&&tr==r){
            result.generateNewNode(this.getData());
            return;
        }
        
        long mid = tl + (tr-tl)/2;

        if(this.left!=null)
            this.left._queryAllKeys(l, Math.min(r,mid), result);

        if(this.right!=null)
            this.right._queryAllKeys(Math.max(l,mid+1), r, result);

        return;
    }

    public AggregateDataNode queryAllKeys(long l, long r){
        AggregateDataNode result = AggregateDataNode.defaultNode();
        this._queryAllKeys(l, r, result);
        return result;
    }

    public void _querySpecificKey(long l, long r, String key, SingleDataNode result){
        if(l>r){
            return;
        }

        //entire range is required
        if(tl==l&&tr==r){
            result.generateNewNode(this.data.getSingleDataNode(key));
            return;
        }
        
        long mid = tl + (tr-tl)/2;

        if(this.left!=null)
            this.left._querySpecificKey(l, Math.min(r,mid), key,result);

        if(this.right!=null)
            this.right._querySpecificKey(Math.max(l,mid+1), r, key,result);

        return;
    }

    public SingleDataNode querySpecificKey(long l, long r, String key){
        SingleDataNode result = SingleDataNode.defaultNode();
        this._querySpecificKey(l, r, key, result);
        return result;
    }
}
