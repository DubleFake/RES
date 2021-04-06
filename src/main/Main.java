package main;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static int p, q, n, z, d = 0, e, i;
    private static List<Integer> primeNumbers = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009));

    public static void prepareVariables() {
    	
    	while(true) {
        System.out.println("Enter 1st prime number p");
        p = sc.nextInt();
        if(primeNumbers.contains(p))
        	break;
        else
        	System.out.println("Your number is not prime or it's greater than 1009");
        }
    	
    	while(true) {
        System.out.println("Enter 2nd prime number q");
        q = sc.nextInt();
        if(primeNumbers.contains(q))
        	break;
        else
        	System.out.println("Your number is not prime or it's greater than 1009");
    	}
        n = p * q;
        z = (p - 1) * (q - 1);
        System.out.println("the value of z = " + z);

        for (e = 2; e < z; e++) {
            if (gcd(e, z) == 1) // e is for public key exponent
            {
                break;
            }
        }
        //e should be in the range 1-z
        System.out.println("the value of e = " + e);

        // calculate d
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * z);
            if (x % e == 0) //d is for private key exponent
            {
                d = x / e;
                break;
            }
        }
        System.out.println("the value of d = " + d);
    }

    private static int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    private static double encrypt(int msg) {
        //Encrypting  C = msg ^e mod n
        return (Math.pow(msg, e)) % n;
    }

   public static double[] encrypt(String msg) {
        int[] charactersAsNumbers = new int[msg.length()];
        for(int i = 0; i < msg.length(); i++) {
            charactersAsNumbers[i] = msg.codePointAt(i);
        }
        System.out.println("Plain text as sequence of numbers: " + Arrays.toString(charactersAsNumbers));

        double[] encryptedMsg = new double[msg.length()];
        for(int i = 0; i < charactersAsNumbers.length; i++) {
            encryptedMsg[i] = encrypt(charactersAsNumbers[i]);
        }
        return encryptedMsg;
    }

   private static BigInteger decrypt(double encrypted) {
        //converting int value of n to BigInteger
        BigInteger N = BigInteger.valueOf(n);
        //converting float value of c to BigInteger
        BigInteger C = BigDecimal.valueOf(encrypted).toBigInteger();

        //Decrypt , P = Cˆd mod N , msgback = P
        return (C.pow(d)).mod(N);
    }

    public static String decrypt(double[] encrypted) {
        StringBuilder builder = new StringBuilder();
        for(double encryptedCharacter: encrypted) {
            BigInteger decryptedCharacter = decrypt(encryptedCharacter);
            builder.append(Character.toChars(decryptedCharacter.intValue()));
        }
        return builder.toString();
    }

    public static void main(String args[]) {
        prepareVariables();
        System.out.println("Enter the text to be encrypted and decrypted");
        String msg = sc.nextLine();

        double[] c = encrypt(msg);
        System.out.println("Encrypted message is: " + Arrays.toString(c));

        String msgBack = decrypt(c);
        System.out.println("Decrypted message is: " + msgBack);
    }
}