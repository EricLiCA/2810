cmake_minimum_required(VERSION 3.6)
project(TP2_H2017_Automates_et_langages_20170323)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        lexique1.txt
        main.cpp
        Node.cpp
        Node.h
        Tree.cpp
        Tree.h)

add_executable(TP2_H2017_Automates_et_langages_20170323 ${SOURCE_FILES})