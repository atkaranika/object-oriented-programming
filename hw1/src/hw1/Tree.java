/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1;

import static java.lang.Math.pow;

/**
 *
 * @author 99kar
 */
// constructor. // 
public class Tree 
{
    node root ;
    String str ;
    public Tree(String stri){
        StringBuilder str_float = new StringBuilder();
        StringBuilder str_new = new StringBuilder(stri) ;
        // str_new is the arithmetic expression with all the spaces deleted. // 
        for (int i = 0 ; i < str_new.length(); i++){
            if (str_new.charAt(i) == ' '){
                str_new.deleteCharAt(i);
                i--;        
            }
        } 
        int left_counter = 0 ;
        int right_counter = 0 ;
        int counter = 0 ;        // number of '\' chars//    
        for(int i = 0; i < str_new.length(); i++){  
            if (str_new.charAt(i) == '\\'){
                counter++;
                //  '\\' must be followed by integer greater than one. // 
                if (is_operate(str_new.charAt(i+1)) == 0)  
                    {
                        System.out.println("\n[ERROR] Invalid expansion expression" );
                        System.exit(1);
                    }
                 else if (Character.getNumericValue(str_new.charAt(i+2)) <=1)
                    {
                        System.out.println("\n[ERROR] Invalid expansion expression" );
                        System.exit(1);
                    }
                 int num ;
                // 1.if before '\' there is ')', this means that '\' operator concerns a whole expression and    // 
                // 2.need to find this.                                                                          //                                                                                   // 
                // 3.example  (3+2*(5+7))\\2                                                                     // 
                // 4.         |         | | |                                                                    // 
                // 5.    `    j       i-1 i p                                                                    //  
                // 6.sub = (3+2*(5+7)) int_num = 2                                                               // 
                 if (str_new.charAt(i-1) == ')'){
                    String sub = "";
                    int index ;
                    int c = 0 ;
                    for(int j = i ; j > 0 ; j --){
                        // 3.when the right counter(number of ')'), becomes equal to c(number of '(') // 
                        // 2.we reach the end of wanted expression                                    // 
                        if (str_new.charAt(j) == ')'){
                            right_counter++;
                        }
                        if (str_new.charAt(j) == '('){
                            c++;
                            if (c == right_counter){
                                index = j ;
                                sub = str_new.charAt(i+1)+ str_new.substring(j, i);
                                int p = i+2 ;
                                StringBuilder int_num  = new StringBuilder("");
                                while (is_num(str_new.charAt(p)) == 1 && p < str_new.length() ){
                                    if (str_new.charAt(p) == '.'){
                                         System.out.println("\n[ERROR] Invalid expansion expression" );
                                        System.exit(1);
                                    }
                                    int_num.append(str_new.charAt(p));
                                    p++;
                                }
                                num = Integer.parseInt(int_num.toString());
                                str_new.deleteCharAt(i);
                                str_new.deleteCharAt(i);
                                str_new.deleteCharAt(i);
                          
                                // 2.we extend the expression, by removind '\\' and complete with appropriate times of expression // 
                                // 2.example (3+2*(5+7))\\2 =>                                                                    // 
                                // 3.sub.length = length of (3+2*(5+7))\*3                                                        // 
                                // 4.                       |                                                                     // 
                                // 5.                       i                                                                     // 
                                // 6.                    = (3+2*(5+7))*(3+2*(5+7))*(3+2*(5+7))                                    // 
                                // 7.if there are more than 1 '\' characters we should put every expressions which have '\' into  // 
                                // 8.separate parentheses for example (3+2*(5+7))\*2\^2                                           // 
                                // 9. = (3+2*(5+7))*3+2*(5+7)))^(3+2*(5+7))*3+2*(5+7))).                                           //                                                                  //  
                                if (counter > 1 ){ 
                                    str_new.insert(i - sub.length() + 1, '(');
                                    i++;
                                }                              
                                int k = 0 ;
                                for(k = 0 ; k < num - 1 ; k++){
                                    str_new.insert(i + k * sub.length(), sub);
                                }
                                if (counter > 1 ){
                                    str_new.insert(i + k * sub.length(), ')');
                                    i++;
                                }
                                // In order to go to the next expression we need to reset index and counters. // 
                                i = 0 ;
                                left_counter = right_counter = 0;
                                break ;
                            }
                        }
                        
                    }

                 }
                 // 1.if there isn't ) before \ there must be a float. Thus we take all the number until we reach the begining of the number. // 
                 // 2.example 2+3.57\*3 = 2+3.57*3.57*3.57
                 else {
                     int z = i-1 ;
                     while(str_new.charAt(z) == '0' || str_new.charAt(z) == '1' ||str_new.charAt(z) == '2' ||str_new.charAt(z) == '3' ||str_new.charAt(z) == '4' ||str_new.charAt(z) == '5' ||str_new.charAt(z) == '6' || str_new.charAt(z) == '7' || str_new.charAt(z) == '8' || str_new.charAt(z) == '9' || str_new.charAt(z) == '.'){
                         str_float.append(str_new.charAt(z));
                         z--;
                     }
                     str_float= str_float.reverse();
                 }

            }
            // 1.when there is '\' before this must exist either a number(1,2,3,..) or a ')'. // 
            else  if (is_operate(str_new.charAt(i)) != 1 && is_num(str_new.charAt(i)) !=1 && str_new.charAt(i) != '(' && str_new.charAt(i) != ')'){
                System.out.println("[ERROR] Invalid character");
                System.exit(1);
            }   
        }
        // finally, we remove all '\' from the string. // 
        this.str = str_new.toString();
        str.replace("\\", "");
        this.root = build(str_new.toString());
    }
 
    // 1.build the tree. //  
    // 2.recursive function, every children of current root is considered another tree, thus we call again the build function  // 
    // 3.every time for each children in order to create the whole tree                                                        // 
    // 4.the algorithm works as follows. We find the operation with the higher priority, we call the recuirsive function for   // 
    // 5.left part and we assign the returned node as the left node of the caller. The same for right part.                    // 
    // 6.example (9 + 5.5) x (3.3 - 28)                                                                                        // 
    // 7.if the given expression is inside parentheses we should remove them, for this reason we count the number of '(' and   //     
    // 8.')' and if they are equal, we keep only the interior string. In the present example we have number '(' and ')' equal  // 
    // 9.but the string isn't(whole) inside parentheses. In order to avoid removing these parentheses and distinguish this case// 
    // 10.we add one more condition, which requires that only if we have local_counter_left = local_counter_right AND we reach // 
    // 11.the end of the expression, we can remove the parentheses.                                                            // 
     public node build(String str){
        int iterations ; 
        int operator;
        
        node current = new node();
        String left ;
        String right ;
        char cur_op ;
        String str_float ;
        String []str_par = null ;
        
        int counter_right = 0 ;
        int counter_left = 0 ;
        int cur_index;
        
        int local_counter_right = 0 ;  
        int local_counter_left = 0 ;   
       
        if (str.length() > 1)
        // the new sting without left and right parentheses if is included initially in parentheses the whole. // 
        if (str.charAt(0) == '(' && str.charAt(str.length()-1)  == ')'){
            for(int i = 0; i < str.length(); i++){
                if (str.charAt(i) == ')'){
                     local_counter_right++;
                }
                 if (str.charAt(i) == '('){
                     local_counter_left++;
                }
                 if (local_counter_right == local_counter_left ){
                     if (i == str.length()-1){
                       str = str.substring(1,str.length()-1);
                     }
                     else break ;
                 }
            }
        }
        // 1.At first, right and left stings are the same.                          // 
        // 2.if the str is a float then we reach the leaves and we return the node. // 
        left = right = str;
        try{
            Float.parseFloat(str);
            current = new node( );
           
            current.left = current.right = null;
            current.cur_float = str;
            return current;
        }
        catch(NumberFormatException e){
        }
        // 1.if we doesn't reach the end. In order to call the function and give the left and the right we need to // 
        // 2.define the priorities, to find out at which point we should break the str.                            //
        // 3.the algorithm works as follows:                                                                       //
        // 4.we traverse the whole string, char by char, we firstly check for invalid seccesively chars eg. (+, /) //
        // 5.if we have more ')' than '(' then we also print an error message and exit the program. After the      //
        // 6.required checks we need to give priorities to every operator. The hierarchy of priorities is the      //
        // 7.the below: 1. expressions contained in parentheses take higher priority over any operation            //
        // 8. 2. ^ 3. /,* 4.+,-. Thus when we reach an operator like all of above we give it the approptiate prior.//
        // 9.In order to achieve the goal of giving the highest priority to the operations that contained in (..)  //
        // 10.we give an extra "weight" to this metric. So, an operation has a priority from its kind(+,- 1)       //
        // 11. (/ * 2) (^ 3) and in this priority ia added the number of parentheses that the operation contained  //
        // 12. *3. For example. 1^(2+3) . priority of ^ is 3 (there are no '(' opened before it), and this of + is //
        // 13. 1 + 3 * 1(1 unclosed '(' before '+').                                                               //
        prot []prot = new prot[str.length()];
        int i ;
        int j = 0;
       
        for(i = 0 ; i < str.length(); i++){
            prot[i] = new prot(1000);
            if (str.charAt(i)== '(' ){
                if (is_operate(str.charAt(i+1))==1 ){
                    System.out.println("[ERROR] Operand appears after opening parenthesis");
                    System.exit(1);
                }
                counter_left++;
            }
            else if (str.charAt(i) == ')'){
                counter_right++;
                if (counter_left < counter_right){
                    System.out.println("\n[ERROR] Closing unopened parenthesis\n");
                    System.exit(1);
                }
                if (is_operate(str.charAt(i-1))==1){
                    System.out.println(" [ERROR] Operand appears before closing parenthesis");
                    System.exit(1);
                }
                if (counter_left == counter_right){
                    counter_left = counter_right = 0 ;
                    j++;
                }
            }
            else if (str.charAt(i) == '+' || str.charAt(i) == '-'){
                if (is_operate(str.charAt(i + 1)) == 1){
                    System.out.println("\n[ERROR] Two consecutive operands");
                    System.exit(1);
                }
                else if (str.charAt(i+1) == ')'){
                    System.out.println(" [ERROR] Operand appears before closing parenthesis");
                    System.exit(1);
                }
                prot[i] = new prot(1 + 3 * (counter_left - counter_right)) ;
            }
            else if (str.charAt(i) == 'x' || str.charAt(i) == '*' || str.charAt(i) == '/' ){
               if (is_operate(str.charAt(i + 1)) == 1){
                    System.out.println("[ERROR] Two consecutive operands\n");
                    System.exit(1);
                }
               else if (str.charAt(i+1) == ')'){
                        System.out.println("[ERROR] Operand appears before closing parenthesis");
                        System.exit(1);
                    }
                prot[i] = new prot(2 + 3*(counter_left- counter_right)) ;
            }
            
            else if (str.charAt(i) == '^' ){
                if (is_operate(str.charAt(i + 1))==1){
                    System.out.println("[ERROR] Two consecutive operands\n");
                    System.exit(1);
                }
                else if (str.charAt(i + 1) == ')'){
                        System.out.println("\n[ERROR] Operand appears before closing parenthesis\n]");
                        System.exit(1);
                    }
                prot[i] = new prot(3 + 3 * (counter_left - counter_right)) ;
                }
            
            else if (str.charAt(i)!= '.' ){
                try{
                    String str_local = "" + str.charAt(i);
                    Integer.parseInt(str_local);

                }catch(NumberFormatException e){
                      System.out.println("[ERROR] Invalid character\n");
                      System.exit(1);
                }
            }
        }
        // System.out.println(counter_left+ " "+ counter_right);
        if (counter_left > counter_right){
             System.out.println("[ERROR] Not closing opened parenthesis\n");
             System.exit(1);
         }
        //separate the initial string, in i position(lowest priority), to pass it as argument to recuirsive call // 
         current = new node();
         i = find_min(prot) ;
         current.operator = str.charAt(i);     
         left = left.substring(0,i);
         right = right.substring(i+1, right.length());

         counter_left = 0;
         counter_right = 0;
         current.left = build(left);
         current.right = build(right);
         return current ;
}
            
    // 1. returns the tree string in a suffix form. E.g Given the (9 + 5.5 ) x (33 - 28), we return  ((9)(5.5)+)((33)(28)-)x. //
    // 2. algorithm: we find the priorities with the same way as in build function.                                           // 
    // 3.The algorithm works as follow: e.g 10 + (2 + 3) * 5 + 10                                                             //
    // 4.                                         / |  \                                                                      //
    // 5.                                    start index end                                                                  //
    // 6. 1st iteration: left = (2), right = (3) => inserted = (2)(3)+                                                        //
    // 7. the modified string 10 + ((2)(3)+) * 5 + 10                                                                         //
    // 8.                          |         |  \                                                                             //
    // 9.                         start    index end                                                                          //
    // 10. 2nd iteration: left = ((2)(3)+) right = (5) iserted => (((2)(3)+)(5)*                                              //
    // 11. the modified string = 10 + (((2)(3)+)(5)* + 10                                                                     //
    // 12.                      |  |             |                                                                            //
    // 13.                   start index        end                                                                           //
    // 14. 3rd iteration: left = (10), right = ((((2)(3)+)(5)*), => inserted = (10)((((2)(3)+)(5)*)+                          //
    // 15. the modified string = (10)(((2)(3)+)(5)*)++10                                                                      //
    // 16.                                           |  \                                                                     //
    // 17                                          start end                                                                  //
    // 18.                              (due to the done function)                                                            //
    // 19. 4rd iteration left = (10)(((2)(3)+)(5)*)+ (due to str_done function), right = 10                                   //
    // 20. => inserted (= (10)(((2)(3)+)(5)*)+)(10)+                                                                          //
    public String toString(){                   
        int counter = 0 ;  // #operatos //
        int counter_left = 0;
        int counter_right = 0 ;
        int index ; //index of operator with highest priority. //
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();
        int local_counter_left = 0 ;
        int local_counter_right = 0 ;
        int z = 0 ;
        
        //Initially the returned str is the given one. //
        StringBuilder ret_str = new StringBuilder(this.str);    
        StringBuilder str_float = new StringBuilder("");
        StringBuilder inserted = new StringBuilder("");

        for (int i = 0; i < str.length(); i++){
            if (is_operate(str.charAt(i)) == 1){
                counter++;
            }
        }
        for(int j = 0 ; j < counter ; j++){
            inserted.delete(0,inserted.length());
            prot [] prot  = new prot[ret_str.length()];
            for(int i = 0 ; i < ret_str.length() ;i++){
               prot[i] = new prot(0) ;
               counter_left = counter_right = 0 ;
            }
            //find priotrities. //
            for (int i = 0; i < ret_str.length(); i++) {
                prot[i] = new prot(0);
                if (ret_str.charAt(i) == '(') {
                    counter_left++;
                } else if (ret_str.charAt(i) == ')') {
                    counter_right++;
                } else if (ret_str.charAt(i) == '+' || ret_str.charAt(i) == '-') {
                    if (ret_str.charAt(i-1) == ')'){
                        int k = 0;
                        //if we've alredy taken into account the operator. //
                        if (done(ret_str.toString(), i) == 1){
                            prot[i] = new prot(0);
                            continue;
                       }
                    }
                    prot[i] = new prot(1 + 3 * (counter_left - counter_right));
                } else if (ret_str.charAt(i) == 'x' || ret_str.charAt(i) == '*' || ret_str.charAt(i) == '/') {
                    if (ret_str.charAt(i-1) == ')'){
                        int k = 0;
                        if (done(ret_str.toString(), i) == 1){
                            prot[i] = new prot(0);
                            continue;
                        }
                    }
                    prot[i] = new prot(2 + 3 * (counter_left - counter_right));

                } else if (ret_str.charAt(i) == '^') {
                    if (ret_str.charAt(i-1) == ')'){
                        int k = 0;
                        if (done(ret_str.toString(), i) == 1){
                            prot[i] = new prot(0);
                            continue;
                        }
                    }
                    prot[i] = new prot(3 + 3 * (counter_left - counter_right));
                } 
            }
            index = find_max(prot);
            local_counter_left = 0 ;
            local_counter_right = 0;
            left = new StringBuilder("");
            right = new StringBuilder("");

            int start = 0 ;
            int end = 0; 
            //left string //
            if (index > 0){
                if (ret_str.charAt(index - 1) == ')'){
                    start = 0;
                    for (start = index - 1; start >= 0 ; start--){
                        left.append(ret_str.charAt(start));
                        if (ret_str.charAt(start) == ')'){
                            local_counter_right++;
                        }
                        if (ret_str.charAt(start) == '('){
                            local_counter_left++;
                            if (local_counter_left == local_counter_right){
                                start--;
                                left.reverse();
                                break;
                            }
                        }
                    }
                }
                //case      (3 + 5.5) x (33 - 28) //
                //           | |                  //
                //   <---start index              //
                // finally left : (3)             //
                else if (is_operate(ret_str.charAt(index - 1)) == 0){
                    start = index - 1;
                    while(is_num(ret_str.charAt(start)) == 1){
                        left.append(ret_str.charAt(start));
                        start--;
                        if (start < 0){
                            break ;
                        }
                     }

                    left = left.reverse();
                    left.insert(0,"(");
                    left.insert(left.length(),")");
                }
                
                if (is_operate(ret_str.charAt(index - 1)) == 1){
                    start = index - str_done(ret_str.toString(),index).length()-1;
                    left.append(str_done(ret_str.toString(),index));
                    left.insert(0,"(");
                    left.insert(left.length(),")");
                }
            }
            if (index < ret_str.length()-1){
                // 1.variable flag: for the reason that we may have the 10+((2)(3)+)(5)*+10 after some iterations//
                // 2.and the right string have to be equal to ((2)(3)+)(5) that is, we should not stop at the    //
                // 3.index where #'(' == #')'. Thus the flag consitutes a signal that the right string should    //
                // 4.be continued                                                                                //
                int flag = 0;
                if (ret_str.charAt(index + 1) == '('){
                    end = 0 ;
                    for (end = index + 1; end < ret_str.length(); end++){
                        right.append(ret_str.charAt(end));
                        if (ret_str.charAt(end) == '('){
                            local_counter_left++;
                        }
                        if (ret_str.charAt(end) == ')'){
                            local_counter_right++;
                            if (local_counter_left == local_counter_right){
                                if (end + 1 < ret_str.length()){
                                    if (ret_str.charAt(end + 1) == '('){
                                        flag = 1; 
                                        continue ;
                                    }
                                    else {
                                        if (flag == 1){
                                            right.append(ret_str.charAt(end + 1));
                                            right.insert(0,"(");
                                            right.insert(right.length(),")");
                                            end = end+2;
                                            break;
                                        }
                                        end = end + 1 ;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    end = index + 1;
                    while(is_num(ret_str.charAt(end)) == 1){
                        right.append(ret_str.charAt(end));
                        if (end >= ret_str.length() - 1){
                            end++;
                            break ;
                        }
                        end++;
                     }
                    right.insert(0,"(");
                    right.insert(right.length(),")");
                }
                inserted.append(left.toString() + right.toString() + ret_str.charAt(index));
                ret_str.insert(start + 1,inserted);
                ret_str.delete(start + 1 + inserted.length(), end + inserted.length());
            }
        }
        return ret_str.toString();
    }

    // 1.This function calculates the result of mathematic expression, we create an auxiliary function "to_calculate" because //
    // 2.this calculation need to be recuirsive                                                                               //
    public double calculate(){
      return to_calculate(this.root) ;
    }
    // 1. we recuirsively caclulate the result. When we reach the leaves we make the calculation betwwen the two leaves and  //
    // 2. we return this to the head of the tree, then recuirsively make the calculation and return and so on until we reach //
    // 3. the head of the whole tree.                                                                                        //
    private double to_calculate(node head){
        char operate ;
        double left ;
        double right ;
        
        operate = head.operator ;
        
        if (head.left == null && head.right == null){
            return  Double.parseDouble(head.cur_float) ;
        }
        else 
        {
            left = to_calculate(head.left);
            right =   to_calculate(head.right);
           //  System.out.println("LEFT RIGHT OPERATE"+ left + " " + head.operator + " " + right );
            if (head.operator == '+'){
                return left + right ;
            }
            if (head.operator == '-'){
                return left - right ;
            }
            if (head.operator == '*' || head.operator == 'x' ){
                return left * right ;
            }
            if (head.operator == '/'){
                return left / right ;
            }
            if (head.operator == '^'){
               return pow(left, right) ;
           }

        }
        return 0 ;

    }
    // If a char represents an operator, 1 is returned otherwise 0. //
    int is_operate(char c){
        if (c== '+' || c == '-'  || c == 'x' || c == '*' || c == '/'|| c == '^' ){
            return 1 ;
        }
        else return 0 ;
    }
        // If a char represents a number or a '.'(for floats ), 1 is returned otherwise 0. //
    int is_num(char c){
        if (c == '0' || c == '1' ||c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c== '8' || c == '9' || c == '.'){
            return 1;
        }
        else return 0 ;

    }
    // 1.done function is called by tostring function in order to distinguish the operators which have been already taken into account //
    // 2.for example if in one iteration we have the string (10)(((2)(3)+)(5)*)++10, we don't want to take into account again the first//
    // 3.+ operator 
    int done(String str, int i){
        int k ;
        int counter_left = 0;
        int counter_right = 0 ;
         for (k = i - 1; k > 0; k--){
            if (str.charAt(k) == ')'){
                counter_right++;
            }
            if (str.charAt(k) == '('){
                counter_left++;
                if (counter_left == counter_right){
                    if (str.charAt(k-1)==')'){
                        return 1;
                    }
                    else return 0 ;
                }
            }
       }
        return 0 ;
    }
    // return the substring which is already in suffix from. E.g 10 + (2+3)(5)*<--(i), this will return (2+3)(5).//
    String str_done(String str, int i){
        int k ;
        int counter_left = 0;
        int counter_right = 0 ;
        StringBuilder ret_str = new StringBuilder(str.charAt(i)); 
         for (k = i-1 ; k >= 0 ; k--){
            ret_str.append(str.charAt(k));
            if (str.charAt(k) == ')'){
                counter_right++;
            }
            if (str.charAt(k) == '('){
                counter_left++;
                if (counter_left == counter_right){
                    if (k > 0){
                        if (str.charAt(k - 1) == ')'){
                            continue;
                        }
                    }
                    ret_str.reverse();
                    return ret_str.toString();
                }
            }
         }
         return ret_str.toString() ;
       }
    // This function finds the index of highest priority. //
    public int find_max(prot []prot){
        int r ;
            int index = 0;
            r = 0;
            for(int i = 0 ; i < prot.length ; i++){
                if (prot[i].proter > r){
                    index = i ;
                    r = prot[i].proter;
                }
            }
            return index ;
    }    
    // This function finds the index of lowest priority. //
    public int find_min(prot []prot){
        int r ;
        int index = 0;
        r = 1000;
        for(int i = 0 ; i < prot.length ; i++){
            if (prot[i].proter <= r){
                index = i ;
                r = prot[i].proter;
            }
        }
        return index ;
    }
    // 1.In order to create the dot file from stracture of tree we need to create a recuirsive function "for_string".//
    String toDotString(){
        StringBuilder ret = new StringBuilder();
        node current = this.root ;
        ret.append("digraph ArithmeticExpressionTree { \nfontcolor=\"navy\";\nfontsize=20;\nlabelloc=\"t\"; \nlabel=\"Arithmetic Expression\"\n" ) ; 
        ret.append(for_string(this.root) + "}");
        return ret.toString();


    }
    
    // 1.This recuirsive function is called initially with the root node, in each call is called with the childs of the node //
    // 2.When we reach the leaves we return. In each call it adds to the existing string, the returned sting of its children //
    String for_string(node current){
        StringBuilder ret = new StringBuilder();
        if (current.left == null && current.right == null){
            ret.append(current.hashCode()+ "[label=\""+current.cur_float+"\", "+ "shape=circle, color=black]\n");
            return ret.toString();
        }
        if (current.left != null){
            ret.append(current.hashCode()+ "[label=\""+current.operator+"\", "+ "shape=circle, color=black]\n");
            ret.append(current.hashCode()+ " -> "+ current.left.hashCode()+"\n");
            ret.append(for_string(current.left));
        }

        if (current.right != null){
            ret.append(current.hashCode()+ "[label=\""+current.operator+"\", "+ "shape=circle, color=black]\n");
            ret.append(current.hashCode()+ " -> "+ current.right.hashCode()+"\n");

            ret.append(for_string(current.right));
        }
        return ret.toString() ;
    }
   
}