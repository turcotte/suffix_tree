/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

final class LeafNode implements NodeInterface {

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
     * May contain additional information about this node (such as lca info).
     */

    private Info info;

    /**
     * the coordinates of one of the suffixes ending at this leaf node
     */

    private SuffixCoordinates coordinates;

    /**
     * @stereotype constructor 
     */

    LeafNode(int leftIndex, int length, Object rightSybling) {
        this.leftIndex = leftIndex;
        this.length = length;
        this.rightSybling = rightSybling;
        this.coordinates = null;
        this.info = null;
    }

    /**
     * @stereotype constructor 
     */

    LeafNode() {
        this.leftIndex = 0;
        this.length = 0;
        this.rightSybling = null;
        this.coordinates = null;
        this.info = null;
    }

    public final int getLeftIndex() { return this.leftIndex; }

    final void setLeftIndex(int leftIndex){ this.leftIndex = leftIndex; }

    public final int getLength() { return this.length; }

    final void setLength(int length){ this.length = length; }

    public final Object getRightSybling() { return this.rightSybling; }

    final void setRightSybling(Object rightSybling){ this.rightSybling = rightSybling; }

    /**
     * Returns the first coordinate object.
     */

    public final SuffixCoordinates getCoordinates() {
        return coordinates;
    }

    /**
     * prepends the current coordinates (an object that contains the starting
     * index of the current added suffix) to the list of coordinates -
     * that in case two strings have the same suffix
     */

    final void addCoordinates(int position) {
        SuffixCoordinates newCoord;
        if(this.coordinates != null) {
            newCoord = new SuffixCoordinates(position, this.coordinates);
        }else{
            newCoord = new SuffixCoordinates(position, null);
        }
        this.coordinates = newCoord;
    }

    /**
     * Returns the first info object.
     * The access to the next ones are done through the Info interface.
     */

    public final Info getInfo() { return this.info; }

    /**
     * prepends the current info to the list of already existing info objects
     */

    final public void setInfo(Info info) {
        if(this.info != null) {
            info.setNextInfo(this.info);
        }
        this.info = info;
    }

}
