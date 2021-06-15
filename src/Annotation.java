/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

/**
 * An example of the kind of information that can be attached to the
 * nodes of the suffix tree: here, the sum of the length of all the
 * labels on the path from the root to the current node.
 *
 * @author Marcel Turcotte
 */

public class Annotation implements Info {

    private int pathLength;
    private Info next;

    /**
     * Stores pathLen in this Annotation object.
     * 
     * @param pathLen pathLen to be stored in this Annotation.
     */

    Annotation(int pathLength) {
        this.pathLength = pathLength;
    }

    /**
     * Returns the next element of information stored at that node.
     *
     * @return the next element of information stored at that node.
     */

    public Info getNextInfo() {
        return next;
    }
    
    /**
     * Add an element of information to this node.
     *
     * @param element of information to be added to this node.
     */

    public void setNextInfo(Info next) {
        this.next = next;
    }

    /**
     * Returns the pathLen.
     *
     * @return the pathLen.
     */

    public int getPathLength() {
        return pathLength;
    }

    /**
     * A class method to decorate a tree with pathLen information at each node. 
     *
     * @param a suffix tree to be decorated.
     */

    public static void addPathLength(SuffixTree tree) {

        InternalNode root = (InternalNode) tree.getRoot();

        if (root != null)
            addPathLength(0, (NodeInterface) root.getFirstChild());
    }

    private static void addPathLength(int prefix, NodeInterface node) {

        if (node == null)
            return;

        int pathLength = prefix + node.getLength();

        node.setInfo(new Annotation(pathLength));

        if (node instanceof InternalNode)
            addPathLength(pathLength, (NodeInterface) ((InternalNode) node).getFirstChild());

        addPathLength(prefix, (NodeInterface) node.getRightSybling());

    }

    /**
     * Returns a String representation of this and the following
     * elements of information.
     *
     * @return a String representation of this and the following
     * elements of information.
     */

    public String toString() {
        String out = "pathLength = " + pathLength;

        if (next != null)
            out = out + next.toString();

        return out;
    }

}
