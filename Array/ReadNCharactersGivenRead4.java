package Array;

public class ReadNCharactersGivenRead4 {
    public static void main(String[] args) {
        ReadNCharactersGivenRead4 readNCharactersGivenRead4 = new ReadNCharactersGivenRead4();
        System.out
                .println("" + readNCharactersGivenRead4.read(new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' }, 6));
    }

    /*
     * 
     * Problem Statement: Read N Characters Given Read4
     * You are given a function read4(char[] buf4) that reads up to 4 characters
     * from a file into buf4 and returns the number of characters actually read
     * (could be less than 4 at the end of the file).
     * 
     * Implement a function read(char[] buf, int n) that reads at most n characters
     * into buf using only calls to read4.​
     * 
     * Returns the actual number of characters read (could be < n if file size < n).
     * 
     * Example
     * Suppose the file has "abcdefgh", and you call read(buf, 6).
     * 
     * 1st call to read4 reads "abcd" → put into buf[0..3]
     * 
     * 2nd call to read4 reads "efgh" → put "e" and "f" into buf[4,5] (stop after
     * n=6)
     * 
     * Function returns 6, buf = {'a','b','c','d','e','f'}
     * 
     * If file has "abc" and you call read(buf, 6),
     * 
     * 1st call to read4 reads "abc" only (buf4 size < 4), copy to buf, return 3.
     */

    public int read(char[] buf, int n) {
        int elementCount = 0;
        while (elementCount < n) {
            
        }
        return 0;
    }
}
