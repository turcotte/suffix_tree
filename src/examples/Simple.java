/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

class Simple {

    public static void main(String[] args) {

        SuffixTree tree = new SuffixTree("");

        TreeBuilder builder = new TreeBuilder(tree);

        builder.addToken("mississippi$");

        TreePrinter printer = new TreePrinter(tree);

        printer.prettyPrint();
    }
}

/*
 * $ java Simple
 *
 * <node root>
 *   <node label=s>
 *     <node label=si>
 *       <leaf label=ssippi$ pos=2>
 *       <leaf label=ppi$ pos=5>
 *     </node>
 *     <node label=i>
 *       <leaf label=ssippi$ pos=3>
 *       <leaf label=ppi$ pos=6>
 *     </node>
 *   </node>
 *   <node label=p>
 *     <leaf label=pi$ pos=8>
 *     <leaf label=i$ pos=9>
 *   </node>
 *   <leaf label=mississippi$ pos=0>
 *   <node label=i>
 *     <node label=ssi>
 *       <leaf label=ssippi$ pos=1>
 *       <leaf label=ppi$ pos=4>
 *     </node>
 *     <leaf label=ppi$ pos=7>
 *     <leaf label=$ pos=10>
 *   </node>
 *   <leaf label=$ pos=11>
 * </node>
 *
 */
