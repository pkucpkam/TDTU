public class BST {
    public Node root;

    public BST() {
        this.root = null;
    }

    private Node insert(Node x, Integer key) {
        if (x == null) {
            x = new Node(key);
            return x;
        }
        int cmp = key.compareTo(x.data);
        if (cmp < 0) {
            x.leftNode = insert(x.leftNode, key);
        } else if (cmp > 0) {
            x.rightNode = insert(x.rightNode, key);
        } else {
            x.data = key;
        }
        return x;
    }

    // insert to big tree
    public void insert(Integer key) {
        root = insert(root, key);
    }

    public void NLR() {
        NLR(root);
    }

    public void NLR(Node x) {
        if (x != null) {
            System.out.print(x.data + " ");
            NLR(x.leftNode);
            NLR(x.rightNode);
        }
    }

    public void LRN() {
        LRN(root);
    }

    public void LRN(Node x) {
        if (x != null) {
            LRN(x.leftNode);
            LRN(x.rightNode);
            System.out.print(x.data + " ");
        }
    }

    public Node search(Node x, Integer key) {
        if (x == null) {
            return null;
        }
        if (key < x.data) {
            return search(x.leftNode, key);
        } else if (key > x.data)
            return search(x.rightNode, key);
        else
            return x;
    }

    public int findMin() {
        return findMin(root).data;
    }

    public Node findMin(Node x) {
        if (x.leftNode == null) {
            return x;
        } else {
            return findMin(x.leftNode);
        }
    }

    public int findMax() {
        return findMax(root).data;
    }

    public Node findMax(Node x) {
        if (x.rightNode == null) {
            return x;
        } else {
            return findMax(x.rightNode);
        }
    }

    public Node deleteMin(Node x) {
        if (x.leftNode == null)
            return x.rightNode;
        x.leftNode = deleteMin(x.leftNode);
        return x;
    }

    public Node delete(Node x, Integer key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.data);
        if (cmp < 0)
            x.leftNode = delete(x.leftNode, key);
        else if (cmp > 0)
            x.rightNode = delete(x.rightNode, key);
        else {
            if (x.rightNode == null)
                return x.leftNode;
            if (x.leftNode == null)
                return x.rightNode;
            Node t = x;
            x = findMin(t.rightNode);
            x.rightNode = deleteMin(t.rightNode);
            x.leftNode = t.leftNode;
        }
        return x;
    }

    public void createTree(String strKey) {
        String s[] = strKey.split(" ");
        for (String string : s) {
            Integer i = Integer.parseInt(string);
            insert(i);
        }
    }

    // ascending <=> LNR
    public void printAsc() {
        LNR(root);
    }

    public void LNR(Node x) {
        if (x != null) {
            LNR(x.leftNode);
            System.out.print(x.data + " ");
            LNR(x.rightNode);
        }
    }

    // descending <=> RNL
    public void printDesc() {
        RNL(root);
    }

    public void RNL(Node x) {
        if (x != null) {
            RNL(x.rightNode);
            System.out.print(x.data);
            RNL(x.leftNode);
        }
    }

    public boolean contains(Integer key) {
        if (search(root, key) != null)
            return true;
        return false;
    }

    public Node deleteMax(Node x) {
        if (x.rightNode == null)
            return x.leftNode;
        x.rightNode = deleteMax(x.rightNode);
        return x;
    }

    public Node delete_pre(Node x, Integer key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.data);
        if (cmp < 0)
            x.leftNode = delete(x.leftNode, key);
        else if (cmp > 0)
            x.rightNode = delete(x.rightNode, key);
        else {
            if (x.rightNode == null)
                return x.leftNode;
            if (x.leftNode == null)
                return x.rightNode;
            Node t = x;
            x = findMax(t.leftNode);
            x.leftNode = deleteMin(t.leftNode);
            x.rightNode = t.rightNode;
        }
        return x;
    }

    public int height(Node x) {
        if (x == null) {
            return 0;
        } else {
            return 1 + Math.max(height(x.leftNode), height(x.rightNode));
        }
    }

    private Integer sum(Node x) {
        if (x == null)
            return 0;
        else
            return x.data + sum(x.rightNode) + sum(x.leftNode);
    }

    public Integer sum() {
        return sum(root);
    }

}