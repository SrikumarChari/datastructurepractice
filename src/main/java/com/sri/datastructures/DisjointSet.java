package com.sri.datastructures;

import java.util.Iterator;

public class DisjointSet<E extends Comparable<E>> {
    //will contain a list of sets represented as a list, i.e., list of lists.
    //Here are some rules:
    //		a) E is the set member
    //		b) The set representative will always be the head of the list
    //		c) We can access next and previous set members using List/ListElement functionality
    //		d) No order is guaranteed within a set and also no order within the list of sets "sets"

    private List<List<E>> sets;

    public DisjointSet() {
        sets = new List<List<E>>();
    }

    public String toString() {
        String outStr = "";
        for (List<E> aSet : sets) {
            int count = 0;
            for (E anElement : aSet) {
                if (count == 0) {
                    //print the representative member
                    outStr += "Rep Member: " + anElement.toString() + "\t\t";
                } else {
                    //print a regular member
                    outStr += "Member: " + anElement.toString() + "\t";
                }
                count++;
            }
            outStr += "\n";
        }
        return outStr;
    }

    //find the set that contains "member"
    public List<E> findSet(E member) {
        //iterate through all the sets
        for (List<E> aSet : sets) {
            if (aSet.search(member) != null) {
                return aSet;
            }
        }
        return null;
    }

    public boolean makeSet(E repMember) {
        //see if the set exists... only search the representative members
        for (List<E> aSet : sets) {
            if (aSet.getHead().compareTo(repMember) == 0) //set already exists
            {
                return false;
            }
        }

        //create a new list for the set
        List<E> newSet = new List<E>();
        newSet.add(repMember);

        //add the new sets to the list of sets
        sets.add(newSet);
        return true;
    }

    public boolean union(E repMember1, E repMember2) {
        List<E> set1 = findSet(repMember1);
        List<E> set2 = findSet(repMember2);
        if (set1 == null || set2 == null) //can't create an union if both are not existing sets
        {
            return false;
        }

        //combine the two lists the representative member of set2 will become the representative member of set1
        //since there is no ordering guarantee, we create a list with all elements of set1 and set2
        //with repMemeber2 as the head of this combined list
        //to makes things a bit efficient, we will append the smaller list to the larger list
        List<E> src, dest;
        Iterator<E> itSrc;
        if (set1.getCount() < set2.getCount()) {
            src = set1;
            dest = set2;
            itSrc = src.iterator();
            //no need to insert the head of set2 because we are appended to
            //set2 where repMember2 is already the head
        } else {
            src = set2;
            dest = set1;
            itSrc = src.iterator();
            //insert the head of set2 because repMember2 now is the
            //representative member of the union.
            dest.insertHead(itSrc.next());
        }

        while (itSrc.hasNext()) {
            E setE = itSrc.next();
            dest.add(setE);
        }
        //delete the src list from the list of sets
        sets.delete(src);
        return false;
    }

    public E leastCommonAncestor(E u) {
        return u;
    }

    public int getCount() {
        return sets.getCount();
    }
}
