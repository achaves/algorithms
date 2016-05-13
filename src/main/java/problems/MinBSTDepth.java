package problems;

/**
 * Created by allenc289 on 4/14/16.
 */
public class MinBSTDepth {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int minDepth(TreeNode a) {
        if (a == null) {
            return 0;
        }
        // Only right subtree exists
        else if (a.left == null && a.right != null) {
            return 1 + minDepth(a.right);
        }
        // only left subtree exists
        else if (a.left != null && a.right == null) {
            return 1 + minDepth(a.left);
        } else {
            return Math.min(minDepth(a.left),
                    minDepth(a.right)) + 1;
        }
    }
}
