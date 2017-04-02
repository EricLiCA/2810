//
// Created by Eric Li on 2017-04-02.
//

#ifndef TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_TREE_H
#define TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_TREE_H

#include "Node.h"
#include <vector>
#include <fstream>

class Tree {
private:
    Node* root_;
    Node* suggestionNode_;
    char suggestion_;
    std::string typedCharacters_;
    std::vector<std::string> mots_;

public:
    Tree(std::string lexiqueName);

    void addWord(std::string wordToAdd);
    void addLexique(int low, int high);

    void autocomplete();

    void findSuggestion(char letter);
};


#endif //TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_TREE_H
