package problems;

import java.util.ArrayList;

/**
 * Created by allenc289 on 4/14/16.
 */
public class LeafPathsWithSum {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> solutions = new ArrayList();
        ArrayList<Integer> solution = new ArrayList<Integer>();

        calculatePathSums(root, sum, solutions, solution);
        return solutions;
    }

    public void calculatePathSums(TreeNode node, int sum, ArrayList<ArrayList<Integer>> solutions, ArrayList<Integer> solution) {
        if (node == null) return;
        solution.add(node.val);

        if (node.val == sum && node.left == null && node.right == null) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.addAll(solution);
            solutions.add(temp);
        }

        calculatePathSums(node.left, sum - node.val, solutions, solution);
        calculatePathSums(node.right, sum - node.val, solutions, solution);

        solution.remove(solution.size()-1);
    }
}
