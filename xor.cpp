#include<iostream>
#include<cmath>

using namespace std;

void initialize_tochar(char arr[], int totlen, string message);
void initialize_toint(int intarr[], char arr[], int totlen);
void binarr(int miniarr[], int n);
void xorer(int output[], int a[], int b[]);
int bintodec(int binarr[]);

int main(){

	// Get the 2 strings
	string a,b;

	cout << "String 1: ";
	getline (cin, a);

	cout << "String 2: ";
	getline (cin, b);

	// size of the bigger of the 2 strings 
	int totlen = 0;

	int lena = a.length(); // length of string A
	int lenb = b.length(); // length of string B

	if (lena >= lenb){
		totlen = lena;
	}
	else {
		totlen = lenb;
	}

	// put both strings into char arrays
	char achar[totlen];
	char bchar[totlen];

	initialize_tochar(achar, totlen, a);
	initialize_tochar(bchar, totlen, b);

	// put everything in an int array
	int aint[totlen] = {0};
	int bint[totlen] = {0};

	initialize_toint(aint, achar, totlen);
	initialize_toint(bint, bchar, totlen);
	
	// making an array of arrays
	// one dimension is totlen and the other is 7
	
	int bina[totlen][7];
	int binb[totlen][7];

	// yeehaw let's put some numbers in :)
	for (int i=0; i< totlen; i++){
		binarr(bina[i], aint[i]);
	}

	for (int i=0; i< totlen; i++){
		binarr(binb[i], bint[i]);
	}
	
	// we need a place to store the results of the xor
	// array of arrays once more, size totlen and 7

	int intxor[totlen][7];

	// initializing the array
	for (int i=0; i< totlen; i++){
		xorer(intxor[i], bina[i], binb[i]);
	}

	// converting from binary to decimal
	// putting that into an array
	
	int intvals[totlen];

	for (int i=0; i < totlen; i++){
		intvals[i] = bintodec(intxor[i]);
	}

	// printing out integer array
	
	cout << endl;
	cout << "XOR results: " << endl;
	for (int i=0; i< totlen; i++){
		cout << intvals[i] << " ";
	}

	cout << endl;

	return 0;
}

void initialize_tochar(char arr[], int totlen, string message){
	// let everything be the null char for now 
	for (int i=0; i < totlen; i++){
		arr[i] = '\0';
	}

	// work backwards to initialize array 
	for (int i=message.length()-1, j=totlen-1; i >=0; i--, j--){
		arr[j] = message[i];
	}

}

void initialize_toint(int intarr[], char arr[], int totlen){
	// adding null character because addition ensures that it
	// gets stored as an int and not a char 
	for (int i=0; i<totlen; i++){
		intarr[i] = arr[i] + '\0';
	}
}

void binarr(int miniarr[], int n){

	int reverseminiarr[7]={0};
	int i=0;

	while (n > 0){
		reverseminiarr[i] = n  % 2;
		n /= 2;
		i++;
	}
	int size=7;	
	for (int j=0; j < size; j++){
		miniarr[j]=reverseminiarr[size-j-1];
	}

}

void xorer(int output[], int a[], int b[]){
	for (int i=0; i<7; i++){
		if (a[i]!=b[i]){
			output[i]=1;
		}
		else {
			output[i]=0;
		}
	}
}

int bintodec(int binarr[]){
	int sum=0;
	
	for (int i=0; i < 7; i++){
		sum += (binarr[i] * pow(2, 6-i));
	}

	return sum;
}
