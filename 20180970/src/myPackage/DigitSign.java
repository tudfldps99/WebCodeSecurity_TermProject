package myPackage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class DigitSign {	// sign / verify
	
	MyKeyPair myKeyPair = new MyKeyPair();

	final String keyAlgorithm = "RSA";
	final String signAlgorithm = "SHA1withRSA";
	
	//�־��� ������ ���Ͽ� ���ؼ� ���ڼ����� ����
	public byte[] sign(String dataFilename, String keyFilename) throws NoSuchProviderException, IOException  {
		try {			
			myKeyPair.createKeys();
			
			//dataFilename�� ����� plainText �о����
        	String plainText = myKeyPair.restorePlain(dataFilename);
        	
			//keyFilename�� ����� privatekey �о����
			PrivateKey priv = myKeyPair.restorePrivateKey(keyFilename);

			Signature privateSignature = Signature.getInstance(signAlgorithm);
			privateSignature.initSign(priv);
			privateSignature.update(plainText.getBytes());
			byte[] signature = privateSignature.sign();
			
			return signature;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
	}
	
	//������ ���Ͽ� ���ؼ� ���ڼ����� ����
	public boolean verify(String dataFilename, String sigFilename, String keyFilename) throws FileNotFoundException, ClassNotFoundException, IOException {
        try {	
        	//dataFilename�� ����� plainText �о����
        	String plainText = myKeyPair.restorePlain(dataFilename);
        	
			//keyFilename�� ����� publickey �о����
			PublicKey pub = myKeyPair.restorePublicKey(keyFilename);
			
        	//sigFilename ���Ͽ� ����� ���ڼ��� ���� �ҷ�����			
			byte[] sign = myKeyPair.restoreSign(sigFilename);
        	
			Signature sig = Signature.getInstance(signAlgorithm);
			sig.initVerify(pub);
			sig.update(plainText.getBytes());
			boolean rslt = sig.verify(sign);

			return rslt;
        }  catch (NoSuchAlgorithmException e) {
        	System.out.println("Exception �߻�");
        } catch (InvalidKeyException e) {
        	System.out.println("Exception �߻�");
        } catch (SignatureException e) {
			System.out.println("Exception �߻�");
        } 
        return false;
	}
}
