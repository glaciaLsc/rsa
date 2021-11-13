import java.util.Scanner;
import java.util.Random;
import java.math.BigInteger;

public class RSA
{
	Scanner s = new Scanner(System.in);
	Random r;
	int bitlength, e_int;
	BigInteger n, e, d; // Values for private & public key

	public RSA()
	{
		BigInteger[] cyphertext;
		
		/* Obtain bit length and e values from user */
		System.out.print("Enter desired encryption bit length: ");
		bitlength = s.nextInt();
		System.out.print("Enter desired e value: ");
		e_int = s.nextInt();
		s.nextLine();
		
		/* Execute key generation, encryption, and decryption methods */
		genkey();
		cyphertext = encrypt();
		decrypt(cyphertext);
	}	

	public void genkey()
	{
		BigInteger one, p, q, phi;
		one = new BigInteger("1"); // For while comparisons
		e = new BigInteger(Integer.toString(e_int)); // Convert e to BigInteger

		r = new Random();
		p = new BigInteger(bitlength/2, 100, r);
		/* If p mod e equals one, generate new p value */
		while (p.mod(e) == one)
		{
			r = new Random();
			p = new BigInteger(bitlength/2, 100, r);
		}

		r = new Random();
		q = new BigInteger(bitlength/2, 100, r);
		/* If q mod e equals one, generate new q value */
		while (q.mod(e) == one)
		{
			r = new Random();
			q = new BigInteger(bitlength/2, 100, r);
		}
		
		/* Obtain n, phi, d values */
		n = p.multiply(q);
		phi = (q.subtract(q.valueOf(1))).multiply(p.subtract(p.valueOf(1)));
		d = e.modInverse(phi);
	}

	public BigInteger[] encrypt()
	{
		String text;
		int[] textbytes;
		BigInteger[] cyphertext;
		
		/* Get plain text from user */
		System.out.print("Enter message to encrypt: ");
		text = s.nextLine();
		System.out.println();
		
		/* Initialize textbyte and cyphertext arrays */
		textbytes = new int[text.length()];
		cyphertext = new BigInteger[text.length()];
		
		for (int i=0; i < text.length(); i++)
			textbytes[i] = (int)text.charAt(i);
		
		/* Apply RSA encryption formula using public key (n, e) */
		for (int i=0; i < text.length(); i++)
		{
			cyphertext[i] = new BigInteger(Integer.toString(textbytes[i]));
			cyphertext[i] = cyphertext[i].modPow(e, n);
		}

		/* Print encrypted cyphertext */
		System.out.print("Encrypted cyphertext: ");
		for (int i=0; i < text.length(); i++)
		{
			System.out.print(cyphertext[i]);
			System.out.print(" ");
		}
		System.out.println();
		System.out.println();
		
		return cyphertext;
	}

	public void decrypt(BigInteger[] cyphertext)
	{
		BigInteger[] textbytes = new BigInteger[cyphertext.length];
		
		/* Apply RSA decryption formula using private key (n, d) */
		for (int i=0; i < cyphertext.length; i++)
			textbytes[i] = cyphertext[i].modPow(d, n);
		
		/* Print decrypted message */
		System.out.print("Decrypted message: ");
		for (int i=0; i < cyphertext.length; i++)
			System.out.print((char) textbytes[i].intValue());
	}

	public static void main(String[] args)
	{
		new RSA();
	}

}
