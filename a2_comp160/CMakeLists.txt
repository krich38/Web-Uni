cmake_minimum_required(VERSION 2.8.4)
project(a2_comp160)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES
    css/print.css
    images/header.jpg
    images/main.jpg
    images/menu.png
    AssignDetails.html
    Equip.html
    index.html
    Rego.html
    style.css)

add_executable(a2_comp160 ${SOURCE_FILES})