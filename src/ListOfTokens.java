/* This copyrighted source code is freely distributed under the terms
 * of the GNU General Public License. 
 *
 * See LICENSE for details.
 *
 * Copyrights (C) 2003-7 University of Ottawa, D. Cernea and M. Turcotte
 *
 * Contact: turcotte@site.uottawa.ca
 */

class ListOfTokens {

    /**
     * head of the list
     */

    private Token head;

    ListOfTokens() {
        this.head = null;
    }

    /**
     * returns token position
     * inserts token as string if the token is not already there 
     */

    final int insertToken(String token) {
              int position = 0;

               if (this.head == null) {  // empty list
            Token temp = new Token();
            temp.setToken(token);
            this.head = temp;
        } else {
            Token current = this.head;
            while (current != null) { //list not at end

        	if (! (current.getToken()).equals(token)) { //current element different from token
        	    position++;
                    if (current.getNext() != null) { //there are more to check
                            current = current.getNext();
                    } else { // no more to check, have to insert
                        Token temp = new Token();
                        temp.setToken(token);
                        current.setNext(temp);
                        break;
                    }
                } else { //found it, do not insert
                    break;
                }
            }
        }

        return position;
    }

    /**
     * returns the substring at the position "position", where position is counted
     * from the first symbol of the first token (cumulative position) 
     */

    final String getSubstring(int leftIndex, int length) {
        int count=0;
        Token next = null;
        Token current = this.head;
        int len = 0;
        while( leftIndex >= count +  (len=current.getLen())) {
            count += len;
            if((next = current.getNext())!= null) { current = next; }
        }
        return (current.getToken()).substring(leftIndex - count, leftIndex - count + length);
    }

    /**
     * returns the substring at the position "position", to the end of the
     * string,  where position is counted
     * from the first symbol of the first token (cumulative position)
     */

    final String getSubstring(int leftIndex) {
        int count=0;
        Token next = null;
        Token current = this.head;
        int len = 0;
        while( leftIndex >= count +  (len=current.getLen())) {
            count += len;
            if((next = current.getNext())!= null) { current = next; }
        }
        return (current.getToken()).substring(leftIndex - count);
    }

    /**
     * returns the starting index of the string containing "position"
     */

    final int getStart(int position) {
        int pos = 0;
        Token cur = this.head;
        for(int i=0; i<position; i++) {
            pos += cur.getLen();
            cur = cur.getNext();
        }
        return pos;
    }

    /**
     * Returns the index of the string that contains position.
     */

    final int getIndex(int position) {

        int len, current = 0, index = 0;
        Token p = head;

        while (current + (len = p.getLen()) <= position) {
            current += len;
            p = p.getNext();
            index++;
        }

        return index;
    }
    
    /**
     * Returns the length of the Longest Common Extension (LCE), starting
     * at position i of p, and j within the list of tokens.
     */

    final int getLCE(String p, int i, int j, int length) {

        // Move to j

        Token current = head;
        int count = 0;

        while (j > count + current.getLen()) {

            count += current.getLen();
            current = current.getNext();

            if (current == null) 
        	throw new IndexOutOfBoundsException();
        }

        String token = current.getToken();
        int tokenLen = token.length(), offset = j - count;
        int pLen = p.length(), match = 0;

        while ((i < pLen) && 
               (offset < tokenLen) && 
               (match < length) && 
               (p.charAt(i) == token.charAt(offset))) {
            i++;
            offset++;
            match++;
        }

        return match;
    }


    /**
     * returns the total length of the strings contained in this list
     */
    final int getTotalLength() {
        int len = 0;
        Token cur = this.head;
        while(cur != null) {
            len += cur.getLen();
            cur = cur.getNext();
        }
        return len;
    }

}
