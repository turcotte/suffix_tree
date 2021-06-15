/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

public final class SuffixTree {

    /**
     * the root of the tree
     */

    private InternalNode root;

    /**
     * permitted characters in the strings
     */

    private String alphabet;

    /**
     * the strings in the tree
     */

    private ListOfTokens tokens;

    /**
     * total number of nodes in the tree
     */

    private int noNodes;

    /**
     * Alphabet specifies what characters are allowed in the strings added to the suffix tree.
     * @stereotype constructor 
     */

    public SuffixTree(String alphabet) {
        this.alphabet = alphabet;
        this.root = new InternalNode();
        this.tokens = new ListOfTokens();
        this.noNodes = 1;
    }

    /**
     * adds a new string to the suffix tree 
     */
    final int addToken(String token) {
        return this.tokens.insertToken(token);
    }

    /**
     * returns the root of the tree
     */
    final InternalNode getRoot() {
        return this.root;
    }

    /**
     * returns the substring starting at leftIndex and having length length
     * from the string collection
     */
    final String getSubstring(int leftIndex, int length) {
        return this.tokens.getSubstring(leftIndex, length);
    }

    /**
     * Returns the length of the Longest Common Extension (LCE), starting
     * at position pos of the pattern, and leftIndex from the string collection.
     *
     * @return the length of the longest common extension from position i of
     * the pattern and the starting position of the label of this node.
     */

    final int getLCE(String pattern, int pos, NodeInterface node) {
        return tokens.getLCE(pattern, pos, node.getLeftIndex(), node.getLength());
    }

    /**
     * returns the suffix starting at leftIndex 
     */

    final String getSubstring(int leftIndex) {
        return this.tokens.getSubstring(leftIndex);
    }

    /**
     * returns the starting index of the token at indexToken
     */

    final int getStart(int indexToken) {
        return this.tokens.getStart(indexToken);
    }

    /**
     * Returns the index of the token that contains position.
     */

    final int getIndex(int position) {
        return this.tokens.getIndex(position);
    }

    /**
     * returns the total length of the strings in the tree
     */

    final int getTotalLength() {
        return this.tokens.getTotalLength();
    }

    /**
     * returns the total no of nodes in the tree
     */

    public final int getNoNodes() {
        return this.noNodes;
    }

    /**
     * updates the total no of nodes in the tree, after a new string was
     * inserted
     */

    final void updateNoNodes(int value) {
        this.noNodes += value;
    }

}


