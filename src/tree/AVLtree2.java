package tree;
// https://segmentfault.com/a/1190000019101902
public class AVLtree2<T extends Comparable<T>> {
    private static final int MAX_HEIGHT_DIFFERENCE = 1;

    private Node<T> root;//根节点
     // // AVL树的节点(内部类)。节点定义
    class Node<KT> {

        KT key;

        Node<KT> left;

        Node<KT> right;

        int height = 1;

        public Node(KT key, Node<KT> left, Node<KT> right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    public void insert(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = insert(root, key);
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key, null, null);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            node.left = insert(node.left, key);//如果 树层次多，迭代了好几次，迭代到插入的那一次，height变化，往上影响到>2,则平衡它。
        } else {
            node.right = insert(node.right, key);// 如node.night为null则返回新建节点，node.right=node（key）
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);//不平衡的节点是从下往上传递的
        }
        refreshHeight(node);
        return node;
    }

    private int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private void refreshHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 此方法中的node, node1, node2分别代表上文范型中的1、2、3号节点
     * 返回的是根节点
     */
    private Node<T> balance(Node<T> node) {
        Node<T> node1, node2;
        // ll
        if (height(node.left) > height(node.right) &&
                height(node.left.left) > height(node.left.right)) {
            node1 = node.left;
            node.left = node1.right;
            node1.right = node;

            refreshHeight(node);//node1的高度没变
            return node1;
        }
        // lr
        if (height(node.left) > height(node.right) &&
                height(node.left.right) > height(node.left.left)) {
            node1 = node.left;
            node2 = node.left.right;

            node.left = node2.right;
            node1.right = node2.left;
            node2.left = node1;
            node2.right = node;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        // rr
        if (height(node.right) > height(node.left) &&
                height(node.right.right) > height(node.right.left)) {
            node1 = node.right;
            node.right = node1.left;
            node1.left = node;

            refreshHeight(node);
            return node1;
        }
        // rl
        if (height(node.right) > height(node.left) &&
                height(node.right.left) > height(node.right.right)) {
            node1 = node.right;
            node2 = node.right.left;
            node.right = node2.left;
            node1.left = node2.right;
            node2.left = node;
            node2.right = node1;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        return node;
    }
    //删除节点
    public void remove(T key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = remove(root, key);
    }
    //删除节点，返回的是删除的节点,
    private Node<T> remove(Node<T> node, T key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        }
        if (cmp > 0){
            node.right = remove(node.right, key);
        }
        if (cmp == 0) {// node是要删除的节点
            if (node.left == null || node.right == null) {
                return node.left == null ? node.right : node.left; //tree = (tree.left!=null) ? tree.left : tree.right;

                //a) 如果节点的左右子树有一棵为空，则使用非空子树填补空缺。如果被删除节点是叶子节点，则不需要填补空缺
            }
             T successorKey = successorOf(node).key;//有双子树，寻找代替点
            node = remove(node, successorKey);//？？？删除要替代 的节点
            node.key = successorKey;
        }

        if (Math.abs(height(node.left) - height(node.right)) > MAX_HEIGHT_DIFFERENCE) {
            node = balance(node);
        }
        refreshHeight(node);
        return node;
    }

    /**
     * 寻找被删除节点的继承者
     */
    private Node<T> successorOf(Node<T> node) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (node.left == null || node.right == null) {
            return node.left == null ? node.right : node.left;
        }

        return height(node.left) > height(node.right) ?
                findMax(node.left, node.left.right, node.left.right == null) :
                findMin(node.right, node.right.left, node.right.left == null);
        //则使用节点的左右子树中更高的那棵子树中的最大/最小节点来填补空缺（如果子树高度一致则哪边都可以）
    }

    private Node<T> findMax(Node<T> node, Node<T> right, boolean rightIsNull) {
        if (rightIsNull) {
            return node;
        }
        return findMax((node = right), node.right, node.right == null);
    }

    private Node<T> findMin(Node<T> node, Node<T> left, boolean leftIsNull) {
        if (leftIsNull) {
            return node;
        }
        return findMin((node = left), node.left, node.left == null);
    }

    private Node<T> balance2(Node<T> node) {
        Node<T> node1, node2;
        // ll & l
        if (height(node.left) > height(node.right) &&
                height(node.left.left) >= height(node.left.right)) {
            node1 = node.left;
            node.left = node1.right;
            node1.right = node;

            refreshHeight(node);
            return node1;
        }
        // lr
        if (height(node.left) > height(node.right) && height(node.left.right) > height(node.left.left)) {
            node1 = node.left;
            node2 = node.left.right;
            node.left = node2.right;
            node1.right = node2.left;
            node2.left = node1;
            node2.right = node;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        // rr & r
        if (height(node.right) > height(node.left) &&
                height(node.right.right) >= height(node.right.left)) {
            node1 = node.right;
            node.right = node1.left;
            node1.left = node;

            refreshHeight(node);
            return node1;
        }
        // rl
        if (height(node.right) > height(node.left) &&
                height(node.right.left) > height(node.right.right)) {
            node1 = node.right;
            node2 = node.right.left;
            node.right = node2.left;
            node1.left = node2.right;
            node2.left = node;
            node2.right = node1;

            refreshHeight(node);
            refreshHeight(node1);
            return node2;
        }
        return node;
    }
}
