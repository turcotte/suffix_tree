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
 * Interface to access the node information.  Implemented by
 * InternalNode and LeafNode.
 *
 * @stereotype interface 
 */

public interface NodeInterface {

    /**
     * @return the length on the branch leading to this node.
     */

    int getLeftIndex();

    /**
     * @return the length of the branch leading to this node.
     */

    int getLength();

    /**
     * @return the right sybling of this node, if there is one.
     */

    Object getRightSybling();

    /**
     * @return the first Info object that is associated with the node.
     * One example could be the LcaInfo.
     */

    Info getInfo();

    /**
     * @return the first Info object that is associated with the node.
     * One example could be the LcaInfo.
     */

    void setInfo(Info info);
}
