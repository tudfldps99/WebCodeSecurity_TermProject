package myPackage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MyKeyPair implements Serializable {	//비대칭 키 생성 / 생성된 비대칭키를 파일에 저장하고 다시 복구

	private static final long serialVersionUID = 1L;

	// 비대칭키 생성
	private static final String keyAlgorithm = "RSA";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;				// KeyPair 안에 PublicKey, PrivateKey 존재
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public void createKeys() throws NoSuchAlgorithmException, NoSuchProviderException{		//키 만듦		
	      keyGen = KeyPairGenerator.getInstance(keyAlgorithm); 
	      pair = keyGen.generateKeyPair(); 
	      publicKey = pair.getPublic(); 
	      privateKey = pair.getPrivate();
	}
	
	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}
	
	public PublicKey getPublicKey() {
		return this.publicKey;
	}
	
	//생성된 비대칭키를 파일에 저장
	public void savePublicKey(PublicKey publicKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(publicKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void savePrivateKey(PrivateKey privateKey, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(privateKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//파일에 저장한 비대칭 키 복구
	PublicKey restorePublicKey(String filename) {
		PublicKey publicKey = null;
		try (FileInputStream fis = new FileInputStream(filename)) {
			try (ObjectInputStream ostream = new ObjectInputStream(fis)) {
				Object obj = ostream.readObject();
				publicKey = (PublicKey)obj;
			} catch (ClassCastException e) {
				System.out.println("Exception 발생");
			} catch (ClassNotFoundException e) {
				System.out.println("Exception 발생");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Exception 발생");
		} catch (IOException e1) {
			System.out.println("Exception 발생");
		} 
		return publicKey;
	}
	
	PrivateKey restorePrivateKey(String filename) {
		PrivateKey privateKey = null;

	    try(FileInputStream fis = new FileInputStream (filename)){
	       try(ObjectInputStream ois = new ObjectInputStream(fis)){
	          Object obj = ois.readObject();
	          privateKey = (PrivateKey)obj;
	       }
	    } catch (ClassCastException e) {
		   System.out.println("Exception 발생");
	    } catch (ClassNotFoundException e) {
	       System.out.println("Exception 발생");
	    } catch (FileNotFoundException e) {
		   System.out.println("Exception 발생");
	    } catch (IOException e) {
	       System.out.println("Exception 발생");
	    }
	    return privateKey;
	    
	}
	
	
	//전자서명 파일 저장
	public final void saveSign(byte[] data, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(data);
			}
		} catch (IOException e) {
			System.out.println("Exception 발생");
		}
	}
	
	//전자서명 파일 복구
	public final byte[] restoreSign(String filename) {
		byte[] data = null;
		try (FileInputStream fstream = new FileInputStream(filename)) {
			try (ObjectInputStream ostream = new ObjectInputStream(fstream)) {
				Object obj = ostream.readObject();
	            data = (byte[]) obj;
	         } catch (ClassCastException e) {
		 		System.out.println("Exception 발생");
	         } catch (ClassNotFoundException e) {
	 			System.out.println("Exception 발생");
	         }
	      } catch (IOException e) {
				System.out.println("Exception 발생");
	      }
	      return data;
	   }
	
	//plainText 파일 복구
	public final String restorePlain(String filename) throws IOException {
		String data = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			
			while(line != null) {
				sb.append(line);
				line = br.readLine();
			}
			
			data = sb.toString();
		}
		return data;
	}
}
