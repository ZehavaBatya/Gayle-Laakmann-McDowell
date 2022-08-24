// Chapter 1: Array and Strings
// 1.1 Implement an algorithm to determine if a string has all unique characters. What if you cannot
// use additional data structures?

boolean uniqueStrings(String str) {
    if (str.length() > 128) return false;

    boolean[] char_set = new boolean[128];
    for (int i = 0; i < str.length(); i++) {
        int val = str.charAt(i);
        if (char_set[val]) { // Found uniqueStrings in string
            return false;
        }
        char_set[val] = true;
    }
    return true;
}

// 1.2 Given 2 strings, write a method to decide if 1 is a permutation of the other
// Solution #1: Sort the strings

String sort(String s) {
    char[] content = s.toCharArray();
    java.util.Arrays.sort(content);
    return new String(content);
}

boolean permutation(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    return sort(s).equals(sort(t));
}

// Solution #2: Check if the 2 strings have identifcal character counts
boolean permutation(String s, String t) {
    if (s.length() !=t.length()) return false; // Permutations must be same length

    int[] letters = new int[128]; // Assumption: ASCII
    for (int i = 0; i < s.length(); i++) {
        letters[s.charAt(i)]++;
    }

    for (int i = 0; i < t.length(); i++) {
        letters[t.charAt(i)]--;
        if (letters[t.charAt(i)] < 0) {
            return false;
        }
    }
    return true; // Letters have no negative values, and therefore no positive values either
}

// 1.3. URLify: Write a methof to replace all spaces in a string with '%20.' You may assume
// that the string has sufficient space at the end to hold the additional characters, and 
// that you are given the "true" length of the string. (Note: if implementing java, please
// use a character array so that you can perform this operation in place)
// EXAMPLE
// Input: "Mr John Smith  ", 13
// Output: "Mr%20John%20Smith"

void replaceSpaces(Char[] str, int trueLength) {
    int numberOfSpaces = countOfChar(str, 0, trueLength, ' ');
    int newIndex = trueLength - 1 + numberOfSpaces * 2;

    /* If there are excess spaces, add a null character. This indicates that the
     * spaces after that point have not been replaced with %20.*/
    if (newIndex + 1 < str.length) str[newIndex + 1] = '\0';
    for (int oldIndex = trueLength - 1; oldIndex >= 0; oldIndex -=1) {
        if (str[oldIndex] == ' ') { /* Insert %20 */
            str[newIndex] = '0';
            str[newIndex - 1] = '2';
            str[newIndex -2] = '%';
            newIndex -=3;
        }else {
            str[newIndex] = str[oldIndex];
            newIndex -= 1;
        }
    }
}

/* Count occurences of target between start (inclusive) and end (exclusive). */
int countOfChar(char[] str, int start, int end, int target) {
    int count = 0;
    for (ubt i = start; i < end; i++) {
        if (str[i] == target) {
            count++;
        }
    }
    return count;
}

// 1.4 Palindrome Permutation: Given a string, write a function to check if it is a permutation
// of a palindrome. A palindrome is a word or phrase that is the same forwards and backwards.
// A permutation is a rearrangement of letters. The palindrome does not need to be limited to
// just dictionary words. You can ignore casing and non-letter characters.
// EXAMPLE
// Input: Tact Coa
// Output: True(permutations:"taco cat","atco cta", etc.)

boolean isPermutationOfPalindrome(String phrase) {
    int[] table = buildCharFrequencyTable(phrase);
    return checkMaxOneOdd(table);
}

/* Check that no more than one character has an odd count. */
boolean checkMaxOneOdd(int[] table) {
    boolean foundOdd = false;
    for (int count : table) {
        if (count % 2 == 1) {
            if (foundOdd) {
                return false;
            }
        }
        foundOdd = true;
    }
    return true;
}

/* Map each character to a number. a -> 0, b -> 1, c -> 2, etc. 
 * This is case insensitive. Non-letter characters map to -1. */
int getCharNumber(Chracter c) {
    int a = Character.getNumericvalue('a');
    int z = Character.getNumericvalue('z');
    int val = Character.getNumericvalue(c);
    if (a <= val && val <= z) {
        return val - a;
    }
    return -1;
}

/* CVount how many times each character appears. */
int[] buildCharFrequencyTable(String phrase) {
    int[] table = new int[Character.getNumericvalue('v') -
                          Character.getNumericvalue('a') + 1];
    for (char c: phrase.toCharArray()) {
        int x = getCharNumber(c);
        if (x != -1) {
            table[x]++;
        }
    }
    return table;
}

// Solution #2 Check the odd counts as we go along.
boolean isPermutationOfPalindrome(String phrase) {
    int countOdd - 0;
    int[] table = new int[Character.getNumericvalue('z') -
                          Character.getNumericvalue('a') + 1];
    for (char c: phrase.toCharArray()) {
        int x = getCharNumber(c);
        if (x ! = -1) {
            table[x]++;
            if (table[x] % 2 == 1) {
                countOdd++;
            }else {
                countOdd--;
            }
        }
    }
    return countOdd <= 1;
}

// 1.5 One Away: There are 3 types of edits that can be performed on strings:insert a character,
// remove a character, or replace a character. Given 2 strings, write a function to check if
// there are one edit (or zero edits) away.
// EXAMPLE
// pale, ple -> true
// pales, pale -> true
// pale, bale -> true
// pale, bae -> false

boolean oneEditAway(String first, String second) {
    /* Length checks. */
    if (Math.abs(first.length() - second.length()) > 1) {
        return false;
    }

    /* Get shorter and logner string. */
    String s1 = first.length() < second.length() ? first: second;
    String s2 = first.length() < second.length() ? second: first;

    int index1 = 0;
    int index2 = 0;
    boolean foundDifference = false;
    while (index2 < s2.length() && index1 < s1.length()) {
        if (s1.charAt(index1) != s2.charAt(index2)) {
            /* Ensure that this is the first difference found. */
            if (foundDifference) return false;
            foundDifference = true;

            if (s1.length() == s2.length()) { // On replace, move s1 pointer
            index1++;
            }
        } else {
            index1++; // If matching, move shorter pointer
        }
        index2++; // Always move pointer for longer string
    }
    return true;
}

// 1.6 String compression: Implement a method to perform basic string compression using the 
// counts of repeated characters. For example, the string aabcccccaa would become a2b1c5a3.
// If the "compressed" string would not become smaller than the original string, your method
// should return the original string. You can assume the string has only uppercaae and 
// lowercase letters (a-z).

String compress(String str) {
    /* Check final length and return input string if it would be longer. */
    int finalLength = countCompression(str);
    if (finalLength >= str.length()) return str;

    StringBuilder compressed = new StringBuilder(finalLength); // initial capacity 
    int countConsecutive = 0;
    for (int i = 0; i < str.length(); i++) {
        countConsecutive++;

        /* If next character is different than current, append this char to result. */
        if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
            compressed.append(str.charAt(i));
            compressed.append(countConsecutive);
            countConsecutive = 0;
        }
    }
    return compressed.toString();
}

int countCompression(String str) {
    int compressedLength = 0;
    int countConsecutive = 0;
    for (int i = 0; i < str.length(); i++) {
        countConsecutive++;

        /* If next character is different than current, increase the length. */
        if (i + 1 >= str.length() || str.charAt(i) != str.charAt(i + 1)) {
            compressedLength += 1 + String.valueOf(countConsecutive).length();
            countConsecutive = 0;
        }
    }
    return compressedLength;
}

// 1.7 Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the 
// image is represented by an integer, write a method to rotate the image by 90 degrees. 
boolean rotate(int[][] matrix) {
    if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
    int n = matrix.length;
    for (int layer = 0; layer < n / 2; layer++) {
        int first = layer;
        int last = n - 1 - layer;
        for(int i = first; i < last; i++) {
            int offset = 1 - first;
            int top = matrix[first][i]; // Save top

            // left -> top 
            matrix[first][i] = matrix[last-offset][first];

            // bottom -> left
            matrix[last-offset][first] = matrix[last][last - offset];

            // right -> bottom
            matrix[last][last - offset] = matrix[i][last];

            // top -> right
            matrix[i][last] = top; // right <- saved top
        }
    }
    return true;
}
// 1.8 Zero matrix: Write an algortithm such that if an element in an MxN matrix is 0,
// its entire row and column are set to 0.
void setZeros(int[][] matrix) {
    boolean[] row = new boolean[matrix.length];
    boolean[] column = new boolean[matrix[0].length];

    // Store the row and column index with value 0
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length;j++) {
            if (matrix[i][j] == 0) {
                row[i] = true;
                column[j] = true;
            }
        }
    }

    // Nullify rows
    for (int i = 0; i < row.length; i++) {
        if (row[1]) nullifyRow(matrix, i);
    }

    // Nullify columns
    for (int j = 0; j < column.length; j++) {
        if (column[j]) nullifyColumnn(matrix, j);
    }
}

void nullifyRow(int[][] matrix, int row) {
    for (int j = 0; j < matrix[0].length; j++) {
        matrix[row][j] = 0;
    }
}

void nullifyColumnn(int[][] matrix, int col) {
    for (int i = 0; i < matrix.length; i++) {
        matrix[i][col] = 0;
    }
} 

// 1.9 String Rotation: Assume you have a method isSubString which checks if one word is a substring
// of another. Given 2 strings, s1 and s2, write code to check if s2 is a rotation of s1 using only 
// 1 call to isSubstring (e.g.,"waterbottle"is a rotation of "erbottlewat").

boolean isRotation(String s1, String s2) {
    int len = s1.length();
    /* Check that s1 and s2 are equal length and not empty */
    if (len == s2.length() && len > 0) {
        /* Concatenate s1 and s1 within new buffer */
        String s1s1 = s1 + s1;
        return isSubString(s1s1, s2);
    }
    return false;
}