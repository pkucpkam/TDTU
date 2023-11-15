public class AVL {

    Node root = null;

    public int checkBalance(Node x) {
        return height(x.left) - height(x.right);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    public Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    public Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node balance(Node x) {
        if (checkBalance(x) < -1) {
            if (checkBalance(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (checkBalance(x) > 1) {
            if (checkBalance(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    private Node insert(Node x, Integer key) {
        if (x == null) {
            x = new Node(key);
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = insert(x.left, key);
            } else if (cmp > 0) {
                x.right = insert(x.right, key);
            } else {
                return null;
            }
        }
        x.height = 1 + Math.max(height(x.left), height(x.right));
        x = balance(x);
        return x;
    }

    public void insert(Integer key) {
        root = insert(root, key);
    }

    private Node findMin(Node x) {
        if (x.left == null) {
            return x;
        } else {
            return findMin(x.left);
        }
    }

    public Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    public Node delete(Node x, Integer key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node t = x;
            x = findMin(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x = balance(x);
        return x;
    }

    private void LNR(Node x) {
        if (x != null) {
            LNR(x.left);
            System.out.print(x.key + " ");
            LNR(x.right);
        }
    }

    public void printInOrder() {
        LNR(root);
        System.out.println();
    }
}
