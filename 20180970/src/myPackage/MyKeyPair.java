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

public class MyKeyPair implements Serializable {	//���Ī Ű ���� / ������ ���ĪŰ�� ���Ͽ� �����ϰ� �ٽ� ����

	private static final long serialVersionUID = 1L;

	// ���ĪŰ ����
	private static final String keyAlgorithm = "RSA";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;				// KeyPair �ȿ� PublicKey, PrivateKey ����
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public void createKeys() throws NoSuchAlgorithmException, NoSuchProviderException{		//Ű ����		
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
	
	//������ ���ĪŰ�� ���Ͽ� ����
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
	
	//���Ͽ� ������ ���Ī Ű ����
	PublicKey restorePublicKey(String filename) {
		PublicKey publicKey = null;
		try (FileInputStream fis = new FileInputStream(filename)) {
			try (ObjectInputStream ostream = new ObjectInputStream(fis)) {
				Object obj = ostream.readObject();
				publicKey = (PublicKey)obj;
			} catch (ClassCastException e) {
				System.out.println("Exception �߻�");
			} catch (ClassNotFoundException e) {
				System.out.println("Exception �߻�");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Exception �߻�");
		} catch (IOException e1) {
			System.out.println("Exception �߻�");
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
		   System.out.println("Exception �߻�");
	    } catch (ClassNotFoundException e) {
	       System.out.println("Exception �߻�");
	    } catch (FileNotFoundException e) {
		   System.out.println("Exception �߻�");
	    } catch (IOException e) {
	       System.out.println("Exception �߻�");
	    }
	    return privateKey;
	    
	}
	
	
	//���ڼ��� ���� ����
	public final void saveSign(byte[] data, String filename) {
		try (FileOutputStream fstream = new FileOutputStream(filename)) {
			try (ObjectOutputStream ostream = new ObjectOutputStream(fstream)) {
				ostream.writeObject(data);
			}
		} catch (IOException e) {
			System.out.println("Exception �߻�");
		}
	}
	
	//���ڼ��� ���� ����
	public final byte[] restoreSign(String filename) {
		byte[] data = null;
		try (FileInputStream fstream = new FileInputStream(filename)) {
			try (ObjectInputStream ostream = new ObjectInputStream(fstream)) {
				Object obj = ostream.readObject();
	            data = (byte[]) obj;
	         } catch (ClassCastException e) {
		 		System.out.println("Exception �߻�");
	         } catch (ClassNotFoundException e) {
	 			System.out.println("Exception �߻�");
	         }
	      } catch (IOException e) {
				System.out.println("Exception �߻�");
	      }
	      return data;
	   }
	
	//plainText ���� ����
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
