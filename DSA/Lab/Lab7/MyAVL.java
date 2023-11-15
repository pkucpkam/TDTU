public class MyAVL {
    public static void main(String[] args) {
        // create a BST object
        AVL avl = new AVL();
        /*
         * BST tree example
         * 45
         * / \
         * 10 90
         * / \ /
         * 7 12 50
         */
        // insert data into BST
        avl.insert(45);
        avl.insert(10);
        avl.insert(90);
        avl.insert(95);
        avl.insert(100);
        avl.printInOrder();
        System.out.println();
    }
}
