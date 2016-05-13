package problems;

/**
 * Created by allenc289 on 4/14/16.
 */
public class IsBSTBalanced {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private int height(TreeNode a) {
        if (a == null) return 0;
        return Math.max(height(a.left), height(a.right)) + 1;
    }

    public int isBalanced(TreeNode a) {
        if (a == null) return 0;

        //if (Math.abs(height(a.left) - height()))
        //return (isBalanced(a.left) - isBalanced(a.right))

        return 0;
    }


}
