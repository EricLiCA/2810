//
// Created by Eric Li on 2017-04-02.
//

#ifndef TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_NODE_H
#define TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_NODE_H

#include <iostream>

enum State {
    start, edge, final
};

class Node {
private:
    char value_;
    Node* left_;
    Node* right_;
    Node* middle_;
    State state_;

public:
    Node(char character);
    char getValue();

    Node* getRight();

    Node* getLeft();

    Node* getMiddle();

    void addRight(Node* newCharacter);

    void addLeft(Node* newCharacter);

    void addMiddle(Node* newCharacter);

    void changeValue(char character);

    void print();

    void setState(State state);

    State getState();
};


#endif //TP2_H2017_AUTOMATES_ET_LANGAGES_20170323_NODE_H
