import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Assignment1 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int B = Integer.parseInt(in.readLine());
        String inputMerkle[] = new String[B];
        for (int i = 1; i <= B; i++) {
            int T = Integer.parseInt(in.readLine());
            String transac[] = T % 2 == 0 ? new String[T] : new String[T + 1];
            for (int j = 0; j < T; j++) {
                transac[j] = in.readLine();
            }
            if (T % 2 == 1) {
                transac[transac.length - 1] = transac[transac.length - 2];
            }
            String temp = in.readLine();
            String merkleRoot = generateMerkleRoot(transac, transac.length);
            inputMerkle[i - 1] = temp.equals(merkleRoot) ? "Valid" : "Invalid";
        }
        for (int i = 0; i < B; i++) {
            System.out.println(inputMerkle[i]);
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
        } else if (size >= 2 && size <= 4) {
            effective_case = "E";
        }
        switch (effective_case) {
            case "A":
                merkleRoot = fiftyLeafNode(transaction, size, "TRANSACT");
                break;
            case "B":
                merkleRoot = twentysixLeafNode(transaction, size, "TRANSACT");
                break;
            case "C":
                merkleRoot = fourteenLeafNode(transaction, size, "TRANSACT");
                break;
            case "D":
                merkleRoot = eightLeafNode(transaction, size, "TRANSACT");
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

    public static String fiftyLeafNode(String transaction[], int size, String type)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String[] hash_level_7 = new String[50];
        String[] hash_level_6 = new String[26];
        int lb = 0;
        int ub = 1;
        int lastindex = countElements(transaction);
        if (lastindex < size) {
            if (lastindex % 2 == 1) {
                transaction[lastindex] = transaction[lastindex - 1];
            }
        }
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_7[i] = getMD5(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_7[i] = transaction[i];
            }
        }
        for (int i = 0; i < hash_level_6.length - 1; i++) {
            if (!(hash_level_7[lb] == null && hash_level_7[ub] == null)) {
                hash_level_6[i] = getMD5(hash_level_7[lb] + hash_level_7[ub]);
                lb += 2;
                ub += 2;
            } else {
                break;
            }
        }
        return (twentysixLeafNode(hash_level_6, hash_level_6.length, "HASH"));
    }

    public static String twentysixLeafNode(String transaction[], int size, String type)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] hash_level_6 = new String[26];
        String[] hash_level_5 = new String[14];
        int lb = 0;
        int ub = 1;
        int lastindex = countElements(transaction);
        if (lastindex < size) {
            if (lastindex % 2 == 1) {
                transaction[lastindex] = transaction[lastindex - 1];
            }
        }
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_6[i] = getMD5(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_6[i] = transaction[i];
            }
        }
        for (int i = 0; i < hash_level_5.length - 1; i++) {
            if (!(hash_level_6[lb] == null && hash_level_6[ub] == null)) {
                hash_level_5[i] = getMD5(hash_level_6[lb] + hash_level_6[ub]);
                lb += 2;
                ub += 2;
            }
        }
        return (fourteenLeafNode(hash_level_5, hash_level_5.length, "HASH"));
    }

    public static String fourteenLeafNode(String transaction[], int size, String type)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] hash_level_5 = new String[14];
        String[] hash_level_4 = new String[8];
        int lb = 0;
        int ub = 1;
        int lastindex = countElements(transaction);
        if (lastindex < size) {
            if (lastindex % 2 == 1) {
                transaction[lastindex] = transaction[lastindex - 1];
            }
        }
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_5[i] = getMD5(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_5[i] = transaction[i];
            }
        }
        for (int i = 0; i < hash_level_4.length - 1; i++) {
            if (!(hash_level_5[lb] == null && hash_level_5[ub] == null)) {
                hash_level_4[i] = getMD5(hash_level_5[lb] + hash_level_5[ub]);
                lb += 2;
                ub += 2;
            }
        }
        return (eightLeafNode(hash_level_4, hash_level_4.length, "HASH"));
    }

    public static String eightLeafNode(String transaction[], int size, String type)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] hash_level_4 = new String[8];
        String[] hash_level_3 = new String[4];
        int lb = 0;
        int ub = 1;
        int lastindex = countElements(transaction);
        if (lastindex < size) {
            if (lastindex % 2 == 1) {
                transaction[lastindex] = transaction[lastindex - 1];
            }
        }
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_4[i] = getMD5(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_4[i] = transaction[i];
            }
        }
        for (int i = 0; i < 4; i++) {
            if (!(hash_level_4[lb] == null && hash_level_4[ub] == null)) {
                hash_level_3[i] = getMD5(hash_level_4[lb] + hash_level_4[ub]);
                lb += 2;
                ub += 2;
            }
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
        int lastindex = countElements(transaction);
        if (lastindex < size) {
            if (lastindex % 2 == 1) {
                transaction[lastindex] = transaction[lastindex - 1];
            }
        }
        if (type.equals("TRANSACT")) {
            for (int i = 0; i < size; i++) {
                hash_level_3[i] = getMD5(transaction[i]);
            }
        } else if (type.equals("HASH")) {
            for (int i = 0; i < size; i++) {
                hash_level_3[i] = transaction[i];
            }
        }
        for (int i = 0; i < hash_level_2.length; i++) {
            if (!(hash_level_3[lb] == null && hash_level_3[ub] == null)) {
                hash_level_2[i] = getMD5(hash_level_3[lb] + hash_level_3[ub]);
                lb += 2;
                ub += 2;
            }
        }
        if (hash_level_2[1] == null) {
            hash_level_1[0] = hash_level_2[0];
        } else {
            hash_level_1[0] = getMD5(hash_level_2[0] + hash_level_2[1]);
        }
        return hash_level_1[0];
    }

    public static String getMD5(String str)
            throws NoSuchAlgorithmException, RuntimeException, UnsupportedEncodingException {
        byte[] bytesOfMessage = str.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(bytesOfMessage);

        final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[theMD5digest.length * 2];
        for (int j = 0; j < theMD5digest.length; j++) {
            int v = theMD5digest[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static int countElements(String arr[]) {
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                pos = i;
                break;
            }
        }
        return (pos == 0 ? arr.length : pos);
    }
}
