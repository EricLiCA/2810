#include "Tree.h"

Tree::Tree(std::string lexiqueName) {
    std::ifstream infile(lexiqueName);
    for( std::string line; getline( infile, line );) mots_.push_back(line);

    std::string rootWord = mots_[mots_.size()/2];
    Node* root = new Node(*rootWord.substr(0, 1).c_str());
    root_ = root;
    suggestionNode_ = root_;
    root_->setState(start);
    Node* currentNode = root_;
    addLexique(0, mots_.size() - 1);
    addWord("incandescentes");
    while (currentNode != nullptr) {
        currentNode->print();
        currentNode = currentNode->getMiddle();
    }
}

void Tree::addWord(std::string wordToAdd){
    Node* currentNode = root_;
    int matchCounter = 0;

    while (matchCounter < wordToAdd.size()) {
        char currentCharacter = *wordToAdd.substr(matchCounter, 1).c_str();

        if (currentNode->getValue() == currentCharacter) {
            matchCounter++;
            if (currentNode->getMiddle() == nullptr) {
                if (matchCounter < wordToAdd.size()) {
                    currentNode->addMiddle(new Node(*wordToAdd.substr(matchCounter, 1).c_str()));
                }
                else {
                    currentNode->setState(final);
                }
            }
            currentNode = currentNode->getMiddle();
        }
        else {
            if (*wordToAdd.substr(matchCounter, 1).c_str() < currentNode->getValue()) {
                if (currentNode->getLeft() == nullptr) {
                    currentNode->addLeft(new Node(*wordToAdd.substr(matchCounter, 1).c_str()));
                    if (matchCounter == 0) currentNode->getLeft()->setState(start);
                }
                currentNode = currentNode->getLeft();
            }
            else {
                if (currentNode->getRight() == nullptr) {
                    currentNode->addRight(new Node(*wordToAdd.substr(matchCounter, 1).c_str()));
                    if (matchCounter == 0) currentNode->getRight()->setState(start);
                }
                currentNode = currentNode->getRight();
            }
        }
    }
}

void Tree::addLexique(int low, int high) {
    int mid = (high - low) / 2 + low;
    if (low <= high) {
        std::string midWord = mots_[mid];
        addWord(midWord);
        addLexique(low, mid - 1);
        addLexique(mid + 1, high);
    }
}

void Tree::autocomplete() {
    /*std::string letter;
    std::cin >> letter;
    std::cout << "Entered while loop" << std::endl;
    letter = *letter.substr(0, 1).c_str();
    std::cout << letter << std::endl;
    while(true){
        //std::cin >> letter;
        letter = getchar();
        typedCharacters_.append(letter);
        std::cout << "\r" + typedCharacters_;
        findSuggestion(*letter.c_str());
        std::cout << suggestion_ << std::endl;
    }*/
    std::string letter;
    while (std::cin >> letter) {
        letter = *letter.substr(0, 1).c_str();
        typedCharacters_ += letter;
        suggestion_ = typedCharacters_;
        findSuggestion(*letter.c_str());
        std::cout << suggestion_ << std::endl;
    }
}

void Tree::findSuggestion(char letter) {
    /*bool found = false;
    suggestion_ = letter;
    while(!found){
        if (letter == suggestionNode_->getValue()){
            Node* tempSuggestion = suggestionNode_;
            while(suggestionNode_->getState() != final){
                suggestion_ += suggestionNode_->getMiddle()->getValue();
                suggestionNode_ = suggestionNode_->getMiddle();
            }
            found = true;
            suggestionNode_ = tempSuggestion;
        }

        else if(letter < suggestionNode_->getValue()){
            suggestionNode_ = suggestionNode_->getLeft();
        }
        else
        {
            suggestionNode_ = suggestionNode_->getRight();
        }
    }*/
    Node* tempSuggestion = suggestionNode_;
    bool found = false;
    while (!found) {
        if (letter == tempSuggestion->getValue()) {
            suggestionNode_ = tempSuggestion->getMiddle();
            while (tempSuggestion->getState() != final) {
                tempSuggestion = tempSuggestion->getMiddle();
                suggestion_ += tempSuggestion->getValue();
            }
            found = true;
        }
        else {
            if (letter < tempSuggestion->getValue()) {
                tempSuggestion = tempSuggestion->getLeft();
            }
            else {
                tempSuggestion = tempSuggestion->getRight();
            }
        }
    }
}