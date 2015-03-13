package com.sri.utility;

import java.math.BigInteger;
import java.util.Random;

import com.sri.datastructures.List;

//provides a bunch of string matching functions
public class StringMatcher {
	/*
	 * checks to see if s1 and s2 match or if s2 is a substring of s1
	 * as the name suggests it is the slowest of the lot but for small string this will work great
	 */
	public static boolean naiveStringMatcher (char[] text, char[] pattern) {
		return naiveStringMatcher(text, pattern);
	}
	public static List<Integer> naiveStringMatcher (String text, String pattern) {
		//create a list of integers to hold the match locations
		List<Integer> matches = new List<Integer> ();
		
		int textLength = text.length();
		int patternLength = pattern.length();
		for (int s = 0; s <= textLength - patternLength; s++) {
			if (isExactMatch(text.substring(s, s+patternLength), pattern))
				matches.add (s);
		}
		return matches;
	}
	
	//use the Rabin-Karp approach for a single pattern
	public static List<Integer> rkStringMatcher (String text, String pattern) {
		long prime = buildLargePrime(); //generate a large prime
		int radix = 10; //for now we default to 10
		return rkStringMatcher(text, pattern, radix, prime);
	}
	

	//Rabin-Karp method for multiple patterns of varying lengths
	public static List<List<Integer>> rkStringMatcher (String text, String[] pattern) {
		long prime = buildLargePrime(); //generate a large prime
		int radix = 10; //for now we default to 10
		return rkStringMatcher(text, pattern, radix, prime);
	}
	

	/*
	 * Rabin-Karp matcher - exact implementation from CLRS
	 */
	private static List<Integer> rkStringMatcher (String text, String pattern, int radix, long prime) {
		//create a list to hold the starting indices of the places where match occurs
		List<Integer> matches = new List<Integer> ();
		
		int textLength = text.length();
		int patternLength = pattern.length();
		long h = (long) (Math.pow(radix, (patternLength-1)) % prime);
		long patternHash = 0, textHash = 0;
		
		//pre-process strings to generate initial hashes
		for (int i = 0; i < patternLength; i++) {
			patternHash = (radix * patternHash + pattern.charAt(i)) % prime;
			textHash = (radix * textHash + text.charAt(i)) % prime;
		}

		//now try to match the strings
		for (int s = -1; s < textLength - patternLength; s++) {
			if (patternHash == textHash) {
				//hash codes match, now see if there is an exact match
				if (isExactMatch(text.substring(s+1, s+patternLength+1), pattern))
					matches.add(s+1);
			}
			if (s < textLength - patternLength - 1)
				//remove the leading digit and add the trailing digit
				textHash = radix * (textHash - text.charAt(s+1) * h) + text.charAt(s+patternLength+1) % prime;
		}
		return matches;
	}

	/*
	 * TODO: is it possible to 
	 * 
	 * searching a text string for an occurrence of any one of a given set of k
	 * patterns. the patterns  have different lengths. 
	 * 
	 * 32.2-3 Show how to extend the Rabin-Karp method to handle the
	 * problem of looking for a given m by m pattern in an n by n array of
	 * characters. (The pattern may be shifted vertically and horizontally, but
	 * it may not be rotated.)
	 * 
	 */
	private static List<List<Integer>> rkStringMatcher (String text, String[] patterns, int radix, long prime) {
		//create a list to hold lisst of the starting indices of the places where match occurs
		List<List<Integer>> matches = new List<List<Integer>> ();
		for (int i = 0; i < patterns.length; i++) {
			List<Integer> m = new List<Integer>();
			matches.add(m);
		}

		//lengths of text and patterns
		int textLength = text.length();
		int[] patternLengths = new int[patterns.length];
		for (int i = 0; i < patterns.length; i++)
			patternLengths[i] = patterns[i].length();
		
		//hash values, since pattern lengths could be different, we need one for each pattern
		long[] h = new long[patterns.length];
		for (int i = 0 ; i < patterns.length; i++)
			h[i] = (long) (Math.pow(radix, (patternLengths[i]-1)) % prime);
		
		//arrays for the actual hash
		long[] patternHash = new long [patterns.length], textHash = new long[patterns.length];
	
		
		//pre-process the text to generate initial hash
		for (int i = 0; i < patterns.length; i++) {
			for (int j = 0; j < patterns[i].length(); j++) {
				textHash[i] = ((radix * textHash[i]) + text.charAt(j)) % prime;
				patternHash[i] = ((radix * patternHash[i]) + patterns[i].charAt(j)) % prime;
			}
		}

		//now try to match the strings
		for (int i = 0; i < patterns.length; i ++) {
			for (int s = -1; s < textLength - patternLengths[i]; s++) {
				if (patternHash[i] == textHash[i]) {
					//hash codes match, now see if there is an exact match
					if (isExactMatch(text.substring(s+1, s+patternLengths[i]+1), patterns[i]))
						matches.get(i).add(s+1);
				}
				if (s < textLength - patternLengths[i] - 1)
					//remove the leading digit and add the trailing digit
					textHash[i] = radix * (textHash[i] - text.charAt(s+1) * h[i]) + text.charAt(s+patternLengths[i]+1) % prime;
			}
		}
		return matches;
	}

	private static boolean isExactMatch (String text, String pattern) {
		int n = text.length();
		int m = pattern.length();
		if (n != m)
			return false;
		
		for (int i = 0; i < n; i++) {
			if (text.charAt(i) != pattern.charAt(i))
				return false;
		}
		return true;
	}
	
	private static long buildLargePrime() {
		BigInteger prime = BigInteger.probablePrime(31, new Random());
		return prime.longValue();
	}
}
