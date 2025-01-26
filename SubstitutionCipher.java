/**
 * Jackson Douma
 * January 26, 2025
 * COMP-4476 Assignment 1 Problem 1
 * Substitution Cipher Program
 */

import java.util.*;

public class SubstitutionCipher 
{
    private static String Z29 = "abcdefghijklmnopqrstuvwxyz ,.";
    private static int Z29_SIZE = 29;

    /**
     * this method will make random key
     * @return key
     */
    public static int[] generateRandomKey() 
    {
        List<Integer> keyList = new ArrayList<>();
        int[] key = new int[Z29_SIZE];

        // randomizing in list since it's easier, and changing to array after
        for (int i = 0; i < Z29_SIZE; i++) 
        {
            keyList.add(i);
        }

        Collections.shuffle(keyList);

        for (int i = 0; i < Z29_SIZE; i++)
        {
            key[i] = keyList.get(i);
        }

        return key;
    }

    /**
     * this method encrypts plaintext using the key
     * @param plaintext
     * @param key
     * @return ciphertext
     */
    public static String encrypt(String plaintext, int[] key) 
    {
        StringBuilder ciphertext = new StringBuilder();

        for (char c : plaintext.toLowerCase().toCharArray()) 
        {
            int index = Z29.indexOf(c);

            // if character is not in Z29
            if (index == -1) 
            {
                throw new IllegalArgumentException("Invalid character in plaintext: " + c);
            }

            ciphertext.append(Z29.charAt(key[index]));
        }

        return ciphertext.toString();
    }

    /**
     * this method decrypts ciphertext using the key
     * @param ciphertext
     * @param key
     * @return plaintext
     */
    public static String decrypt(String ciphertext, int[] key) 
    {
        StringBuilder plaintext = new StringBuilder();
        int[] inverseKey = new int[Z29_SIZE];

        // make inverse of key
        for (int i = 0; i < Z29_SIZE; i++) 
        {
            inverseKey[key[i]] = i;
        }

        for (char c : ciphertext.toLowerCase().toCharArray()) 
        {
            int index = Z29.indexOf(c);

            // if character is not in Z29
            if (index == -1) 
            {
                throw new IllegalArgumentException("Invalid character in ciphertext: " + c);
            }

            plaintext.append(Z29.charAt(inverseKey[index]));
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

        // get random key
        int[] key = generateRandomKey();

        // get user input
        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();

        System.out.println("Key: " + Arrays.toString(key));

        // encrypt plaintext
        String ciphertext = encrypt(plaintext, key);
        System.out.println("Ciphertext: " + ciphertext);

        // decrypt ciphertext
        String decryptedText = decrypt(ciphertext, key);
        System.out.println("Decrypted text: " + decryptedText);

        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();

        // check results
        if (plaintext.equalsIgnoreCase(decryptedText)) 
        {
            System.out.println("Encryption and decryption correct!");
        } 
        else 
        {
            System.out.println("ERROR: Encryptionor decryption incorrect.");
        }

        scanner.close();

        System.out.println();
        System.out.println("----------------------------------------------");
    }
}
