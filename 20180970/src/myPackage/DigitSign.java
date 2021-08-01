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
	
	//주어진 데이터 파일에 대해서 전자서명을 생성
	public byte[] sign(String dataFilename, String keyFilename) throws NoSuchProviderException, IOException  {
		try {			
			myKeyPair.createKeys();
			
			//dataFilename에 저장된 plainText 읽어오기
        	String plainText = myKeyPair.restorePlain(dataFilename);
        	
			//keyFilename에 저장된 privatekey 읽어오기
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
	
	//데이터 파일에 대해서 전자서명을 검증
	public boolean verify(String dataFilename, String sigFilename, String keyFilename) throws FileNotFoundException, ClassNotFoundException, IOException {
        try {	
        	//dataFilename에 저장된 plainText 읽어오기
        	String plainText = myKeyPair.restorePlain(dataFilename);
        	
			//keyFilename에 저장된 publickey 읽어오기
			PublicKey pub = myKeyPair.restorePublicKey(keyFilename);
			
        	//sigFilename 파일에 저장된 전자서명 내용 불러오기			
			byte[] sign = myKeyPair.restoreSign(sigFilename);
        	
			Signature sig = Signature.getInstance(signAlgorithm);
			sig.initVerify(pub);
			sig.update(plainText.getBytes());
			boolean rslt = sig.verify(sign);

			return rslt;
        }  catch (NoSuchAlgorithmException e) {
        	System.out.println("Exception 발생");
        } catch (InvalidKeyException e) {
        	System.out.println("Exception 발생");
        } catch (SignatureException e) {
			System.out.println("Exception 발생");
        } 
        return false;
	}
}
