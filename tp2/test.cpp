#include <iostream>

using namespace std;

int main() {
  string currentWord;
  for(int i=0; i<10; i++){
    string letter;
    cin >> letter;
    currentWord.append(letter);
    cout << "\r" + currentWord;
    cout << "\r\b\b\b\b\b\b\b\b\b\b\b\b" + to_string(i);

  }



}
