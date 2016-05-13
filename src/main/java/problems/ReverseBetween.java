package problems;

import java.util.Stack;

/**
 * Created by allenc289 on 4/22/16.
 */
public class ReverseBetween {
    public static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            ListNode iteratingNode = this;
            do {
                sb.append(iteratingNode.val + " ->");
                iteratingNode = iteratingNode.next;
            } while (iteratingNode.next != null);
            sb.append(" nil");

            return sb.toString();
        }
    }


    public static ListNode reverseBetween(ListNode a, int m, int n) {
        Stack<ListNode> nodesToReverse = new Stack<>();

        ListNode currentNode = a;
        ListNode beforeFirstNodeToReverse = null;

        for (int i = 0; i < n; i++) {
            if (i >= m - 1) {
                nodesToReverse.add(currentNode);
            } else {
                beforeFirstNodeToReverse = currentNode;
            }

            if (i == n - 1) {
                // We are at the last node to be reversed
                ListNode afterLastNodeToReverse = currentNode.next;

                ListNode iteratingNode = beforeFirstNodeToReverse;
                while (!nodesToReverse.empty()) {
                    ListNode reversedNode = nodesToReverse.pop();
                    if (iteratingNode == null) {
                        a = reversedNode; // new head
                    } else {
                        iteratingNode.next = reversedNode;
                    }

                    iteratingNode = reversedNode;
                }

                iteratingNode.next = afterLastNodeToReverse;
            }


            currentNode = currentNode.next;
        }

        return a;
    }

    public static void testReverseBetween() {
        ListNode l = new ListNode(1);
        l.next = new ListNode(2);
        l.next.next = new ListNode(3);
        l.next.next.next = new ListNode(4);

        System.out.println(l.toString() + " after 1,2= " + reverseBetween(l, 1, 3));
    }
}
