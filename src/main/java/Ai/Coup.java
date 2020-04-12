package Ai;

public class Coup {
    public int i;
    public int j;

    public Coup(final int _i, final int _j) {
        i = _i;
        j = _j;
    }
    public  String toString(){
        return "("+i+","+j+")";
    }
}