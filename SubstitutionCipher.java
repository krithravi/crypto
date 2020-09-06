import java.util.*;
import java.io.*;

public class SubstitutionCipher {
	public static void main(String args[]) throws FileNotFoundException {

		// Scanner object to get user input
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter 1 to encrypt and 2 to decrypt: ");
		int option = scan.nextInt();
		scan.nextLine();

		System.out.print("Enter the message: ");
		String mes = scan.nextLine();

		System.out.print("Enter the key: ");
		String key = scan.nextLine();

		switch (option) {
			case 1:
				System.out.println("Encrypted result: ");
				if (isNum(key))
					System.out.println(monoAlph(mes, Integer.parseInt(key), 1));
				else
					System.out.println(vigenere(mes, key, 1));
				break;

			case 2:
				if (key.length() == 0){
					// putting values in dictionary
					HashSet<String> dict = new HashSet<String>();

					// Scanner object to read in dictionary
					Scanner file = new Scanner(new File("american-english.txt"));

					while (file.hasNextLine()){
						dict.add(file.nextLine().toLowerCase());
					}

					unknownDecrypt(mes, dict);
				}
				else if (isNum(key))
					System.out.println(monoAlph(mes, Integer.parseInt(key), 0));
				else if (key.length() != 0)
					System.out.println(vigenere(mes, key, 0));
				break;
		}


	}

	public static char upChar(char c, int n, int o){
		int foo = c;
		// encryption (add n)
		if (o == 1){
			if (foo >= 'a' && foo <= 'z'){
				foo += n;
				while (foo > 'z'){
					foo -= 26;
				}
			}
			else if (foo >= 'A' && foo <= 'Z'){
				foo += n;
				while (foo > 'Z'){
					foo -= 26;
				}
			}

		}
		// decryption (subtract n)
		else if (o == 0){
			if (foo >= 'a' && foo <= 'z'){
				foo -= n;
				while (foo < 'a'){
					foo += 26;
				}
			}
			else if (foo >= 'A' && foo <= 'Z'){
				foo -= n;
				while (foo < 'A'){
					foo += 26;
				}
			}

		}

		return (char) foo;
	}

	// monoalphabetic substitution cipher

	public static String monoAlph(String mes, int n, int o){
		StringBuilder sb = new StringBuilder("");
		for (int i=0; i< mes.length(); i++){
			sb.append(upChar(mes.charAt(i), n, o));
		}
		return sb.toString();
	}



	// Vigenere substitution cipher
	public static String vigenere(String mes, String key, int o){
		StringBuilder sb = new StringBuilder("");
		key.toLowerCase();
		for (int i=0; i< mes.length(); i++){
			sb.append(upChar(mes.charAt(i), key.charAt(i % key.length()) - 'a' +1, o));
		}

		return sb.toString();
	}

	public static boolean isNum(String s){
		for (char c : s.toCharArray()){
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static void unknownDecrypt(String mes, HashSet<String> dict){
		// array to store all the counts
		ArrayList<ArrayList<String> > counts = new ArrayList<ArrayList<String> >();

		// nested arraylist contains entries of the format
		// <position, string, numWords>
		for (int i=1; i<= 25; i++){
			String str = monoAlph(mes, i, 1);
			ArrayList<String> foo = new ArrayList<>();

			foo.add(String.valueOf(i-1));
			foo.add(str);
			foo.add(String.valueOf(numWords(str, dict)));

			counts.add(foo);
		}

		// sort based on final column (numWords)
		Collections.sort(counts, new Comparator<ArrayList<String>>(){
			@Override
			public int compare(ArrayList<String> first, ArrayList<String> second) {
				return second.get(2).compareTo(first.get(2));
			}
		});

		System.out.println("\nWith a Caesar cipher, the most likely plaintext is: ");
		System.out.println(counts.get(0).get(1));
		System.out.println(  "with key -" + (25 - Integer.parseInt(counts.get(0).get(0)) )  );

		// printing the other results
		System.out.println("\nThe other results were: ");
		System.out.println("Key \t Plaintext \t\t\t # Words");

		for (int i=0; i< 16 + mes.length() + String.valueOf(counts.get(0).get(2)).length(); i++ ){
			System.out.print("-");
		}
		System.out.println();
		for (int i=0; i< counts.size(); i++){
			System.out.println("-"+ (25 - Integer.parseInt(counts.get(i).get(0)) )
							+ "\t" + counts.get(i).get(1)
							+"\t"+ counts.get(i).get(2));
		}
	}

	// in the dictionary, reads in words
	public static int numWords(String s, HashSet<String> dict){
		int counter = 0;

		String [] arr = s.split(" ");
		for (String i : arr){
			// case insensitive matching
			if (dict.contains(i.toLowerCase()))
				counter++;
		}

		return counter;
	}


}
