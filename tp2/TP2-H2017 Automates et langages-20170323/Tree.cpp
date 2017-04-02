//
// Created by Eric Li on 2017-04-02.
//

#include "Tree.h"

Tree::Tree(std::string lexiqueName) {
    std::ifstream infile(lexiqueName);
    for( std::string line; getline( infile, line ); )
    {
        //std::cout << line << std::endl;
        mots_.push_back(line);
    }
    std::string rootWord = mots_[mots_.size()/2];
    Node* root = new Node(*rootWord.substr(0, 1).c_str());
    root_ = root;
    Node* currentNode = root_;
    addLexique(0, mots_.size() - 1);
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
                    currentNode->setAsFinal();
                }
            }
            currentNode = currentNode->getMiddle();
        }
        else {
            if (*wordToAdd.substr(matchCounter, 1).c_str() < currentNode->getValue()) {
                if (currentNode->getLeft() == nullptr) {
                    currentNode->addLeft(new Node(*wordToAdd.substr(matchCounter, 1).c_str()));
                }
                currentNode = currentNode->getLeft();
            }
            else {
                if (currentNode->getRight() == nullptr) {
                    currentNode->addRight(new Node(*wordToAdd.substr(matchCounter, 1).c_str()));
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