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
 * Simple object that contains a string, and a link to the next token.
 */

class Token {

    private String token;

    private Token next;

    Token() {
        this.next = null;
        this.token = null;
    }

    Token(String token, Token next) {
        this.token = token;
        this.next = next;
    }

    void setToken(String tokenStr) { 
        this.token = tokenStr; 
    }

    int  getLen() {
        return this.token.length();
    }

    String getToken() { 
        return this.token; 
    }

    Token getNext() { 
        return this.next;
    }

    void setNext(Token next) {
        this.next = next; 
    }

}
