/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

 class Solution {
    public void reorderList(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        ListNode cur = head;

        nodes.add(cur);
        while (cur.next != null) {
            cur = cur.next;
            nodes.add(cur);
        }

        cur = head;
        for (int i = 0; i < nodes.size(); ++i) {
            cur.next = nodes.get(i % 2 == 0 ? i / 2 : nodes.size() - i / 2 - 1);
            cur = cur.next;
        }
        cur.next = null;
    }
}
