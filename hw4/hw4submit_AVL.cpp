#include <iostream>
#include <fstream>

#include <stack>          // std::stack
#include "AVL.hpp"

using namespace std;

AVL::AVL() {
    this->size = 0;
    root = nullptr;
}
AVL::AVL(AVL& a) {
    //cout << "eftasa "<< endl;
    this->size = 0;
    root = nullptr;
    
    //cout <<"init size" << this->size << endl ;
    Iterator iter_begin = a.begin();
    Iterator iter_end = a.end();
    while(iter_begin!=iter_end){
       // cout <<"---->"<<iter_begin.getCurNode()->getElement()<< endl;
        this->add(iter_begin.getCurNode()->getElement());
        //  cout << *this << endl;
        iter_begin++;
    }


};
bool AVL::contains(string e) {
    Iterator iter_begin = begin();
  // cout << "iter_begin  " << iter_begin.getCurNode()->getElement() << endl;
    Iterator iter_end = end();
 //   cout << "iter_end  " << iter_end.getCurNode() << endl;
   // cout << (iter_begin.getCurNode() == iter_end.getCurNode()) << endl;
    while (iter_begin != iter_end) {
        
        if (iter_begin.getCurNode()->getElement().compare(e) == 0) {
           // cout << "contains returns true  " << endl;
            return true;
        }
        
        iter_begin++;
        
    }
    //cout << "contains returns false " << endl;
    return false;
};
bool AVL::add(string e) {
    //cout << "add size " << size << endl;
    if (size == 0) {
        this->size++;
        this->root = new Node(e, nullptr, nullptr, nullptr);
        return true;
    }

    if (contains(e)) {
        return false;
    }
    else {
       
        Node* newNode;
        Node* curr = root; 
        newNode = new Node();

        while (curr!=nullptr) {

//            cout << "----> sigkrinw " << curr->getElement() << " " << e << endl;
            if (curr->getElement().compare(e) > 0) {

               // cout << "mikrotero " << endl;

                if (curr->getLeft() == nullptr) {

                    newNode->setParent(curr);
                    newNode->setLeft(nullptr);
                    newNode->setRight(nullptr);
                    newNode->setElement(e); 
                    curr->setLeft(newNode);
                    curr->updateHeight();
                   // cout << "add------> parent_left1 " << endl << newNode->getElement() << endl;
                    //cout << "add------> parent_left2 " << endl << curr->getElement() << endl;
                    rebalance(newNode);
                    this->size++;
                    return true;
                    //is balanced
                }
                curr = curr->getLeft();
            }
            else {
               // cout << "megalitero " << endl;
                if (curr->getRight() == nullptr) {
                    newNode->setParent(curr);
                    newNode->setLeft(nullptr);
                    newNode->setRight(nullptr);
                    newNode->setElement(e);
                    curr->setRight(newNode);
                    curr->updateHeight();
                   // cout << "add------> parent_right2 " << endl << curr-> getElement() << endl;
                    this->size++;
                    rebalance(newNode);
                    return true;
                }
                curr = curr->getRight();
            }
            
        }
        return false;

    }
}

bool AVL::rmv(string e) {

    if(this->root->getElement().compare(e) == 0 && size == 1){
        this->size = 0 ;
        root = NULL ;
        free(root);
        return true ;
    }
    if (!contains(e)) {
        return false;
    }
    else deleteNode(this->root, e);
    this->size--;
    return true ;
}
AVL::Node* AVL::deleteNode(Node * root, string e) {
    

    if (root == nullptr) {
        return root;
    }
    if (e.compare(root->getElement())<0) {
       // cout << "pira diadromi " << root->getLeft()->getElement() << endl;
        root->setLeft(deleteNode(root->getLeft(), e));
    }
    else if (e.compare(root->getElement()) > 0) {
       // cout << "pira diadromi " << root->getRight()->getElement() << endl;
        root->setRight(deleteNode(root->getRight(), e));
    }
    else {
        if (root->getLeft() == nullptr || root->getRight() == nullptr) {
            Node* temp; 
            if (root->getLeft() != nullptr) {
                temp = root->getLeft();
            }
            else if (root->getRight() != nullptr) {
                temp = root->getRight();
            }
            else {
                temp = NULL;
            }
            if (temp == NULL) {
                //cout << "EFTASA" << endl ;
                temp = root;
                root = NULL; 

            }
            else {
                root->setElement(temp->getElement()) ;
                root->setLeft(temp->getLeft());
                root->setRight(temp->getRight());
                root->updateHeight();
            }
            free(temp);
        }
        else {
            Node* temp = root->getRight();
            while (temp->getLeft() != nullptr) {
                temp = temp->getLeft();
            }
            root->setElement(temp->getElement());
            root->setRight(deleteNode(root->getRight(), temp->getElement()));

        }

        if (root == nullptr) {
           // cout << "EFTASAAAA" << endl ;
            return root;
        }

        
           
        }
            
     
   
    Node * v = root ;
    Node *w , *u;
    v->updateHeight();
    if (!v->isBalanced()) {
    // cout << "#############rebalance "<< e <<" " << root->getElement()<<endl;
       
        w = rebalance_son(v);
        u = rebalance_son(w);
        
    //   cout << " w " << w->getElement() << " u " << u->getElement();
        root = recon(root,w,u);
        if(root->getLeft()!=nullptr){
            root->getLeft()->updateHeight();
        }
        if(root->getRight()!=nullptr){
            root->getRight()->updateHeight();
        }
        root->updateHeight();
        //cout << " new right " << v->getRight()->getElement() << " left " << v->getLeft()->getElement();
         //cout << " new right " << root->getRight()->getElement() << " left " << root->getLeft()->getElement() <<"new element "<< root->getElement() << endl;
    }
    
    return root ;
}
AVL::Node* AVL::rebalance(Node* v) {
    if (v == nullptr) {
        return nullptr;
    }
    Node* u, * w;
    while (v != nullptr) {
     //  cout << "EFTASA 1 " << endl;
        v->updateHeight();
      //cout << "v->getparent()  " << v->getElement()<< " " << v->getHeight() << endl;
        if (!v->isBalanced()) {
            
        //  cout << "EFTASA 2 " << endl;
            w = rebalance_son(v);
            u = rebalance_son(w);
     //    cout << " w " << w->getElement() << " u " << u->getElement();
            v = recon(v,w,u);
            v->getLeft()->updateHeight();
            v->getRight()->updateHeight();
            v->updateHeight();
           
        }
        
        v = v->getParent();
    }
    return nullptr;
}

AVL::Node* AVL::recon(Node* v, Node* w, Node* u) {
    if (u->isLeft() && w->isLeft()) {
     // cout << "ar ar " << endl;
        if (v->getParent() != nullptr) {
            if (v->isLeft()) {
                v->getParent()->setLeft(w);
            }
            else {
               // cout << "HEREEEEEEEEEEEEEEEEEEEEEE" <<endl;
                v->getParent()->setRight(w);
            }
            w->setParent(v->getParent());
        }
        else {
            this->root = w;
        }
        v->setLeft(w->getRight());
        if (w->getRight() != nullptr) {
            w->getRight()->setParent(v);
        }
        w->setRight(v);

        if (v->getParent() == nullptr) {
            w->setParent(nullptr);
            this->root = w ;
        }
        v->setParent(w);
        return w;
    }
    else if (w->isRight() && u->isRight()) {
     // cout << "deksia deksia " << endl;
        if (v->getParent() != nullptr) {
            if (v->isRight()) {
                v->getParent()->setRight(w);
            }
            else {
                v->getParent()->setLeft(w);
            }
            w->setParent(v->getParent());
        }
        else {
            this->root = w;
        }
        v->setRight(w->getLeft());
        if (w->getLeft() != nullptr) {
            w->getLeft()->setParent(v);
        }
        w->setLeft(v);

        if (v->getParent() == nullptr) {
            w->setParent(nullptr);
            this->root = w ;
        }
        v->setParent(w);
        return w;
    }
    else if (u->isLeft()) {
        
       //cout << " ar deksia " << endl;
       /*
       cout << " u " << u->getElement() << " w " << w->getElement() << " v " << v->getElement() << endl;
       if (u->getLeft()!= nullptr)
        cout << "$$$$$$$$$$ u->getleftt()" << u->getLeft()->getElement() << " u = " << u->getElement() << endl;
       if (u->getRight() != nullptr)
           cout << "$$$$$$$$$$ u->getRight()" << u->getRight()->getElement() << " u = " << u->getElement() << endl;
       if (w->getLeft() != nullptr)
           cout << "$$$$$$$$$$ w->getLeftt()" << w->getLeft()->getElement() << " w = " << w->getElement() << endl;
       if (w->getRight() != nullptr)
           cout << "$$$$$$$$$$ w->getRight()" << w->getRight()->getElement() << " w = " << w->getElement() << endl;
       if (v->getLeft() != nullptr)
           cout << "$$$$$$$$$$ v->getLeftt()" << v->getLeft()->getElement() << " v = " << v->getElement() << endl;
       if (v->getRight() != nullptr)
           cout << "$$$$$$$$$$ v->getRight()" << v->getRight()->getElement() << " v = " << v->getElement() << endl;
       cout << "============" << endl << endl;
       */
        v->setRight(u->getLeft());
        if (u->getLeft() != nullptr) {
            u->getLeft()->setParent(v);
        }
        w->setLeft(u->getRight());
        if (u->getRight() != nullptr) {
            u->getRight()->setParent(w);
        }
        if (v->getParent() != nullptr) {
            if (v->isRight()) {
                u->setParent(v->getParent());
                v->getParent()->setRight(u);
            }
            if (v->isLeft()) {
                u->setParent(v->getParent());
                v->getParent()->setLeft(u);
            }
          
        }
        else {
            this->root = u;
             u->setParent(nullptr);
             this->root = u;
        }
        v->setParent(u);
        w->setParent(u);
        u->setLeft(v);
        u->setRight(w);
        /*
        cout << "$$$$$$$$$$ u->getleftt()" << u->getLeft()->getElement()<<" u = " <<u->getElement()<< endl;
        if(u->getRight()!=nullptr)
            cout << "$$$$$$$$$$ u->getRight()" << u->getRight()->getElement() << " u = " << u->getElement() << endl;
        if (w->getLeft() != nullptr)
            cout << "$$$$$$$$$$ w->getLeftt()" << w->getLeft()->getElement() << " w = " << w->getElement() << endl;
        if (w->getRight() != nullptr)
            cout << "$$$$$$$$$$ w->getRight()" << w->getRight()->getElement() << " w = " << w->getElement() << endl;
        if (v->getLeft() != nullptr)
            cout << "$$$$$$$$$$ v->getLeftt()" << v->getLeft()->getElement() << " v = " << v->getElement() << endl;
        if (v->getRight() != nullptr)
            cout << "$$$$$$$$$$ v->getRight()" << v->getRight()->getElement() << " v = " << v->getElement() << endl;
            */


     
        

        return u;

    }
    else {
     //  cout << "deksia ar " << endl;
        v->setLeft(u->getRight());
        if (u->getRight() != nullptr) {
            u->getRight()->setParent(v);
        }
        w->setRight(u->getLeft());
        if (u->getLeft() != nullptr) {
            u->getLeft()->setParent(w);
        }
        if (v->getParent() != nullptr) {
            if (v->isRight()) {
                u->setParent(v->getParent());
                v->getParent()->setRight(u);
            }
            if (v->isLeft()) {
                u->setParent(v->getParent());
                v->getParent()->setLeft(u);
            }

        }
        else {
            this->root = u;
            u->setParent(nullptr);
            this->root = u ;
        }
        v->setParent(u);
        w->setParent(u);
        u->setLeft(w);
        u->setRight(v);
        return u;
    }
}



AVL::Node* AVL::rebalance_son(Node* v) {
    if (v == nullptr) {
        return nullptr;
    }
    if (v->leftChildHeight() > v->rightChildHeight()) {
        return v->getLeft();
    }
    else if (v->leftChildHeight() < v->rightChildHeight()) {
        return v->getRight();
    }
    else if (v->isLeft()) {
        return v->getLeft();
    }
    else return v->getRight();


}

void AVL::print2DotFile(char* filename) {

    Iterator iter_begin = begin();
   // cout << "###########BEGIN" << endl;
    Iterator iter_end = end();


    string file(filename);
    ofstream myfile(file.c_str());


    if (!myfile.is_open()) {
        cout << "Unable to open file " << filename;
        return;
    }

    myfile << "digraph StringTree { \nfontcolor=\"navy\";\nfontsize=20;\nlabelloc=\"t\"; \nlabel=\"Arithmetic Expression\"\n";

  //  myfile << "digraph ArithmeticExpressionTree { \nfontcolor=\"navy\";\nfontsize=20;\nlabelloc=\"t\"; \nlabel=\"Arithmetic Expression\"\n";
    
    while (iter_begin != iter_end) {
      //  cout << "###########END" << iter_begin.getCurNode()->getElement() << endl;
        myfile << iter_begin.getCurNode()->getElement() <<"[label=\"" + iter_begin.getCurNode()->getElement() + "\", " + "shape=circle, color=black]" << endl;
        if (iter_begin.getCurNode()->getLeft() != nullptr) {
            myfile << iter_begin.getCurNode()->getElement() << "->"  << iter_begin.getCurNode()->getLeft()->getElement() << endl ;
        }
        if (iter_begin.getCurNode()->getRight() != nullptr) {
            myfile << iter_begin.getCurNode()->getElement() << "->" << iter_begin.getCurNode()->getRight()->getElement()<<endl  ;
        }
        iter_begin++;
    }

    myfile << "}";
}

void AVL::pre_order(std::ostream& out) {

}
std::ostream& operator<<(std::ostream& out, const AVL& tree) {
   // cout << "OPERATOR <<" << endl;
    AVL::Iterator iter_begin = tree.begin();
    AVL::Iterator iter_end = tree. end();
    while (iter_begin != iter_end) {
     //  cout <<"epppp" <<  endl;
        out <<iter_begin.getCurNode()->getElement() << " ";
        iter_begin++;
    }
   // cout << "<< returns " << endl;
    return out;
}
 AVL& AVL::operator=(const AVL& avl) {
    //cout << "eftasa "<< endl;
    char * filename = (char*)"dotfile";
            this->print2DotFile(filename);
    Iterator iter_begin = this->begin() ;
    Iterator iter_end = this->end();
  //  cout << *this <<endl << endl ;
    int counter = 1;
    while(this->size != 0){
        iter_begin = this->begin();
       // cout << "element " << iter_begin.getCurNode()->getElement() <<endl;
        this->rmv(iter_begin.getCurNode()->getElement());
       // cout << "root " << this->root->getElement()<<" size " << this->size << endl << "filename " << "dotfile"<<  std::to_string(counter).c_str() << endl;
      //  cout << endl << endl ;
        //cout << *this << endl ;
     //   const char * filename = ("dotfile"+ std::to_string(counter)).c_str();
       // this->print2DotFile((char*)filename);
        counter ++;
        
    }
    
    //cout <<"init size" << this->size << endl ;
    iter_begin = avl.begin();
    iter_end = avl.end();
    while(iter_begin!=iter_end){
       // cout <<"---->"<<iter_begin.getCurNode()->getElement()<< endl;
        this->add(iter_begin.getCurNode()->getElement());
        //  cout << *this << endl;
        iter_begin++;
    }

    return *this;
}

AVL  AVL::operator  +(const AVL& avl){
    AVL new_tree ;
    Iterator iter_begin = this->begin();
    Iterator iter_end = this->end();
    while(iter_begin != iter_end){
        new_tree.add(iter_begin.getCurNode()->getElement());
        iter_begin++;
    }
    iter_begin = avl.begin();
    iter_end = avl.end();
    while(iter_begin != iter_end){
        new_tree.add(iter_begin.getCurNode()->getElement());
        iter_begin++;
    }
    return new_tree;
}
AVL& AVL::operator +=(const AVL& avl){
    Iterator iter_begin = avl.begin();
    Iterator iter_end = avl.end();
    while(iter_begin != iter_end){
        this->add(iter_begin.getCurNode()->getElement());
        iter_begin++;
    }
    return *this;
}
AVL& AVL::operator +=(const string& e){
    this->add(e);
    return *this;
    
}
AVL& AVL::operator -=(const string& e){
    this->rmv(e);
    return *this;
}
AVL  AVL::operator  +(const string& e){
    AVL new_tree(*this);
    new_tree.add(e);
    return new_tree;
}
AVL AVL::operator  -(const string& e){
    AVL new_tree(*this);
    new_tree.rmv(e);
    return new_tree;

}
////////node
AVL::Node::Node() {
}
AVL::Node::Node(const string& e, Node* parent, Node* left, Node* right) {
    AVL::Node();
    if (parent == nullptr) {

        this->parent = nullptr;

    }
    else
        *(this->parent) = *parent;
    if (left == nullptr) {
        this->left = nullptr;
    }
    else
        *(this->left) = *left;
    if (right == nullptr) {
        this->right = nullptr;
    }
    else
        *(this->right) = *right;
    height = 1;
    element = e;
}

////////iterator
AVL::Iterator::Iterator(Node* curNode, Node* root) {
    this->curNode = curNode;
    this->root = root;
};
 void AVL::Iterator::setCurNode(Node *node){
     this->curNode = node ;
 }
void AVL::Iterator::setRoot(Node * root){
    this->root = root ;
}

AVL::Iterator& AVL::Iterator::operator++() {
    // cout << "eftasa ++ " << endl;
    
    stack<Node*> st;
    Node* curr = root;
    Node* prev = nullptr;
    
    while (!st.empty() || curr != NULL) {
        while (curr != NULL) {

          //  cout << "curr element "<<curr->getElement() << " " << this->curNode->getElement() << " " << endl;

            if (curr->getRight()!=nullptr)
                st.push(curr->getRight());
            
            prev = curr;
            curr = curr->getLeft();
          
            if (prev->getElement().compare(this->curNode->getElement()) == 0 && curr != nullptr) {
             //  cout << "return curr1 " << curr->getElement() << " " << endl;
                
                this->curNode = curr;
                return *this;
            }
          
        }

        // We reach when curr is NULL, so We
        // take out a right child from stack
        if (st.empty() == false) {

            curr = st.top();
            if (curr != nullptr) {
               // cout << "curr ginete  " << curr->getElement() << endl;
                if (curr->getLeft() != nullptr) {
                //    cout << "curr -> left " << curr->getLeft()->getElement() << " " << endl;
                }
                if (curr->getRight() != nullptr) {
                   // cout << "curr -> left " << curr->getRight()->getElement() << " " << endl;
                }
            }
            
    
            if (prev->getElement().compare(this->getCurNode()->getElement()) == 0) {
                prev = curr;
                this->curNode = curr;
              //   cout << "return curr2" << endl << curr->getElement() << " " << endl;
                return *this;
            }
          //  cout << "prev ginete  "<< curr->getElement() << endl; 
            prev = curr; 
            st.pop();
        }
    }
    this->curNode = NULL;
   //cout << "return null" << curr << " " << endl;
    return *this;
}

string AVL::Iterator::operator*() {
    return this->curNode->getElement();
}

AVL::Iterator AVL::Iterator::operator++(int a) {
  //  cout << "eftasa ++ " << endl;
    Iterator ret(this->root, this->getCurNode());
    stack<Node*> st;
    Node* curr = root;
    Node* prev = nullptr;

    while (!st.empty() || curr != NULL) {


        while (curr != NULL) {

          //  cout << "curr element "<<curr->getElement() << " " << this->curNode->getElement() << " " << endl;

            if (curr->getRight()!=nullptr)
                st.push(curr->getRight());
            prev = curr;
           // cout << "--->prev ginete  " << curr->getElement() << endl;
            curr = curr->getLeft();
            /*
            if (curr != nullptr) {
                cout << "------>curr ginete  " << curr->getElement() << endl;
                if (curr->getLeft() != nullptr) {
                    cout << "curr -> left " << curr->getLeft()->getElement() << " " << endl;
                }
                if (curr->getRight() != nullptr) {
                    cout << "curr -> left " << curr->getRight()->getElement() << " " << endl;
                }
            }
            */
           // cout << "===> sigkrinw stin operator  " << prev->getElement() << " " << this->curNode->getElement() << endl;
            if (prev->getElement().compare(this->curNode->getElement()) == 0 && curr != nullptr) {
             //  cout << "return curr1 " << curr->getElement() << " " << endl;
                
                this->curNode = curr;
              
                return ret;
            }
          
        }

        // We reach when curr is NULL, so We
        // take out a right child from stack
        if (st.empty() == false) {

            curr = st.top();
            if (curr != nullptr) {
               // cout << "curr ginete  " << curr->getElement() << endl;
                if (curr->getLeft() != nullptr) {
                //    cout << "curr -> left " << curr->getLeft()->getElement() << " " << endl;
                }
                if (curr->getRight() != nullptr) {
                   // cout << "curr -> left " << curr->getRight()->getElement() << " " << endl;
                }
            }
            
         // if (prev != nullptr)
           /* cout << "operator mpika deksia kai" << endl << "prev->getElement()" << prev->getElement() << " " << endl;
            cout << "this<curNode->getElement() kai " << endl << this->getCurNode()->getElement() << " " << endl;
           */
            if (prev->getElement().compare(this->getCurNode()->getElement()) == 0) {
                prev = curr;
                this->curNode = curr;
                
              //   cout << "return curr2" << endl << curr->getElement() << " " << endl;
                return ret;
            }
          //  cout << "prev ginete  "<< curr->getElement() << endl; 
            prev = curr; 
            st.pop();
        }
    }
  
    this->curNode = NULL;
   //cout << "return null" << curr << " " << endl;
    return ret;
}



bool AVL::Iterator::operator!=(Iterator it) {
    if (this->curNode == NULL && it.curNode == NULL) {
        //cout << "return true " << endl;
        return false;
    }
    else if (this->curNode == NULL || it.curNode == NULL) {
        //cout << "return true " << endl;
        return true;
    }
    if (this->curNode->getElement().compare(it.curNode->getElement()) == 0) {
       // cout << "return false " << endl;
        return false;
    }
    //cout << "return true " << endl;
    return true;
}

bool AVL::Iterator::operator==(Iterator it) {
    if (this->curNode == NULL && it.curNode == NULL) {
        return true;
    }
    else if (this->curNode == NULL || it.curNode == NULL) {
        return false;
    }
    if (this->curNode->getElement().compare(it.curNode->getElement()) == 0) {
        return true;
    }
    return true;
};

AVL::Iterator AVL::begin() const {
    AVL::Iterator iter(this->root, this->root);
  //  cout << "---->" << iter.getCurNode() << endl;
    return iter;
}

AVL::Iterator AVL::end() const {
    AVL::Iterator iter(this->root, this->root);
    while (iter.getCurNode() != NULL) {
        iter++;
    }
    return iter;
}

AVL::Node* AVL::Iterator::getCurNode() {
    return curNode;
}


