public class TreeNode {
    //create a binary tree class where operators are the internal nodes and operands the leaf nodes
    //we'll create this tree and then use it to traverse through in postfix
    //we'll create a builder class that uses this class' organisational structure

    String value;  //operator or operand
    TreeNode left, right;

    public TreeNode(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString(){
        return value;
    }
}
