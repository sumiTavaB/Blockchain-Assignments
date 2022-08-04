import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleGeneration {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the no. of blocks:");
        int B = Integer.parseInt(in.readLine());
        for (int i = 1; i <= B; i++) {
            System.out.println("Enter the no. of transaction in Block " + i + ": ");
            int T = Integer.parseInt(in.readLine());
            System.out.println("Enter " + T + " transactions of fixed length:");
            String transac[] = T % 2 == 0 ? new String[T] : new String[T + 1];
            for (int j = 0; j < T; j++) {
                transac[j] = in.readLine();
            }
            if (T % 2 == 1) {
                transac[transac.length - 1] = transac[transac.length - 2];
            }
            for (int j = 0; j < transac.length; j++) {
                // System.out.println(" --> " + getMD5(transac[j]));
                System.out.println(" --> " + transac[j]);
            }
        }
    }

    public static String generateMerkleRoot(String transaction[], int size) throws NoSuchAlgorithmException {
        String[] hash_level_7 = new String[50];
        String[] hash_level_6 = new String[26];
        String[] hash_level_5 = new String[14];
        String[] hash_level_4 = new String[8];
        String[] hash_level_3 = new String[4];
        String[] hash_level_2 = new String[2];
        String[] hash_level_1 = new String[1];
        String effective_case = "";
        if (size > 26 && size <= 50) {
            effective_case = "A";
        } else if (size > 14 && size <= 26) {
            effective_case = "B";
        } else if (size > 8 && size <= 14) {
            effective_case = "C";
        } else if (size > 4 && size <= 8) {
            effective_case = "D";
        } else if (size > 2 && size <= 4) {
            effective_case = "E";
        }
        switch (effective_case) {
            case "A":
                int lb = 0, ub = 1, count = 0;
                for (int i = 0; i < size; i++) {
                    hash_level_7[i] = getMD5(transaction[i]);
                }
                for (int i = 0; i < size; i += 2) {
                    if (!hash_level_7[i].equals("") || !hash_level_7.equals(null)) {
                        hash_level_6[i] = getMD5(hash_level_7[lb] + hash_level_7[ub]);
                        lb += 2;
                        ub += 2;
                        ++count;
                    }
                }

        }
        return null;
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException, RuntimeException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(str.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}