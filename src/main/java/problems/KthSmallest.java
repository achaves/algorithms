package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Created by allenc289 on 4/13/16.
 * Preorder : [1, 2, 3]
 * Inorder  : [2, 1, 3]
 */
public class KthSmallest {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int kthSmallest(TreeNode node, int k) {
        Stack<TreeNode> stack = new Stack<>();

        // Add left nodes to stack
        TreeNode currentNode = node;
        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.left;
        }

        // P
        while (!stack.empty()) {
             currentNode = stack.pop();
            k--;
            if (k == 0) {
                return currentNode.val;
            }

            if (currentNode.left != null) {
                stack.push(currentNode.left);
            }

            if (currentNode.right != null) {
                stack.push(currentNode.right);
            }
        }

        return -1;
    }

    public TreeNode buildTreeFromPreOrderAndInOrder(ArrayList<Integer> preorder, ArrayList<Integer> inorder) {
        return buildTreeFromPreOrderAndInOrder(preorder.iterator(), inorder, 0, inorder.size() - 1);
    }

    private TreeNode buildTreeFromPreOrderAndInOrder(Iterator<Integer> iterator, ArrayList<Integer> inorder, int startInorder, int endInorder) {
        Integer val = iterator.next();

        TreeNode tn = new TreeNode(val);

        int posInorder = find(inorder, val, startInorder, endInorder);

        if (startInorder <= posInorder - 1) {
            tn.left = buildTreeFromPreOrderAndInOrder(iterator, inorder, startInorder, posInorder - 1);
        }
        if (posInorder + 1 <= endInorder) {
            tn.right = buildTreeFromPreOrderAndInOrder(iterator, inorder, posInorder + 1, endInorder);
        }

        return tn;
    }

    private TreeNode buildTreeFromPostOrderAndInOrder(ListIterator<Integer> iterator, ArrayList<Integer> inorder, int startInorder, int endInorder) {
        Integer val = iterator.previous();

        TreeNode tn = new TreeNode(val);

        int posInorder = find(inorder, val, startInorder, endInorder);

        if (posInorder + 1 <= endInorder) {
            tn.right = buildTreeFromPostOrderAndInOrder(iterator, inorder, posInorder + 1, endInorder);
        }

        if (startInorder <= posInorder - 1) {
            tn.left = buildTreeFromPostOrderAndInOrder(iterator, inorder, startInorder, posInorder - 1);
        }

        return tn;
    }

    private int find(ArrayList<Integer> inorder, Integer val, int startInorder, int endInorder) {
        for (int i = startInorder; i <= endInorder; i++) {
            if (inorder.get(i).equals(val)) {
                return i;
            }
        }

        return -1;
    }

    void printInOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        /* first recur on left child */
        printInOrder(node.left);

        /* then print the data of node */
        System.out.print(node.val + " ");

        /* now recur on right child */
        printInOrder(node.right);
    }

    void printPostOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        /* first recur on left child */
        printPostOrder(node.left);

        /* now recur on right child */
        printPostOrder(node.right);

        /* then print the data of node */
        System.out.print(node.val + " ");

    }

    void printPreOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        /* then print the data of node */
        System.out.print(node.val + " ");

        /* first recur on left child */
        printPreOrder(node.left);

        /* now recur on right child */
        printPreOrder(node.right);
    }

    private static void printTree(KthSmallest binaryTree, TreeNode tree) {
        // building the tree by printing inorder traversal
        System.out.println();
        System.out.println("Inorder traversal of constructed tree is : ");
        binaryTree.printInOrder(tree);

        // building the tree by printing preorder traversal
        System.out.println();
        System.out.println("Preorder traversal of constructed tree is : ");
        binaryTree.printPreOrder(tree);

        // building the tree by printing postorder traversal
        System.out.println();
        System.out.println("Postorder traversal of constructed tree is : ");
        binaryTree.printPostOrder(tree);
    }


    private static void testPreAndIn(Integer[] preArray, Integer[] inArray) {
        KthSmallest binaryTree = new KthSmallest();
        ArrayList<Integer> pre = new ArrayList<>();
        Collections.addAll(pre, preArray);

        ArrayList<Integer> in = new ArrayList<>();
        Collections.addAll(in, inArray);

        TreeNode tree = binaryTree.buildTreeFromPreOrderAndInOrder(pre, in);
        printTree(binaryTree, tree);
    }



    private static void testPostAndIn(Integer[] postArray, Integer[] inArray) {
        KthSmallest binaryTree = new KthSmallest();
        ArrayList<Integer> post = new ArrayList<>();
        Collections.addAll(post, postArray);

        ArrayList<Integer> in = new ArrayList<>();
        Collections.addAll(in, inArray);

        TreeNode tree = binaryTree.buildTreeFromPostOrderAndInOrder(post.listIterator(post.size()), in, 0, postArray.length - 1);

        printTree(binaryTree, tree);
    }

    // driver program to test above functions
    public static void main(String args[]) {
        // testPreAndIn(new Integer[]{10, 4, 2, 5, 16, 12, 18}, new Integer[]{2, 4, 5, 10, 12, 16, 18});
        //testPreAndIn(new Integer[]{2, 1, 6, 5, 3, 4}, new Integer[]{5, 6, 1, 2, 3, 4});

        testPostAndIn(new Integer[]{5, 6, 1, 4, 3, 2}, new Integer[]{5, 6, 1, 2, 3, 4});
    }
}
