#ifndef TP2_TRIE_NODE_H
#define TP2_TRIE_NODE_H

#include <unordered_map>
#include <string>
#include <vector>

enum State {
    start, edge, final
};

class Node {
private:
    std::unordered_map<char, Node*> children_;
    std::unordered_map<char, Node*> parents_;
    State state_;
    std::string word;

public:

};


#endif //TP2_TRIE_NODE_H
