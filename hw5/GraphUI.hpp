
#ifndef _GRAPH_UI_
#define _GRAPH_UI_
#include <list>
//using namespace std;
template <typename T>
int graphUI() {
  
  string option, line;
  //int distance;
  bool digraph = false;
  
  cin >> option;
  if(!option.compare("digraph"))
    digraph = true;
  Graph<T> g(digraph);
  
  while(true) {
    
    std::stringstream stream;
    cin >> option;
   // cout << option << endl << endl ;
    if(!option.compare("av")) {
      getline(std::cin, line);
      stream << line;
      T vtx(stream);
      if(g.addVtx(vtx))
        cout << "av " << vtx << " OK\n";
      else
        cout << "av " << vtx << " NOK\n";
    }
    else if(!option.compare("rv")) {
      std::stringstream stream_from;
      string from ;
      cin >> from;
      stream_from << from;
      T rmv_vtx(stream_from);
     
       if( g.rmvVtx(rmv_vtx))
        cout << "rv " << rmv_vtx << " OK\n";
      else
        cout << "rv " << rmv_vtx << " NOK\n";
    }
    else if(!option.compare("ae")) {
      T from(cin) ;
      T to(cin) ; 
     
      int distance ;
  
      cin >> distance;
     
      if(g.addEdg(from, to, distance))
        cout << "ae " << from  <<" " << to<< " "<<"OK\n";
      else
        cout << "ae " << from  <<" " << to<< " "<<"NOK\n";
    }
    else if(!option.compare("re")) {

    }
    else if(!option.compare("dot")) {
      
    }
    else if(!option.compare("bfs")) {
      std::stringstream stream_from;
      string from ;
      cin >> from;
      stream_from << from;
       T from_t(stream_from);
       list<T> bfs_list ;
      cout << "\n----- BFS Traversal -----\n";
      bfs_list = g.bfs(from_t);
      typename std::list<T>::iterator it;
      int counter = 0 ; 
    //  cout << g.size << endl ;
      for (it = bfs_list.begin(); it != bfs_list.end(); ++it){
          cout << *it ;
         // it_end = it++;
         
         counter++;
         if(counter!= (int)bfs_list.size()){
           //cout <<counter <<endl ;
            cout<< " -> ";
         }
       
      }

      cout << "\n-------------------------\n";
    }
    else if(!option.compare("dfs")) {
      
      std::stringstream stream_from;
      string from ;
      cin >> from;
      stream_from << from;
       T from_t(stream_from);
       list<T> dfs_list ;
      cout << "\n----- DFS Traversal -----\n";
      dfs_list = g.dfs(from_t);
      typename std::list<T>::iterator it;
      int counter = 0 ; 
      for (it = dfs_list.begin(); it != dfs_list.end(); ++it){
          cout << *it ;
         // it_end = it++;
         
         counter++;
         if(counter!=(int) dfs_list.size()){
           //cout <<counter <<endl ;
            cout<< " -> ";
         }
       
      }
      cout << "\n-------------------------\n";
    }
    else if(!option.compare("dijkstra")) {
      getline(std::cin, line);
      stream << line;
      T from(stream);
      T to(stream);
      list <T> dijkstra = g.dijkstra(from,to);
      cout << "Dijkstra (" << from << " - " << to <<"): " ;

      typename std::list<T>::iterator it;
      int counter = 0 ; 
     
      for (it = dijkstra.begin(); it != dijkstra.end(); ++it){
          cout << *it ;
         // it_end = it++;
         
         counter++;
         if(counter!=(int) dijkstra.size()){
           //cout <<counter <<endl ;
            cout<< ", ";
         }
       
      }
      if(!dijkstra.empty())
        cout << ", " << to ;
      cout <<endl;
  
    }


    else if(!option.compare("mst")) {
      //int sum = 0 ;
      cout << "\n--- Min Spanning Tree ---\n";
      list<Edge<T>> mst_list = g.mst();
      typename std::list<Edge<T>>::iterator it;
      int cost = 0 ; 
   
      for (it = mst_list.begin(); it != mst_list.end(); ++it){
     
        cout << it->from << " -- " << it->to << " " <<"("<< it->dist<<")"<<endl  ;
        cost +=it->dist;
      }
      cout << "MST Cost: " << cost << endl;
    }
    else if(!option.compare("q")) {
      cerr << "bye bye...\n";
      return 0;
    }
    else if(!option.compare("#")) {
      string line;
      getline(cin,line);
      cerr << "Skipping line: " << line << endl;
    }
    else {
      cout << "INPUT ERROR\n";
      return -1;
    }
  }
  return -1;  
}

#endif