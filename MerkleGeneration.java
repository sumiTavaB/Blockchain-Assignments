import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleGeneration {

    static String[] hash_level_7 = new String[50];
    static String[] hash_level_6 = new String[26];
    static String[] hash_level_5 = new String[14];
    static String[] hash_level_4 = new String[8];

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
            String merkleRoot = generateMerkleRoot(transac, transac.length);
            System.out.println("MERKLE ROOT --> " + merkleRoot);
        }
    }

    public static String generateMerkleRoot(String transaction[], int size)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String effective_case = "";
        String merkleRoot = "";
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
            case "D":
                merkleRoot = eightLeafNode(transaction, size);
                break;
            case "E":
                merkleRoot = fourLeafNode(transaction, size, "TRANSACT");
                break;
            case default:
                System.out.println("Unable to generate Merkle Root");
                break;
        }
        return merkleRoot;
    }

    public static String eightLeafNode(String transaction[], int size)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] hash_level_3 = new String[4];
        int lb = 0;
        int ub = 1;
        for (int i = 0; i < size; i++) {
            hash_level_4[i] = getSHA256(transaction[i]);
            System.out.println(transaction[i] + " --> " + hash_level_4[i]);
        }
        for (int i = 0; i < 4; i++) {
            hash_level_3[i] = getSHA256(hash_level_4[lb] + hash_level_4[ub]);
            System.out.println("(" + transaction[lb] + "+" + transaction[ub] + ") --> " + hash_level_3[i]);
            lb += 2;
            ub += 2;
        }
        return (fourLeafNode(hash_level_3, hash_level_3.length, "HASH"));
    }

    public static String fourLeafNode(String transaction[], int size, String type)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] hash_level_3 = new String[4];
        String[] hash_level_2 = new String[2];
        String[] hash_level_1 = new String[1];
        int lb = 0;
        int ub = 1;
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_3[i] = getSHA256(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_3[i] = transaction[i];
            }
        }
        for (int i = 0; i < 2; i++) {
            hash_level_2[i] = getSHA256(hash_level_3[lb] + hash_level_3[ub]);
            lb += 2;
            ub += 2;
        }
        hash_level_1[0] = getSHA256(hash_level_2[0] + hash_level_2[1]);
        return hash_level_1[0];
    }

    public static String getSHA256(String str)
            throws NoSuchAlgorithmException, RuntimeException, UnsupportedEncodingException {
        byte[] bytesOfMessage = str.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] theMD5digest = md.digest(bytesOfMessage);

        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[theMD5digest.length * 2];
        for (int j = 0; j < theMD5digest.length; j++) {
            int v = theMD5digest[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}