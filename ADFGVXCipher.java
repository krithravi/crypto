import java.util.*;

public class ADFGVXCipher {
	public static void main(String args[]){

		// get input!

		// make scanner object for input
		Scanner scan = new Scanner(System.in);

		// the message to encrypt
		System.out.print("Enter message to encrypt: ");
		String mes = scan.nextLine();

		// keyphrase, used to make polybius square
		System.out.print("Enter keyphrase for Polybius square: ");
		String keyphrase = scan.nextLine();

		while (!validKeyphrase(keyphrase) ){
			System.out.print("Enter valid keyphrase: ");
			keyphrase = scan.nextLine();
		}

		// keyword for transposition
		System.out.print("Enter keyword for transposition: ");
		String keyword = scan.nextLine();

		while (keyword.length() <= 1 && keyword.length() > mes.length()){
			System.out.println("Keyword length must match message length.");
			System.out.print("Enter keyword: ");
			keyword = scan.nextLine();
		}


		// take keyphrase and chuck into polybius square
		char [][] square = new char[6][6];

		// putting keyphrase into a character array
		char[] letters = keyphrase.toCharArray();

		// filling the polybius square
		int counter = -1;
		for (int i = 0; i< 6; i++){
			for (int j=0; j< 6; j++){
				counter++;
				square[i][j] = letters[counter];
			}
		}

		// after the substitution
		String substitution = substitute(square, mes);

		// dimensions of transposition array
		int transY = keyword.length();
		int transX = substitution.length()/keyword.length()+1;

		char [][] newSquare = new char[transX][transY];

		// fills in the transposition square
		counter = -1;
		for (int i=0; i< transX; i++){
			for (int j=0; j< transY; j++){
				counter++;
				if (counter < substitution.length())
					newSquare[i][j] = substitution.charAt(counter);
			}
		}

		// the keyword as a character array
		char [] keyArr = keyword.toCharArray();

		// switching columns based on a bubble sort

		boolean repeat = true;

		while (repeat){
			repeat = false;

			for (int i=0; i<keyArr.length-1; i++){
				if (keyArr[i+1] < keyArr[i]){
					repeat = true;

					//dealing with the keyArr
					char temp = keyArr[i+1];
					keyArr[i+1] = keyArr[i];
					keyArr[i] = temp;

					// dealing with the newSquare array
					for (int n = 0; n < transY -1 ; n++){
						temp = newSquare[n][i+1];
						newSquare[n][i+1] = newSquare[n][i];
						newSquare[n][i] = newSquare[n][i];
						newSquare[n][i] = temp;
					}
				}
			}
		}


		String result = "";
		StringBuilder sb = new StringBuilder(result);

		for (int i=0; i< transX; i++){
			for (int j=0; j< transY; j++){
				if (newSquare[i][j] != '\0')
					sb.append(newSquare[i][j]);

			}
		}

		for (int i=0; i< sb.toString().length(); i++){
			System.out.print(sb.toString().charAt(i));
			if (i %2 == 1){
				System.out.print(" ");
			}
		}
		System.out.println();



	}

	// must contain exactly 36 characters
	// must contain all unique characters
	// must contain a-z/A-Z and 0-9

	public static boolean validKeyphrase(String s){
		if (s.length() != 36){
			return false;
		}
		String S = s.toLowerCase();

		Set<Character> foo = new HashSet<>();
		for (int i=0; i< S.length(); i++){
			foo.add(S.charAt(i));
		}

		if (foo.size() != S.length()){
			return false;
		}

		for (int i='a'; i<='z'; i++){
			if (foo.remove((char) i)){}
			else
				return false;
		}

		for (int i='0'; i<='9'; i++){
			if (foo.remove((char) i)){}
			else
				return false;
		}

		if (!foo.isEmpty())
			return false;

		return true;
	}

	public static String substitute(char[][] arr, String s){
		String result = "";
		final char[] cipher = {'A', 'D', 'F', 'G', 'V', 'X'};

		for (int k = 0; k < s.length(); k++){
			arrLoop: {
				for (int i=0; i< 6; i++){
					for (int j=0; j< 6; j++){
						if (s.charAt(k) == arr[i][j] ){
							result += cipher[i];
							result += cipher[j];
							break arrLoop;
						}
					}
				}
			}
		}

		return result;
	}
}
