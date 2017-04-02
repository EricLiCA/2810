//
// Created by Eric Li on 2017-04-02.
//

#include "Node.h"

Node::Node(char character) {
    value_ = character;
    left_ = nullptr;
    right_ = nullptr;
    middle_ = nullptr;
    state_ = edge;
}

char Node::getValue() {
    return value_;
}

Node* Node::getRight() {
    return right_;
}

Node* Node::getLeft() {
    return left_;
}

Node* Node::getMiddle() {
    return middle_;
}

void Node::addRight(Node* newCharacter) {
    right_ = newCharacter;
}

void Node::addLeft(Node* newCharacter) {
    left_ = newCharacter;
}

void Node::addMiddle(Node* newCharacter) {
    middle_ = newCharacter;
}

void Node::changeValue(char character) {
    value_ = character;
}

void Node::print() {
    std::cout << value_;
}

void Node::setState(State state) {
    state_ = state;
}

State Node::getState() {
    return state_;
}