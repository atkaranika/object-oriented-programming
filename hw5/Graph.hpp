
#ifndef _GRAPH_HPP_ 
#define _GRAPH_HPP_

#include <list>
#include <vector> 
#include <ostream>
#include <iostream>
#include <algorithm>
#include <queue>
using namespace std;
template<typename T>
struct Edge {
  T from;
  T to;
  int dist;
  Edge(T f, T t, int d): from(f), to(t), dist(d) {
  }
  bool operator<(const Edge<T>& e) const{
        if(this->dist < e.dist){
              return true ;
        }
        return false ; 
  }
  bool operator>(const Edge<T>& e) const{
        if(this->dist > e.dist){
              return true ;
        }
        return false ; 
  }
  template<typename U>
  friend std::ostream& operator<<(std::ostream& out, const Edge<U>& e);


};

struct DisjointSets
{
    int *parent, *rnk;
    int n;
  
    // Constructor.
    DisjointSets(int n)
    {
        // Allocate memory
        this->n = n;
        parent = new int[n+1];
        rnk = new int[n+1];
  
        // Initially, all vertices are in
        // different sets and have rank 0.
        for (int i = 0; i <= n; i++)
        {
            rnk[i] = 0;
  
            //every element is parent of itself
            parent[i] = i;
        }
    }
  
    // Find the parent of a node 'u'
    // Path Compression
    int find(int u)
    {
        if (u != parent[u])
            parent[u] = find(parent[u]);
        return parent[u];
    }
  
    // Union by rank
    void merge(int x, int y)
    {
        x = find(x), y = find(y);
  
        /* Make tree with smaller height
           a subtree of the other tree  */
        if (rnk[x] > rnk[y])
            parent[y] = x;
        else // If rnk[x] <= rnk[y]
            parent[x] = y;
  
        if (rnk[x] == rnk[y])
            rnk[y]++;
    }
};


template<typename T>
std::ostream& operator<<(std::ostream& out, const Edge<T>& e) {
  out << e.from << " -- " << e.to << " (" << e.dist << ")";
  return out;
}

template <typename T>
class Graph {
  void expand_table();
  void shrink_table();  
  
public:
bool directed ; 
int capacity ;
int size = 0 ;
vector <struct Edge<T> > edges ; 

vector<vector<pair<T, int>>> adj;
      Graph(bool isDirectedGraph = true);
      ~Graph();
      bool contains(const T& info);
      bool addVtx(const T& info);
      bool rmvVtx(const T& info);
      bool addEdg(const T& from, const T& to, int distance);
      list<T> bfs(const T& info) const;
      list<T> dfs(const T& info) const;
      list<T> dfs_rec(const T& info, bool*visited) const;
      list<Edge<T>> mst();
      list<T> dijkstra(const T& from, const T& to);
  /*
  bool rmvEdg(const T& from, const T& to);
      void print2DotFile(const char *filename) const;
  list<T> bellman_ford(const T& from, const T& to);
  */
  
};
template<typename T>
Graph<T>::~Graph(){}
template<typename T>
Graph<T>::Graph(bool isDirected ){
      directed = isDirected;
}

template<typename T>
bool Graph<T>::contains(const T& info){
 
  for (unsigned i=0; i<this->adj.size(); i++){
        if(this->adj.at(i).at(0).first== info){
            return true ;
        }
        
  }
  return false ;
}

template<typename T>
bool Graph<T>::addVtx(const T& info){
      if(contains(info)){
            return false ;
      }
      size++;
      vector<pair<T, int>> v ;
      v.push_back(make_pair(info, size));
      adj.push_back(v);
      return true ;  
}
template<typename T>
bool Graph<T>::rmvVtx(const T& info){
      if (!contains(info)){
            return false ;
      }
    
      for (typename vector<vector<pair<T, int>>>::iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                    
            if(index_in->at(0).first == info){
                 index_in->clear();
                 // adj.erase(index_in);
                  //     cout << "addEdg to_index " << to_index << endl ;
                  adj.erase(index_in);
            }
            else {
                  for (typename vector<pair<T, int>>::iterator x_in = index_in->begin() ;x_in != index_in->end(); x_in++){
                        if(x_in->first == info){
                              index_in->erase(x_in);
                        }
                  }
            }
      }
      int counter = 0 ; 
       for (typename vector<vector<pair<T, int>>>::iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
             counter++;
             index_in->at(0).second = counter ; 
       }
       /*
      cout << endl << endl ;
      for (typename vector<vector<pair<T, int>>>::const_iterator index = adj.begin() ;index != adj.end(); index++){
            
            for (typename vector<pair<T, int>>::const_iterator x = index->begin() ;x != index->end(); x++){
                  cout<< x->first << " ";
            }
            cout << endl ;
      }
      */
      size --;
      return true ;  
}

template<typename T>
bool Graph<T>::addEdg(const T& from, const T& to, int distance){
      T from_loc = from;
      T to_loc = to ;
      if (!contains(from_loc) || !contains(to_loc)){
            return false ;
      }
      int counter = 0 ;
      bool end = false ; 
      for (typename vector<vector<pair<T, int>>>::iterator index = adj.begin() ;index != adj.end(); index++)
      {
            if(end){
                  index = adj.begin();
                  end = false ;
            }
            counter ++;
            if(index->at(0).first== from_loc ){
                 
                  for (typename vector<pair<T, int>>::const_iterator x = index->begin()+1 ;x != index->end(); x++){
                       // cout >> x.first;
                         if(x->first== to_loc){
                             // cout << "add edge iparxei akmi " << x->first<< endl ;
                              return false ;
                        }
                  }
                  //pou tha mpei h nea akmi 
                  int to_index = size +1 ;
                  int max_loc = 0 ;
                  for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                                                
                        if(index_in->at(0).first == to_loc){
                              to_index = index_in->at(0).second ;
                         //     cout << "addEdg to_index " << to_index << endl ;
                        }

                  }
                  end = false ;
                 // cout  << " start "<<index->at(0).first<<endl ;
                  for (typename vector<pair<T, int>>::iterator x_in = index->begin()+1 ;x_in != index->end(); x_in++){
      
                      // cout <<x_in->first <<endl ;
                        for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                             
                              if(index_in->at(0).first == x_in->first){
                                          max_loc = index_in->at(0).second ;
                                          if(max_loc > to_index){
                                               // cout  << " start to add "<<adj.at(counter-1).at(0).first<<endl ;
                                                //cout << " index to add " << (x_in)->first << endl ;
                                                index->insert(x_in, make_pair(to_loc,distance));
                                                
                                                if(directed || to_loc == from ){
                                                    
                                                      return true ;
                                                }
                                                else {
                                                      T old = from_loc ;
                                                      from_loc = to_loc ; 
                                                      to_loc = old ;
                                                      end = true ; 
                                                      break ;
                                                }
                                          }
                              }
                              
                        }
                        if(end){
                              break ;
                        }
                  }
                
                  
                  if(max_loc < to_index){
                        //cout  << " start to add "<<adj.at(counter-1).at(0).first<<endl ;
                        //cout << " index to add 0"<< endl ;
                        adj.at(counter-1).push_back(make_pair(to_loc,distance));
                        
                        if(directed || to_loc == from ){
                              //cout << "return" <<endl ;

                              return true ;
                        }
                        else {
                              T old = from_loc ;
                              from_loc = to_loc ; 
                              to_loc = old ;
                              end = true ; 
                              
                        }
                  }
                   if(end ){
                        
                        counter = 0 ;
                         // cout << "return" <<endl ;
                        if(to_index > index->at(0).second){
                              
                              struct Edge<T> new_edges(from, to, distance);
                              edges.push_back(new_edges);
                        }
                        else{
                              struct Edge<T> new_edges(to, from, distance);
                              edges.push_back(new_edges);
                        }
                        
                        index = adj.begin();
                        continue ;
                  }
            }
  
      }
 return true ;


}

template<typename T>
list<T> Graph<T>::bfs(const T& info) const{
      list<T> bfs_list ;
      bool *visited = new bool[size];
      for(int i = 0; i < size; i++)
        visited[i] = false;
     //cout <<"eftasa" << endl ;
    // Create a queue for BFS
      list<int> queue;
 
      int index = 0 ;
      for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
            if(index_in->at(0).first == info){
                  index = index_in->at(0).second-1 ;
            }
      }
      visited[index] = true;
      
      queue.push_back(index);
      bfs_list.push_back(info);
      list<int>::iterator i;
      while(!queue.empty()){
            // Dequeue a vertex from queue and print it
            index = queue.front();
            
            queue.pop_front();
            
      
            // Get all adjacent vertices of the dequeued
            // vertex s. If a adjacent has not been visited,
            // then mark it visited and enqueue it
            for (typename vector<pair<T, int>>::const_iterator i = adj.at(index).begin(); i != adj.at(index).end(); ++i)
            {     
                 // cout <<"---->    "<< i->first << endl ;
                  for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                        if(index_in->at(0).first == i->first){
                              if (!visited[index_in->at(0).second-1]){
                        
                                    visited[index_in->at(0).second-1] = true;
                                    bfs_list.push_back(index_in->at(0).first);
                                //    cout << "index "<<index << " "<<index_in->at(0).first << " ";
                                    
                                    queue.push_back(index_in->at(0).second-1);
                              }
                              break ; 
                        }
                  }

                 
            }
      }
      /*
      for (typename vector<vector<pair<T, int>>>::const_iterator index = adj.begin() ;index != adj.end(); index++){
            
            for (typename vector<pair<T, int>>::const_iterator x = index->begin() ;x != index->end(); x++){
                  cout << "name "<<x->first << " distance "<< x->second<<endl;
            }
      }
      */
      delete [] visited ;
      return bfs_list;
}
template<typename T>
list<T> Graph<T>::dfs(const T& info) const{
      
      bool *visited = new bool[size];
      for(int i = 0; i < size; i++)
        visited[i] = false;
      list<T> dfs_list  = dfs_rec(info, visited);
      delete [] visited ;
      return dfs_list;
}
template<typename T>
list<T> Graph<T>::dfs_rec(const T& info, bool*visited) const{
       list<T> dfs_list ;
 
      int index = 0 ;
      for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
            if(index_in->at(0).first == info){
                  index = index_in->at(0).second-1 ;
            }
      }
      visited[index] = true;
      dfs_list.push_back(info);
      list<int>::iterator i;
     // cerr << "info ----->" << info << endl ;
  
      for (typename vector<pair<T, int>>::const_iterator i = adj.at(index).begin(); i != adj.at(index).end(); ++i)
      {     
            // cout <<"---->    "<< i->first << endl ;
            for (typename vector<vector<pair<T, int>>>::const_iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                  if(index_in->at(0).first == i->first){
                        if (!visited[index_in->at(0).second-1]){
                  
                              visited[index_in->at(0).second-1] = true;
                              // dfs_list.push_back(index_in->at(0).first);
                              dfs_list.splice (dfs_list.end(), dfs_rec(index_in->at(0).first, visited));
                              
                              break ; 
                        }

                  }
            }

            
      }
      
      return dfs_list; 
}

template<typename T>
list<Edge<T>>  Graph<T>::mst() {
      list<Edge<T>> mst_list ; 
     
      std::sort(edges.begin(), edges.end());
      DisjointSets ds(size);

      typename vector< struct Edge<T> >::iterator it;
      for (it=edges.begin(); it!=edges.end(); it++)
      {
            T u = it->from;
            T v = it->to;
            int index_u = 0 ;
            int index_v = 0 ;
            for (typename vector<vector<pair<T, int>>>::iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                    
                  if(index_in->at(0).first == u){
                        index_u = index_in->at(0).second ; 
                  }
                  if(index_in->at(0).first == v){
                        index_v = index_in->at(0).second ; 
                  }
            }
            
            int set_u = ds.find(index_u);
            int set_v = ds.find(index_v);
      
            if (set_u != set_v)
            {
                  
                  //cout << u << " - " << v << endl;
                  struct Edge<T> new_edge(u,v,it->dist) ; 
                  mst_list.push_back(new_edge);
                  ds.merge(set_u, set_v);
            }
      }
      delete [] ds.parent;
      delete [] ds.rnk;
      return mst_list ; 
}
template<typename T>
list<T> Graph<T>::dijkstra(const T& from, const T& to){
      list<T> dijkstra ; 
      vector<vector<T>> vector_paths;
      int src = 0 ; 
      int dest =0;
      for (typename vector<vector<pair<T, int>>>::iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                  
            if(index_in->at(0).first == from){
                  src= index_in->at(0).second-1 ; 
            }
            if(index_in->at(0).first == to){
                  dest = index_in->at(0).second-1 ; 
            }
      }
            
      typedef pair<int, int> iPair;
      priority_queue< iPair, vector <iPair> , greater<iPair> > pq;
      
      vector<int> dist(size, 1000000);
     
    pq.push(make_pair(0, src));

    dist[src] = 0;

    for (int i = 0 ; i < size ; i++){
      
          vector<T> new_vec ;
          new_vec.push_back(from);
          vector_paths.push_back(new_vec);
    }
     //cerr<<"eftasa" << endl ;
    while (!pq.empty())
    {
        
        int u = pq.top().second;
        pq.pop();
  
        // 'i' is used to get all adjacent vertices of a vertex
        typename vector<pair<T, int>>::iterator i;
        for (i = adj.at(u).begin(); i != adj.at(u).end(); ++i)
        {
            
            T v = (*i).first;
            int index_v = 0 ; 
            for (typename vector<vector<pair<T, int>>>::iterator index_in = adj.begin() ;index_in != adj.end(); index_in++){
                  
            
                  if(index_in->at(0).first == v){
                        index_v = index_in->at(0).second-1 ; 
                  }
            }
            
            int weight = (*i).second;
          //  cout << "node " << adj.at(u).at(0).first << " to " << v <<" index_v "<< index_v<<  " dist " << dist[u] + weight<< endl ;
          
            //  If there is shorted path to v through u.
            if (dist[index_v] > dist[u] + weight)
            {
                dist[index_v] = dist[u] + weight;
                vector_paths.at(index_v).clear();
                vector_paths.at(index_v) = vector_paths.at(u);
                vector_paths.at(index_v).push_back(adj.at(u).at(0).first);
               // cout << "push" << dist[index_v] << " " <<index_v << endl ; 
                pq.push(make_pair(dist[index_v], index_v));
            }
        }
    }
  /*
      printf("Vertex   Distance from Source\n");

      for (int i = 0; i < size; ++i)
            printf("%d \t\t %d\n", i, dist[i]);
      

      cout << "index dest" << dest << endl ; 
      */
       for (typename vector<T>::iterator index_in = vector_paths.at(dest).begin() ;index_in != vector_paths.at(dest).end(); index_in++){
                  
      
             dijkstra.push_back(*index_in);
      }
      if(!dijkstra.empty())
       dijkstra.erase(dijkstra.begin());
      return dijkstra ; 
}

#endif