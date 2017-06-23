cmake_minimum_required(VERSION 3.6)
project(tp2_trie)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp Node.cpp Node.h)
add_executable(tp2_trie ${SOURCE_FILES})