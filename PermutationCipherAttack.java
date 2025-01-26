/**
 * Jackson Douma
 * January 26, 2025
 * COMP-4476 Assignment 1 Problem 3
 * Permutation Cipher Known-Plaintext Attack Program
 */

import java.util.*;

public class PermutationCipherAttack 
{
    private static final String Z29 = "abcdefghijklmnopqrstuvwxyz ,.";
    private static final int Z29_SIZE = 29;

    /**
     * This method does a known-plaintext attack to get block size and key
     * @param plaintext 
     * @param ciphertext
     * @return map of block size and key
     */
    public static Map<String, Object> attack(String plaintext, String ciphertext) 
    {
        Map<String, Object> result = new HashMap<>();

        // try all possible block sizes from 1 to ciphertext length
        for (int m = 1; m <= ciphertext.length(); m++) 
        {
            // block size must divide the ciphertext and plaintext lengths
            if (ciphertext.length() % m != 0 || plaintext.length() % m != 0) 
            {
                continue;
            }

            // divide plaintext and ciphertext into blocks of size m
            List<String> plaintextBlocks = divideIntoBlocks(plaintext, m);
            List<String> ciphertextBlocks = divideIntoBlocks(ciphertext, m);

            boolean check = true;
            int[] key = new int[m];

            // try to find a working map for all blocks
            for (int i = 0; i < plaintextBlocks.size(); i++) 
            {
                // get plain and cipher text block
                String pBlock = plaintextBlocks.get(i);
                String cBlock = ciphertextBlocks.get(i);

                // determine key mapping for block
                int[] currentKey = findKeyPermutation(pBlock, cBlock);

                // no key is found
                if (currentKey == null) 
                {
                    check = false;
                    break;
                }

                if (i == 0) 
                {
                    key = currentKey;
                } 
                else if (!Arrays.equals(key, currentKey)) 
                {
                    check = false;
                    break;
                }
            }

            // if key is found
            if (check) 
            {
                result.put("blockSize", m);
                result.put("key", key);
                return result;
            }
        }

        // no block size or key found
        result.put("blockSize", -1);
        result.put("key", null);
        return result;
    }

    /**
     * this method divides a string into blocks of size m
     * @param text
     * @param m
     * @return blocks.
     */
    private static List<String> divideIntoBlocks(String text, int m) 
    {
        List<String> blocks = new ArrayList<>();

        for (int i = 0; i < text.length(); i += m) 
        {
            blocks.add(text.substring(i, Math.min(i + m, text.length())));
        }

        return blocks;
    }

    /**
     * this methods checks if key works from 1 block to another
     * @param plaintextBlock
     * @param ciphertextBlock
     * @return key
     */
    private static int[] findKeyPermutation(String plaintextBlock, String ciphertextBlock) 
    {
        int m = plaintextBlock.length();
        int[] key = new int[m];
        boolean[] used = new boolean[m];
    
        // check if block sizes are the same
        if (plaintextBlock.length() != ciphertextBlock.length()) 
        {
            return null;
        }
    
        // map characters from plain to cipher
        // if there is invalid mapping or dupe keys, return's null
        // examples with m = 3:
        // [0,1,1] is not valid
        // [0,1,4] is not valid
        // [1,0,2] is valid
        for (int i = 0; i < m; i++) 
        {
            boolean found = false;

            for (int j = 0; j < m; j++) 
            {
                if (plaintextBlock.charAt(i) == ciphertextBlock.charAt(j) && !used[j]) 
                {
                    key[j] = i;
                    used[j] = true;
                    found = true;
                    break;
                }
            }
    
            if (!found) 
            {
                return null;
            }
        }
    
        return key;
    }
    

    /**
     * runs on start
     * @param args
     */
    public static void main(String[] args) 
    {
        System.out.println("----------------------------------------------");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        // get user input
        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter ciphertext: ");
        String ciphertext = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();

        // ATTACK!
        Map<String, Object> result = attack(plaintext, ciphertext);

        int blockSize = (int) result.get("blockSize");
        int[] key = (int[]) result.get("key");

        if (blockSize != -1) 
        {
            System.out.println("Block Size: " + blockSize);
            System.out.println("Key: " + Arrays.toString(key));
        } 
        else 
        {
            System.out.println("ERROR: Failed to get block size or key.");
        }

        scanner.close();

        System.out.println();
        System.out.println("----------------------------------------------");
    }
}

