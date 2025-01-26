/**
 * Jackson Douma
 * January 26, 2025
 * COMP-4476 Assignment 1 Problem 2
 * Permutation Cipher Program
 */

import java.util.*;

public class PermutationCipher 
{
    private static String Z29 = "abcdefghijklmnopqrstuvwxyz ,.";
    private static int Z29_SIZE = 29;

    /**
     * this method encrypts plaintext using key and block size
     * @param plaintext
     * @param key
     * @param m
     * @return ciphertext
     */
    public static String encrypt(String plaintext, int[] key, int m) 
    {
        List<Integer> plaintextIndices = new ArrayList<>();
        StringBuilder ciphertext = new StringBuilder();

        for (char c : plaintext.toLowerCase().toCharArray()) 
        {
            int index = Z29.indexOf(c);

            if (index == -1) 
            {
                throw new IllegalArgumentException("Invalid character in plaintext: " + c);
            }

            plaintextIndices.add(index);
        }

        // Pad plaintext using .
        while (plaintextIndices.size() % m != 0) 
        {
            plaintextIndices.add(28);
        }

        // encrypt with the block size of m
        for (int i = 0; i < plaintextIndices.size(); i += m) 
        {
            int[] block = new int[m];

            for (int j = 0; j < m; j++) 
            {
                block[j] = plaintextIndices.get(i + j);
            }

            for (int j = 0; j < m; j++) 
            {
                ciphertext.append(Z29.charAt(block[key[j]]));
            }
        }

        return ciphertext.toString();
    }

    /**
     * this method decrypts ciphertext using key and blokc size
     * @param ciphertext
     * @param key
     * @param m
     * @return plaintext
     */
    public static String decrypt(String ciphertext, int[] key, int m) 
    {
        int[] inverseKey = new int[m];
        List<Integer> ciphertextIndices = new ArrayList<>();
        StringBuilder plaintext = new StringBuilder();

        // get inverse
        for (int i = 0; i < m; i++) 
        {
            inverseKey[key[i]] = i;
        }

        // change ciphertext to Z29
        for (char c : ciphertext.toLowerCase().toCharArray()) 
        {
            int index = Z29.indexOf(c);

            if (index == -1) 
            {
                throw new IllegalArgumentException("Invalid character in ciphertext: " + c);
            }

            ciphertextIndices.add(index);
        }

        // decrypt with block size of m
        for (int i = 0; i < ciphertextIndices.size(); i += m) 
        {
            int[] block = new int[m];

            for (int j = 0; j < m; j++) 
            {
                block[j] = ciphertextIndices.get(i + j);
            }

            for (int j = 0; j < m; j++) 
            {
                plaintext.append(Z29.charAt(block[inverseKey[j]]));
            }
        }

        // remove padding
        while (plaintext.length() > 0 && plaintext.charAt(plaintext.length() - 1) == '.') 
        {
            plaintext.deleteCharAt(plaintext.length() - 1);
        }

        return plaintext.toString();
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
        System.out.print("Enterm plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter block size: ");
        int m = scanner.nextInt();
        scanner.nextLine();

        if (m <= 0 || m > Z29_SIZE) 
        {
            System.out.println("Invalid block size. It must be between 1 and " + Z29_SIZE);
            scanner.close();
            return;
        }

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();

        // get random key
        List<Integer> keyList = new ArrayList<>();
        for (int i = 0; i < m; i++) 
        {
            keyList.add(i);
        }

        Collections.shuffle(keyList);
        int[] key = keyList.stream().mapToInt(i -> i).toArray();
        System.out.println("Key: " + Arrays.toString(key));

        // encrypt plaintext (also add dash after every block before visual purposes)
        String ciphertext = encrypt(plaintext, key, m);
        StringBuilder formattedCiphertext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) 
        {
            formattedCiphertext.append(ciphertext.charAt(i));

            if ((i + 1) % m == 0 && i != ciphertext.length() - 1) 
            {
                formattedCiphertext.append("-");
            }
        }

        System.out.println("Ciphertext: " + formattedCiphertext);


        // decrypt ciphertext
        String decryptedText = decrypt(ciphertext, key, m);
        System.out.println("Decrypted Text: " + decryptedText);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();

        // check results
        if (plaintext.equalsIgnoreCase(decryptedText)) 
        {
            System.out.println("Encryption and decryption are correct!");
        } 
        else 
        {
            System.out.println("ERROR: Encryption or decryption incorrect.");
        }

        scanner.close();

        System.out.println();
        System.out.println("----------------------------------------------");
    }
}

