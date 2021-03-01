package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//遍历 二叉树
public class Traversal {
  //  前序遍历的递推公式：
  //  preOrder(r) = print r->preOrder(r->left)->preOrder(r->right)
 //   中序遍历的递推公式：
 //   inOrder(r) = inOrder(r->left)->print r->inOrder(r->right)
 //   后序遍历的递推公式：
 //   postOrder(r) = postOrder(r->left)->postOrder(r->right)->print r
/*
    void preOrder(Node* root) {
        if (root == null) return;
        print root // 此处为伪代码，表示打印root节点
        preOrder(root->left);
        preOrder(root->right);
    }
    void inOrder(Node* root) {
        if (root == null) return;
        inOrder(root->left);
        print root // 此处为伪代码，表示打印root节点
        inOrder(root->right);
    }
    void postOrder(Node* root) {
        if (root == null) return;
        postOrder(root->left);
        postOrder(root->right);
        print root // 此处为伪代码，表示打印root节点
    }
  */
      //层序遍历，借用队列辅助即可，根节点先入队列，然后循环从队列中pop节点，
    // 将pop出来的节点的左子节点先入队列，右节点后入队列，依次循环，直到队列为空，遍历结束。
     // Definition for a binary tree node.
     public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
      }
    class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null)
                return new ArrayList<>(0);

            List<List<Integer>> result = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<TreeNode>();
            queue.offer(root);//Queue 中 add() 和 offer()都是用来向队列添加一个元素。
           // 在容量已满的情况下，add() 方法会抛出IllegalStateException异常，offer() 方法只会返回 false
            Queue<TreeNode> curLevelNodes = new LinkedList<TreeNode>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();//出队
                curLevelNodes.offer(node);
                if (queue.isEmpty()) {
                    List<Integer> list = new ArrayList<>(curLevelNodes.size());
                    while (!curLevelNodes.isEmpty()) {
                        TreeNode curNode = curLevelNodes.poll();
                        list.add(curNode.val);
                        if (curNode.left != null) {
                            queue.offer(curNode.left);
                        }
                        if (curNode.right != null) {
                            queue.offer(curNode.right);
                        } }
                    result.add(list);
                } }
            return result;
        } }

}
