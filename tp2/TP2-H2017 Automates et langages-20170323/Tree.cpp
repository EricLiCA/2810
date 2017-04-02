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
    for (int i = 1; i < rootWord.size(); i++) {
        currentNode->addMiddle(new Node(*rootWord.substr(i, 1).c_str()));
        currentNode = currentNode->getMiddle();
    }
    currentNode = root_;
    // To debug root word
    while (currentNode != nullptr) {
        currentNode->print();
        currentNode = currentNode->getMiddle();
    }
    std::cout << std::endl;
    std::cout << "123" << std::endl;
    addWord("illettreeer");
    addWord("audrey");
    std::cout << "123" << std::endl;
    currentNode = root_;
    //currentNode = currentNode->getMiddle();
    while (currentNode != nullptr) {
        currentNode->print();
        currentNode = currentNode->getMiddle();
    }
    std::cout << std::endl;
    currentNode = root_;
    currentNode = currentNode->getLeft();
    while (currentNode != nullptr) {
        currentNode->print();
        currentNode = currentNode->getMiddle();
    }
}

void Tree::addWord(std::string wordToAdd){
    Node* currentNode = root_;

    //std::cout << "Entered function" << std::endl;
    for (int i = 0; i < wordToAdd.size(); i++) {
       // std::cout << wordToAdd.size() << std::endl;
        std::cout << "Entered loop" << std::endl;
        bool added = false;
        bool found = false;
        char currentCharacter = *wordToAdd.substr(i, 1).c_str();
        if (currentNode->getMiddle() == nullptr) {
            currentNode->addMiddle(new Node(*wordToAdd.substr(i+1, 1).c_str()));
            added = true;
            currentNode = currentNode->getMiddle();
        }
        else if (currentCharacter == currentNode->getValue()) {
            //std::cout << "Entered equals" << std::endl;
            found = true;
            currentNode = currentNode->getMiddle();
        }
        else if (currentCharacter < currentNode->getValue()) {
            //std::cout << "Entered smaller" << std::endl;
            if (currentNode->getLeft() == nullptr) {
                currentNode->addLeft(new Node(*wordToAdd.substr(i, 1).c_str()));
                added = true;
            }
            currentNode = currentNode->getLeft();
        }
        else if (currentCharacter > currentNode->getValue()) {
            //std::cout << "Entered bigger" << std::endl;
            if (currentNode->getRight() == nullptr) {
                currentNode->addRight(new Node(*wordToAdd.substr(i, 1).c_str()));
                added = true;
            }
            currentNode = currentNode->getRight();
        }
        if (!added && !found) {
            //std::cout << "Entered decrementation" << std::endl;
            i--;
        } else {
            //std::cout << "Character printed: " << currentCharacter << std::endl;
            //std::cout << "Current i: " << i << std::endl;
        }
        std::cout << "NEXT ITERATION" << std::endl;
    }
}