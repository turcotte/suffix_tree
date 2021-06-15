/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

import java.io.*;

// Reads an input file line by line. Adds each line to a StringBuffer,
// except for the newline characters. Returns the result as a String.
// Does not handle Exceptions.

class Copy {

    private static String copy(String fileName) 
        throws FileNotFoundException, IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        StringBuffer copy = new StringBuffer();

        String line = null;

        while ((line = in.readLine()) != null) {
            copy.append(line);
        }

        in.close();

        return copy.toString();
    }

    public static void main(String[] args)
        throws FileNotFoundException, IOException {

        String s;

        for (int i=0; i<args.length; i++)
            s = copy(args[i]);

    }

}
