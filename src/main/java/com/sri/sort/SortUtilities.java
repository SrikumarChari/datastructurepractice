/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sri.sort;

/**
 *
 * @author schari
 */
public class SortUtilities {

    //set of sorting functions - different types of alogrithms

    public static <T extends Comparable> void insertionSort(T[] source, SortType sortType) {
        for (int j = 1; j < source.length; j++) {
            T key = source[j];
            int i = j - 1;

            while (i > -1 ) {
                if (sortType == SortType.ASCENDING && source[i].compareTo(key) < 0) {
                    //reached a value greater than key
                    break;
                } else if (sortType == SortType.DESCENDING && source[i].compareTo(key) > 0) {
                    //reached a value less than key
                    break;
                }
                source[i + 1] = source[i];
                i--;
            }
            source[i + 1] = key;
        }
    }
    
    public static <T extends Comparable> void mergeSort(T[] source, SortType sortType) {
        
    }
    
    private static <T extends Comparable> void merge(T[] source, SortType sortType, int p, int q, int r) {
        for (int k = p, i = p, j = q+1; k < r && i <= q && j < r; k++) {
            if (sortType == SortType.ASCENDING) {
                if (source[i].compareTo(source[j]) <= 0 ) {
                    source[k] = source[i];
                    i++;
                } else {
                    source[k] = source[j];
                    j++;
                }
            } else if (sortType == SortType.DESCENDING) {
                
            }
        }
    }
}
