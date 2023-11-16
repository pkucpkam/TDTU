import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class EdgeList {
    private Vector<IntegerTriple> edges;

    public EdgeList() {
        edges = new Vector<IntegerTriple>();
    }

    public void addEdge(int w, int u, int v) {
        edges.add(new IntegerTriple(w, u, v));
    }

    public void printGraph() {
        for (int i = 0; i < edges.size(); i++) {
            System.out.println(edges.get(i));
        }
    }

    public void readGraphFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                int w = Integer.parseInt(tokens[0]);
                int u = Integer.parseInt(tokens[1]);
                int v = Integer.parseInt(tokens[2]);
                addEdge(w, u, v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean check(int[] arr, int n) {
        for (int tmp : arr) {
            if (tmp == n)
                return false;
        }
        return true;
    }

    public int numVer() {
        int tmp = 0;
        int list[] = new int[edges.size()];
        int num = 1;
        for (int i = 0; i < edges.size(); i++) {
            if (check(list, edges.get(i).getSource())) {
                list[tmp] = edges.get(i).getSource();
                tmp++;
            } else if (check(list, edges.get(i).getDest())) {
                list[tmp] = edges.get(i).getDest();
                tmp++;
            }
        }

        for (int i : list) {
            if (i != 0) {
                num += 1;
            }
        }
        return num;
    }

    public int numEdges() {
        return edges.size();
    }

    public void enumirate(int u) {
        System.out.print("Enumerate neighbors of a vertex " + u + ": ");
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getSource() == u) {
                System.out.print(edges.get(i).getDest() + " ");
            }
            if (edges.get(i).getDest() == u) {
                System.out.print(edges.get(i).getSource() + " ");
            }
        }
        System.out.println();
    }

    public boolean checkExis(int u, int v) {
        for (int i = 0; i < edges.size(); i++) {
            if ((edges.get(i).getSource() == u && edges.get(i).getDest() == v)
                    || edges.get(i).getSource() == v && edges.get(i).getDest() == u) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        EdgeList graph = new EdgeList();
        // a
        graph.readGraphFromFile("input1.txt");
        System.out.println("Graph:");
        graph.printGraph();
        // b
        System.out.println("The number of vertices :" + graph.numVer());
        // c
        System.out.println("The number of edges : " + graph.numEdges());
        // d
        graph.enumirate(3);
        // e
        System.out.println("Check the existence of edge (0, 2) : " + graph.checkExis(0, 2));
        System.out.println("Check the existence of edge (1,4) : " + graph.checkExis(1, 4));
    }
}