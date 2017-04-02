cmake_minimum_required(VERSION 3.6)
project(TP2_H2017_Automates_et_langages_20170323)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        Données/lexique1.txt
        Données/lexique2.txt
        Données/lexique3.txt
        Données/lexique4.txt
        Données/lexique5.txt
        Données/lexique6.txt
        Données/readLexique.txt
        TP2-LOG2810-H2017.pdf Node.cpp Node.h Tree.cpp Tree.h main.cpp)

add_executable(TP2_H2017_Automates_et_langages_20170323 ${SOURCE_FILES})