import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

public class Assignment1 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BlockchainCreator bc = new BlockchainCreator();
        int B = Integer.parseInt(in.readLine());
        String inputMerkle[] = new String[B];
        String merkleRoot = "";
        for (int i = 1; i <= B; i++) {
            int T = Integer.parseInt(in.readLine());
            if (T >= 2) {
                String transac[] = T % 2 == 0 ? new String[T] : new String[T + 1];
                for (int j = 0; j < T; j++) {
                    transac[j] = in.readLine();
                }
                if (T % 2 == 1) {
                    transac[transac.length - 1] = transac[transac.length - 2];
                }
                String temp = in.readLine();
                merkleRoot = bc.generateMerkleRoot(transac, transac.length);
                inputMerkle[i - 1] = temp.equals(merkleRoot) ? "Valid" : "Invalid";
            } else if (T == 1) {
                String transac = in.readLine();
                merkleRoot = bc.getMD5(transac);
                String temp = in.readLine();
                inputMerkle[i - 1] = temp.equals(merkleRoot) ? "Valid" : "Invalid";
            }
        }
        for (int i = 0; i < B; i++) {
            System.out.println(inputMerkle[i]);
        }
    }
}
