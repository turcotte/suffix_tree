/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

import java.util.LinkedList;

/**
 * Used to build the tree; the tree structure itself is very simple.
 * Implements Ukkonen algorithm.
 */

public final class TreeBuilder {

    /**
     * the current node; it is always the node where the
     * substring S[ j..i] ends (where j is the index of the previous phase)
     */

    private LinkedList current;

    /**
     * the tree in which the string is added
     */

    private SuffixTree myTree;

    /**
     * if the current internal node was just inserted, it does not have
     * a suffix link attached to it; in this case, we have to remeber the string
     * that labels the branch leading to this node
     * (gamma will be followed down from the node at the end of the suffix link
     * starting form the parent of this node
     */

    private String gamma;

    /**
     * the index of the current token;
     * it is assigned in the order of insertion of the strings in the tree
     */

    private int tokenIndex;

    /**
     * preserves the internal node that was previously added to the tree,
     * as well as its parent
     * this is necesary when the chain of explicit extensions ends in the
     * current phase, so that we can restore the starting point for the next
     * phase
     */

    private LinkedList previous;

    /**
     * records the last extension phase, so that we know where to start form
     * when inserting a new suffix in the tree
     */

    private int lastExtPhase;

    /**
     * the string that is currently inserted
     */

    private String token;

    /**
     * the index of the first char ofthis string, relatively to the first
     * char of the first string inserted in the tree
     */

    private int startPos;

    /**
     * number of nodes added to the tree during the insertion of this string
     */

    private int noNodes;

    /**
     * store the token length, so that we don't have to call token.length() each
     * time (expensive)
     */

    private int tokenLen;

    /**
     * store the gamma length, so that we don't have to call gamma.length() each
     * time (expensive)
     */

    private int gammaLen;

    /**
     * Initialized always at the root of the tree, 
     * each time a new string is added to the tree.
     * @stereotype constructor 
     * @input one string that is to be added to the given tree
     * @output no output; constructs the tree, or adds to its structure
     * @preconditions a tree has to be constructed first
     * @postconditions the tree has all the suffixes of the given string
     */
    public TreeBuilder(SuffixTree tree) {
        this.current = new LinkedList();
        this.previous = new LinkedList();
        /**
         * initialize the previous and current list of nodes
         */
        this.current.addFirst(tree.getRoot());
        this.previous.addFirst(tree.getRoot());
        this.myTree = tree;
        this.token = null;
        this.tokenLen = 0;
        /**
         * always initialize the last extension phase to -1, since no phase was
         * done
         */
        this.lastExtPhase = -1;
        this.gamma = "";
        this.gammaLen = 0;
        this.noNodes = 0;
    }

    /**
     * add a string to the tree this builder is constructed for
     */
    public final void addToken(String token) {
        this.token = token;
        this.tokenIndex = this.myTree.addToken(token);

        /**
         * the string is already in the tree, or it does not have the proper
         * alphabet
         */

               if(this.tokenIndex == -1) { return; }
        this.tokenLen = this.token.length();
        this.startPos = this.myTree.getStart(this.tokenIndex);
        int lastExplicit = this.startPos -1;
        /**
         * first extension of the first phase of any string does not require
         * any going up or down
         */
        this.extend(this.startPos, this.startPos);
        /**
         * start the phases;
         * note that each phase starts from the last explicit extension
         */
        for ( int i = 1 + this.startPos; i < this.tokenLen + this.startPos; i++) {
            boolean isExplicit = true;
            while((isExplicit) && (lastExplicit < i)) {
        	lastExplicit++;
                    this.goUp(lastExplicit, i);
                this.goDown();
                isExplicit = this.extend(i, lastExplicit);
            }
            if(!isExplicit) {
        	lastExplicit--;
                this.goToLastExplicit();
            }
        }
        this.myTree.updateNoNodes(this.noNodes);
    }

    /**
     * currentSuffix is the j index of the current phase, and the last explicit
     * extension index + 1
     * @preconditions the previous extension was explicit
     */
    private void goUp(int currentSuffix, int currentRightEnd) {
        /**
         * first save current position in case next extension will be done
         * by the rule 3, and to be able to add the sufix link
         */

        this.previous = (LinkedList)this.current.clone();
        if( this.current.getLast() instanceof InternalNode) {
            /**
             * the current node does not have a suffix link attached to it
             */
            if(((InternalNode)this.current.getLast()).getSuffixLink() == null) {
        	/**
        	 * this happens only after at least one explicit extension
                     * with InternalNode creation was made in this phase,
        	 * otherwise I would have had a suffix link;
                 * note that if the first node on the current list is the root,
                 * we cannot follow any suffix link
                     */
                    if((InternalNode)this.current.getFirst() != this.myTree.getRoot() ) {
        	    int leftIndex = ((InternalNode)this.current.getLast()).getLeftIndex();
        	    int length = ((InternalNode)this.current.getLast()).getLength();
        	    this.gamma = this.myTree.getSubstring(leftIndex, length);
                    this.gammaLen = length;
                    this.current.removeLast();
        	    this.current.addLast(((InternalNode)this.current.getFirst()).getSuffixLink());
                    this.current.removeFirst();
                    }else {
        	    int leftIndex = currentSuffix;
        	    int length = currentRightEnd - currentSuffix;
        	    this.gamma = this.myTree.getSubstring(leftIndex, length);
                    this.gammaLen = length;
                    if(this.current.size() > 1) {
                            this.current.removeLast();
                    }
                    }
            }
            /**
             * the current node has a suffix link attached to it;
             * note that this node has a path
             * S[last-explicit-extension-index...phase-of-last-explicit -1]
             * gamma in this case is
             * S[phase-of-last-explicit...current-phase -1]
             */
            else{
        	this.gamma = this.myTree.getSubstring(this.lastExtPhase,
        					      currentRightEnd - this.lastExtPhase);
                this.gammaLen = currentRightEnd - this.lastExtPhase;
        	if(this.current.size() > 1) {
        	    this.current.removeFirst();
                }
                this.current.addLast(((InternalNode)this.current.getFirst()).getSuffixLink());
        	this.current.removeFirst();
            }
        }else{
            /**
             * the last node of the current list is a LeafNode; this happens
             * when, in the previous extension I went down, the current gamma
             * ended on the branch to a leaf node, and I did not perform any
             * explicit extension
             */
            int leftIndex = currentSuffix;
            int length = currentRightEnd - currentSuffix;
            this.gamma = this.myTree.getSubstring(leftIndex, length);
            this.gammaLen = length;
            this.current.removeLast();
            InternalNode next = (InternalNode)((InternalNode)this.current.getFirst()).getSuffixLink();
            /**
             * in case the parent of this leaf is not the root, follow the
             * suffix link
             */
            if(next != null) {
                this.current.addLast(next);
                this.current.removeLast();
            }
        }
    }

    /**
     * walks down from node s(v) along the path gamma, using skip/count trick
     */
    private void goDown() {
        boolean where = true;
        while((where) && (gammaLen > 0)) {
            char branch = this.gamma.charAt(0);
            where = this.findChild(branch);
        }
    }

    /**
     * finds the child of the current node that has branchStart as its first character;
     * this function will succeed in finding the child since the string S[j-1..i]
     * is already in the tree
     * returns true if there are more branches to be followed
     */
    private boolean findChild(char branchStart) {
        /**
         * NOTE: findChild is called only when gamma is not empty; this means
         * that the curent node is an InternalNode
         * also, since the substring S[j..i] is already in the tree,
         * we are bounded to find a child that starts with the first char of gamma
         */
        NodeInterface temp = (NodeInterface)((InternalNode)this.current.getLast()).getFirstChild();
        /**
         * I need to preserve the previous node in my search, in case my gamma ends
         * on a branch, and that branch is not to the first child 
         */
        NodeInterface tempP = temp;
        /**
         * here the fact that a child node can be a leaf does not influence the result,
         * since if gamma's first symbol is found on a branch of a LeafNode
         * of the current string, gamma's length will be less than
         * the real current rightEnd of that branch
         */

        String branch = this.myTree.getSubstring(temp.getLeftIndex(),
        					 temp.getLength());
        char start = branch.charAt(0);
        /**
         * find the child whos branch starts with the first char of gamma
         */
        while(start > branchStart) {
            tempP = temp;
            temp = (NodeInterface)temp.getRightSybling();
            branch = this.myTree.getSubstring(temp.getLeftIndex(),
        				      temp.getLength());
            start = branch.charAt(0);
        }
        /**
         * the this.current list has only one element when the last explicit
         * extension was made from root
         */
        if(this.current.size() > 1) {
            this.current.removeFirst();
               }
        this.current.addLast(temp);
        /**
         * if gamma's length is greater than the length of the branch, return true
         * and set gamma to the remaining string;
         * if gamma's length is equal to the branch length, return false, and set gamma to
         * empty string; string S[j..i] finishes at current node
         * if gamma's length is less than the branch legth, string S[j..i] ends
         * somewhere inside this branch, leave gamma as it is, and return false
         */
        if(gammaLen > (temp.getLength())) {
            this.gamma = this.gamma.substring(temp.getLength());
            this.gammaLen = this.gamma.length();
            return true;
        } else {
            if(gammaLen == (temp.getLength())) {
                this.gamma = "";
                this.gammaLen = 0;
                return false;
            }else{
                /**
                 * add both the node whos branch starts with first char of gammma
                 * and its left sybling, because we need it to update its right sybling
                 * link
                 * if the node is the first child, it has no left sybling,
                 * so we leave only two nodes in the current list
                 */
                if(tempP != temp) {
        	    this.current.add(1, tempP);
                }
        	return false;
            }
        }
    }

    /**
     * @postconditions returns true if the extension was explicit 
     */
    private boolean extend(int phase, int extension) {
        boolean isExplicit = false;
        char phaseChar = this.token.charAt(phase - this.startPos);
        /**
         * NOTE: string gamma is S[j..i], where i is the index of the previous phase
         * since index i is not the last character in the current string, it cannot be
         * the string terminator, therefore, the node where it ends cannot be a LeafNode
         * of one of the previously inserted strings
         * moreover, each LeafNode of the current string has the rightIndex = to i+1, the index
         * of the current phase, which makes it impossible for the string S[j..i]
         * to end at it unless i = -1 ! meaning that I am in phase zero of the first string
         */
        if( gammaLen == 0) {
            /**
             * find the start char of the branch to first child, if there is one
             */
            NodeInterface temp = (NodeInterface)((InternalNode)this.current.getLast()).getFirstChild();
            if (temp == null) {
                this.noNodes +=1;
        	LeafNode t = new LeafNode(this.startPos, this.tokenLen, null);
                ((InternalNode)this.current.getLast()).setFirstChild(t);
                isExplicit = true;
            }else{


                String branch = this.myTree.getSubstring(temp.getLeftIndex(),
        						 temp.getLength());
                char start = branch.charAt(0);

        	NodeInterface tempP = temp;
        	char startP = start;

        	boolean goRight = (phaseChar <= start)&&(temp.getRightSybling() != null);
                   while(goRight) {
        	    tempP = temp;
        	    startP = start;
        	    temp = (NodeInterface)temp.getRightSybling();
        	    if(temp == null) {
        		goRight = false;
        	    }else{
        		/**
                         * check if temp is a LeafNode of the current string
                         */
        		int branchStart = temp.getLeftIndex();
        		int branchLen = 0;
        		if(branchStart < this.startPos) {
        		    branchLen = temp.getLength();
        		}else{
        		    branchLen = phase - branchStart + 1;
        		}
        		branch = this.myTree.getSubstring(branchStart, branchLen);

        		start = branch.charAt(0);
        		goRight = (phaseChar <= start);
        	    }
                   }

                  if(startP == phaseChar) {
        	    /**
        	     * rule 3: do nothing
        	     * actually, this is where we have to check if the currently
        	     * inserted string ends at this leaf node;
        	     * if it does, than we have to insert a new set of coordinates,
        	     * without creating a new leaf node
        	     */
        	    String str1 = this.token.substring(phase-this.startPos);
        	    String str2 = this.myTree.getSubstring(tempP.getLeftIndex(), tempP.getLength());
        	    if(str1.equals(str2)) {
                        ((LeafNode)tempP).addCoordinates(extension);
        		isExplicit = true;
        	    }else{
                        isExplicit = false;
        	    }
                   }else{
        	    this.noNodes +=1;
        	    LeafNode t = new LeafNode(phase, this.tokenLen - (phase - this.startPos), null);
        	    t.addCoordinates(extension);
        	    if(phaseChar < startP) {
                    	/**
        		 * tempP holds the node after which I have to insert the new leafNode
        		 * rule 2, without creating a new internal node
        		 */
                    	t.setRightSybling(tempP.getRightSybling());
        		if(tempP instanceof LeafNode) {
        		    ((LeafNode)tempP).setRightSybling(t);
        		}else{
        		    ((InternalNode)tempP).setRightSybling(t);
        		}
        	    }else{
                    	/**
        		 * tempP is the first child of this.current.getLast()
        		 * and t must be inserted before it
        		 */
                    	t.setRightSybling(tempP);
        		((InternalNode)this.current.getLast()).setFirstChild(t);
        	    }
        	    isExplicit = true;
        	}
            }
        }else{
            /**
             * string gamma ends somewhere on the branch between the first
             * and the last node of the current list
             * it does not matter if the last element is a leaf node
             * in case it is, and in case it is a leaf node of the current string
             * all I need is the next character after the end of the gamma string
             * I am not interested in any other characters, so the fact that my
             * branch is not yet completed, since I didn't get
             * to the last phase, doesn't affect the algorithm
             */
            NodeInterface down = (NodeInterface)this.current.getLast();
            String branch = this.myTree.getSubstring(down.getLeftIndex(),
        					     down.getLength());
            char nextChar = branch.charAt(gammaLen);
            if(nextChar == phaseChar) {
                /**
                 * do nothing; extension rule 3
                 * remove the left sybling in case there is one
                 */
                if(this.current.size() > 2) {
        	    this.current.remove(1);
                }
                String str1 = this.token.substring(phase-this.startPos);
                String str2 = this.myTree.getSubstring(down.getLeftIndex() + gammaLen,
        					       down.getLength() - gammaLen) ;
                if(str1.equals(str2)) {
        	    ((LeafNode)down).addCoordinates(extension);
                    isExplicit = true;
                    }else{
        	    isExplicit =  false;
                    }
            }else{
                /**
                 * insert a new internal node on the branch, and add a new leaf too
                 * split the branch;
                 * the down's node rightSybling always becomes the rightSybling
                 * of the new internal node
                 */
                this.noNodes +=1;
                InternalNode tempI = new InternalNode(down.getLeftIndex(),
        					      gammaLen, down.getRightSybling(), null);
                if(down instanceof InternalNode) {
        	    ((InternalNode)down).setLeftIndex(down.getLeftIndex() + gammaLen);
        	    ((InternalNode)down).setLength(down.getLength() - gammaLen);
        	    ((InternalNode)down).setRightSybling(null);
                }else{
        	    ((LeafNode)down).setLeftIndex(down.getLeftIndex() + gammaLen);
        	    ((LeafNode)down).setLength(down.getLength() - gammaLen);
        	    ((LeafNode)down).setRightSybling(null);
                }

        	/**
                 * create new leaf node, but do not setup the links yet
                 */
                this.noNodes +=1;
                LeafNode tempL = new LeafNode(phase, this.tokenLen -(phase-this.startPos), null);
                tempL.addCoordinates(extension);

                /**
                 * if the branch on which we perform insertion is not the first
                 * branch, the middle element of this.current will have tempI
                 * as new rightSybling
                 * otherwise, the first element of this.current will have tempI
                 * as new first child
                 */
        	if(this.current.size() > 2) {
                    Object removed = this.current.remove(1);
        	    if(removed instanceof InternalNode) {
        		((InternalNode)(removed)).setRightSybling(tempI);
                    }else{
                        ((LeafNode)(removed)).setRightSybling(tempI);
                    }
                }else{
                    ((InternalNode)this.current.getFirst()).setFirstChild(tempI);
                }

        	/**
                 * now setup the children of new internal node tempI
                 */
                if(nextChar > phaseChar) {
                    tempI.setFirstChild((NodeInterface)this.current.getLast());
                    Object last = this.current.getLast();
                    if(last instanceof LeafNode) {
                            ((LeafNode)this.current.getLast()).setRightSybling(tempL);
                    }else{
        		((InternalNode)this.current.getLast()).setRightSybling(tempL);
                    }
                }else{
        	    tempI.setFirstChild(tempL);
                    tempL.setRightSybling((NodeInterface)this.current.getLast());
                }
        	/**
                 * replace this.current last element with  newly created internal
                 * node, since it represents the end of substring S[j..i]
                 * needed for the next extension performed, and for
                 * suffix link setup
                 */
                this.current.removeLast();
                this.current.addLast(tempI);
                /**
                 * sice I am creating a new internal node at the end of gamma
                 * set gamma to empty string
                 */
                this.gamma = "";
                this.gammaLen = 0;
                /**
                 * extension rule 2 with creation of a new internal node
                 */
                isExplicit = true;
            }
        }
        /**
         * setup suffix link if it is necessary; note that there is no
         * suffix link to add in the case previous does not contain any element
         * which happens when we are inserting a string into an already grown tree
         */
        if((this.previous.size() != 0) &&
           (previous.getLast() instanceof InternalNode) ) {
            if((((InternalNode)previous.getLast()).getSuffixLink() == null)&&
               ((InternalNode)this.previous.getLast() != this.myTree.getRoot())) {
        	((InternalNode)this.previous.getLast()).setSuffixLink((InternalNode)this.current.getLast());
            }
        }

        if (isExplicit) {
            this.lastExtPhase = phase;
        }
        return isExplicit;
    }

    /**
     * just before ending the current phase, after an implicit extension
     * was made
     */

    private void goToLastExplicit( ) {
        this.current = (LinkedList)this.previous.clone();
    }

}
