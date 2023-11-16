import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class AdjacencyMatrix {
    private int[][] adj;
    private final int NUMBER_OF_VERTICES;

    public AdjacencyMatrix(int vertices) {
        NUMBER_OF_VERTICES = vertices;
        adj = new int[NUMBER_OF_VERTICES][NUMBER_OF_VERTICES];
    }

    // Initialize with an input filename
    public AdjacencyMatrix(String filename) throws Exception {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
        NUMBER_OF_VERTICES = sc.nextInt();
        adj = new int[NUMBER_OF_VERTICES][NUMBER_OF_VERTICES];
        sc.nextLine();
        for (int i = 0; i < NUMBER_OF_VERTICES; i++)
            for (int j = 0; j < NUMBER_OF_VERTICES; j++)
                adj[i][j] = sc.nextInt();
    }

    public void setEgde(int vertexSource, int vertexDestination, int weight) {
        try {
            adj[vertexSource][vertexDestination] = weight;
            adj[vertexDestination][vertexSource] = weight;
        } catch (ArrayIndexOutOfBoundsException indexBounce) {
            System.out.println("The vertex is invalid");
        }
    }

    public int getEgde(int vertexSource, int vertexDestination) {
        try {
            return adj[vertexSource][vertexDestination];
        } catch (ArrayIndexOutOfBoundsException indexBounce) {
            System.out.println("The vertex is invalid");
        }
        return -1;
    }

    public void printGraph() {
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            for (int j = 0; j < NUMBER_OF_VERTICES; j++) {
                System.out.print(adj[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int countVer() {
        return NUMBER_OF_VERTICES;
    }

    public int countEdges() {
        int count = 0;
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            for (int j = 0; j < NUMBER_OF_VERTICES; j++) {
                count += adj[i][j];
            }
        }
        return count;
    }

    public void Enumerae(int u) {
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            if (adj[u - 1][i] == 1) {
                System.out.print(i + 1 + " ");
            }
        }
        System.out.println();
    }

    public boolean existence(int u, int v) {
        if (adj[u - 1][v - 1] == 1)
            return true;
        return false;
    }

    public void BFS(int startV) {
        boolean visited[] = new boolean[NUMBER_OF_VERTICES];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startV);
        visited[startV] = true;

        BFS(queue, visited);
        System.out.println();
    }

    private void BFS(Queue<Integer> queue, boolean[] visited) {
        if (queue.isEmpty()) {
            return;
        }
        int currV = queue.poll();
        System.out.print(currV + " ");
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            if (adj[currV][i] == 1 && !visited[i]) {
                queue.add(i);
                visited[i] = true;
            }
        }

        BFS(queue, visited);
    }

    public void DFS(int startV) {
        boolean[] visited = new boolean[NUMBER_OF_VERTICES];
        DFS(startV, visited);
        System.out.println();
    }

    public void DFS(int currV, boolean[] visited) {
        System.out.print(currV + " ");
        visited[currV] = true;
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            if (adj[currV][i] == 1 && !visited[i]) {
                DFS(i, visited);
            }
        }
    }

    public void StackDFS(int startV) {
        boolean visited[] = new boolean[NUMBER_OF_VERTICES];
        Stack<Integer> stack = new Stack<>();
        stack.push(startV);
        while (!stack.isEmpty()) {
            int currV = stack.pop();
            if (!visited[currV]) {
                System.out.print(currV + " ");
                visited[currV] = true;
            }

            for (int i = NUMBER_OF_VERTICES - 1; i >= 0; i--) {
                if (adj[currV][i] == 1 && !visited[i]) {
                    stack.push(i);
                }
            }
        }
        System.out.println();
    }

    public boolean isReachable(int u, int v) {
        if (adj[u][v] == 1)
            return true;
        return false;
    }

    public AdjacencyList convertToAL() {
    AdjacencyList adjacencyList = new AdjacencyList(NUMBER_OF_VERTICES);
    for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
        for (int j = 0; j < NUMBER_OF_VERTICES; j++) {
            if (adj[i][j] == 1) {
                adjacencyList.addEdge(i, j);
            }
        }
    }
    return adjacencyList;
}



    public static void main(String args[]) throws Exception {
        AdjacencyMatrix AdjMatrix = new AdjacencyMatrix("2D_Exam1.txt");
        System.out.println("'a) AM : ");
        AdjMatrix.printGraph();

        // b
        System.out.println("The number of vertices = " + AdjMatrix.countVer());

        // c
        System.out.println("The number of edges = " + AdjMatrix.countEdges());

        // d
        System.out.print("Enumerate neighbors of a vertex u : ");
        AdjMatrix.Enumerae(1);

        // d
        System.out.println("the existence of edge  " + AdjMatrix.existence(2, 2));
        System.out.println("the existence of edge  " + AdjMatrix.existence(2, 3));

        // Exercise 4a - BFS
        System.out.print("BFS : ");
        AdjMatrix.BFS(3);

        // Exercise 4b - DFS
        System.out.print("DFS : ");
        AdjMatrix.DFS(0);

        // Exercise 4c - Stack DFS
        System.out.print("DFS using Stack : ");
        AdjMatrix.StackDFS(0);

        // Exercise 4d - IsReachable
        System.out.println("vertex 4 is reachable from vertex 0 : " + AdjMatrix.isReachable(4, 0));
        System.out.println("vertex 4 is reachable from vertex 2 : " + AdjMatrix.isReachable(4, 2));

        //Exercise 5 - Convert AdjacencyMatrix to Adjacency List
        System.out.println("After convert to Adjacency List");
        AdjMatrix.convertToAL().printGraph();

    }
}