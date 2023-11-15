public class Main {
  public static void main(String[] args) {
    // create a BST object
    BST bst = new BST();
    /*
     * BST tree example
     * 45
     * / \
     * 10 90
     * / \ /
     * 7 12 50
     */
    // insert data into BST
    bst.insert(45);
    bst.insert(10);
    bst.insert(90);
    bst.insert(50);
    bst.insert(7);
    bst.insert(12);
    bst.printAsc();
    bst.delete_pre(bst.root, 10);
    System.out.println();
    bst.printAsc();
  }
}