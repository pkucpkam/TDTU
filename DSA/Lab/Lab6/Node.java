public class Node {
    Integer data;
    Node rightNode, leftNode;

    public Node() {
        this.data = 0;
        this.rightNode = null;
        this.leftNode = null;
    }

    public Node(Integer data) {
        this.data = data;
        this.rightNode = this.leftNode = null;
        
    }
}