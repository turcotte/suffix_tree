/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

final class InternalNode implements NodeInterface {

    /**
     * The starting index of the branch that leads to this node.
     * Note that the index is calculated relatively to the first char of
     * the first string inserted in the tree.
     */

    private int leftIndex;

    /**
     * The length of the branch leading to this node. 
     */

    private int length;

    /**
     * Link to the right sybling of this node (if there is one). 
     */

    private Object rightSybling;

    /**
     * Contains additinaol information about this node (such as lca info). 
     */

    private Info info;

    /**
     * Link to the first child of this node.  There are always at
     * least two children to this node.  The access to the rest of the
     * children is done following the right sybling(s) of the first
     * child.
     */

    private Object firstChild;

    /**
     * An internal node always has a sufix link. 
     */

    private Object suffixLink;

    /**
     * @stereotype constructor 
     */

    InternalNode() {
        this.leftIndex = 0;
        this.length = 0;
        this.rightSybling = null;
        this.firstChild = null;
              this.suffixLink = null;
        this.info = null;
    }

    /**
     * @stereotype constructor 
     */

    InternalNode(int leftIndex, int length, Object rightSybling, Object firstChild) {
        this.leftIndex = leftIndex;
        this.length = length;
        this.rightSybling = rightSybling;
        this.firstChild = firstChild;
        this.suffixLink = null;
        this.info = null;
    }

    final Object getFirstChild(){ return this.firstChild; }

    final void setFirstChild(Object firstChild){ this.firstChild = firstChild; }

    final Object getSuffixLink(){ return this.suffixLink; }

    final void setSuffixLink(Object suffixLink){ this.suffixLink = suffixLink; }

    public final int getLeftIndex() { return this.leftIndex; }

    final void setLeftIndex(int leftIndex){ this.leftIndex = leftIndex; }

    public final int getLength() { return this.length; }

    final void setLength(int length){ this.length = length; }

    public final Object getRightSybling() { return this.rightSybling; }

    final void setRightSybling(Object rightSybling){ this.rightSybling = rightSybling; }

    public final Info getInfo() { return this.info; }

    /**
     * sets an Info object to this node. If there is already one, it appends it
     * to a list of Info objects.
     */
    final public void setInfo(Info info) {
        if (this.info != null) {
            info.setNextInfo(this.info);
        }
        this.info = info;
    }
}
