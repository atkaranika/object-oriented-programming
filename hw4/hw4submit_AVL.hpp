#pragma once
#ifndef _AVL_HPP
#define _AVL_HPP

#include <iostream>
#include <fstream>

using namespace std;

class AVL {
private:
    class Node {
        Node* parent, * left, * right;
        int height;
        string element;

    public:
        Node(const string& e, Node* parent, Node* left, Node* right);
        Node();
        Node* getParent() const {
            return parent;
        };
        Node* getLeft() const {
            return left;
        };
        Node* getRight() const {
            return right;
        };
        string getElement() const {
            return element;
        };
        int    getHeight() const {
            return height;
        };

        void setLeft(Node* left) {
            this->left = left;
        };
        void setRight(Node* right) {
            this->right = right;
        };
        void setParent(Node* parent) {
            this->parent = parent;
        };
        void setElement(string e) {
            element = e;
        };
        bool isLeft() const {
            if (parent == nullptr) {
                return false;
            }
            else if (parent->getLeft() != nullptr) {
               // cout << "parent " << parent->getElement()<<" "<< parent->getLeft()->getElement() << endl;
                if (parent->getLeft()->getElement() == this->element) {
                    return true;
                }
            }
            return false;
        };
        bool isRight() const {
            if (parent == nullptr) {
                return false;
            }
            else if (parent->getRight() != nullptr) {
                if (parent->getRight()->element == element) {
                    return true;
                }
            }
            return false;
        };
        int  rightChildHeight() const {
            if (right == nullptr) {
                return 0;
            }
            else return right->getHeight();
        };
        int  leftChildHeight() const {
            if (left == nullptr) {
                return 0;
            }
            else return left->getHeight();
        };
        int updateHeight() {
            
            if (right == nullptr && left == nullptr) {
                this->height = 1;
                return 1;
            }
            else if (right == nullptr) {
                this->height = left->getHeight() + 1;
                return  left->getHeight() + 1;
            }
            else if (left == nullptr) {
                this->height =right->getHeight() + 1;
                return  right->getHeight() + 1;
            }
            else {
                this->height = max(left->getHeight() + 1, right->getHeight() + 1);
                return max(left->getHeight() + 1, right->getHeight() + 1);
            }
        };

        bool isBalanced() {
            if (right != nullptr && left != nullptr) {
                int diff = right->getHeight() - left->getHeight();
                if (diff > 1 || diff < -1) {
                    return false;
                }
                else return true;
            }
            if (left != nullptr) {
                if (right == nullptr && left->getHeight() > 1) {
                    return false;
                }
            }
            if (right != nullptr) {
                
                if (left == nullptr && right->getHeight() > 1) {
                    
                    return false;
                }
            }
            return true;
        };
    };
private:

    int   size;
    Node* root;

public:

    class Iterator {
    private:
        Node* curNode;
        Node* root;


    public:
        Iterator(Node* curNode, Node* root);
        Iterator& operator++();
        Iterator operator++(int a);
        string operator*();
        bool operator!=(Iterator it);
        bool operator==(Iterator it);
        void setCurNode(Node *node);
        void setRoot(Node * root);
        Node* getCurNode();
    };

    Iterator begin() const;
    Iterator end() const;

    static const int MAX_HEIGHT_DIFF = 1;
    AVL();
    AVL(AVL&);
    bool contains(string e);
    bool add(string e);
    bool rmv(string e);
    void print2DotFile(char* filename);
    void pre_order(std::ostream& out);

    

    friend std::ostream& operator<<(std::ostream& out, const AVL& tree);
    
    AVL& operator  =(const AVL& avl);
    AVL  operator  +(const AVL& avl);
    
    AVL& operator +=(const AVL& avl);
    AVL& operator +=(const string& e);
    AVL& operator -=(const string& e);
    AVL  operator +(const string& e);
    AVL  operator  -(const string& e);
    
    Node* rebalance(Node* v);
    Node* rebalance_son(Node* v);
    Node* recon(Node* v, Node* w, Node* u);

    //my method for remove a node
    Node* deleteNode(Node* root, string e);
};

#endif