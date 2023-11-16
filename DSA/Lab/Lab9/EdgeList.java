<<<<<<< HEAD
import java.util.Vector;

public class EdgeList{
    private Vector <IntegerTriple > edges;

    public EdgeList(){
        edges = new Vector <IntegerTriple >();
    }   

    public void addEdge(int w, int u, int v){
        edges.add(new IntegerTriple(w,u,v));
    }

    public void printGraph(){
        for(int i = 0; i < edges.size(); i++){
            System.out.println(edges.get(i));
        }
    }
=======
import java.util.Vector;

public class EdgeList{
    private Vector <IntegerTriple > edges;

    public EdgeList(){
        edges = new Vector <IntegerTriple >();
    }   

    public void addEdge(int w, int u, int v){
        edges.add(new IntegerTriple(w,u,v));
    }

    public void printGraph(){
        for(int i = 0; i < edges.size(); i++){
            System.out.println(edges.get(i));
        }
    }
>>>>>>> 24c5249d655fa027d636c3c18201d8b99830bb02
}