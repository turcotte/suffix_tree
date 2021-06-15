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
 * This interface is to be implemented by new classes that need to add
 * their own information to the tree nodes.
 * 
 * Allows implementation of algorithms that use different information
 * associated to each node.
 *
 * @stereotype interface 
 */

public interface Info {

    /**
     * @return next object in the list of object that implement Info interface. 
     */

    Info getNextInfo();

    /**
     * @param object that implements Info interface. 
     */

    void setNextInfo(Info next);

}
