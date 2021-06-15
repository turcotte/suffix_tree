# Java suffix tree library

Java suffix tree library developed by Daniela Cernea at the University of Ottawa

## Description

This suffix tree library was developed by Daniela Cernea in partial
fulfillment of the requirements for her Honours project under the
supervision of Marcel Turcotte at the University of Ottawa during the
winter of 2003.

Contact: marcel.turcotte@uottawa.ca

## Warnings

* The codebase predates Java 1.5. Sadly, generics are not used.

* As the following
  [stack overflow post](https://stackoverflow.com/questions/4679746/time-complexity-of-javas-substring)
  explains, in the transition from Java 1.6 to 1.7, the behaviour of
  *substring* changed to create copies. The implication is that the
  execution time for the construction of the suffix tree is no longer
  linear!

## Status

The library was not further developed after 2003. It is still useful
for didactic purposes. Fixing the above two issues could be a good way
to learn about suffix trees.

## License

This copyrighted source code is freely distributed under the terms
of the GNU General Public License.

See LICENSE for details.

