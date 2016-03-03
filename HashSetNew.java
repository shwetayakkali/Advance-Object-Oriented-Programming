
/* 
 * HashSetNew.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.util.*;
/**
 * HashSetNew is our implementation of the inbuilt Hashset. We override the add(),contains(),isEmpty(),size(),remove(),clear() methods.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class HashSetNew extends HashSet {
    static Object hashsetarray[]=new Object[20050];
    
    static int indexno=0;
    static int count=0;
    
    /**
     * The add() is the overridden method where elements are added into the hashsetarray [].
     * @param item      Object
     * @return boolean
     */
    
    public boolean add(Object item){
        
        boolean flag=true;
        
        if(isEmpty()){
            
            hashsetarray[indexno]=item;
            indexno++;
            
            flag=true;
        }
        else{
            
            here:for(int i=0;i<indexno;i++){
                
                if(hashsetarray[i]==item){
                    flag=false;
                    break here;
                }
            }
            
            if(flag==true){
                
                hashsetarray[indexno]=item;
                indexno++;
                flag=true;
                
                }
        }
        
        return flag;
    }
    /**
     * The isEmpty() is the overridden method where we check if the hashsetarray is empty or not.
     * @return boolean
     */
    public boolean isEmpty(){
        
        if(indexno==0)
            return true;
        return false;
    
    }
    
    /**
     * The size() is the overridden method where current size of the array is checked.
     * @return int
     */
    public int size(){
        return indexno;
    
    }
    
    /**
     * The clear() is the overridden method where we clear the hashsetarray and reset the index.
     * @return int
     */
    public void clear(){
        
        for(int i=0;i<indexno;i++)
            hashsetarray[i]=null;
        
        indexno=0;
        
    }
    
    /**
     * The contains() is the overridden method where we check if the given item is present in the hashsetarray or not.
     * @param item Object
     * @return boolean
     */
    public boolean contains(Object item){
        
        for(int i=0;i<size();i++){
                
                if(hashsetarray[i]==item){
                    
                    return true;
                }
            }
    
        return false;
    
    }
    
    /**
     * The remove() is the overridden method where we remove the particular object and decrement the size by 1.
     * @param item Object
     * @return boolean
     */
    public boolean remove(Object item){
        int index=0;
        for( int i=0;i<size();i++){
            if(hashsetarray[i]==item){
                index=i;
                break;
            }
        }
        for(int i=index;i<size()-1;i++){
            hashsetarray[i]=hashsetarray[i+1];
        
        }
        indexno=indexno-1;
        
        return true;
    }
    
    /**
     * The iterator() is the overridden method where we iterate over the hashsetarray.
     * @return Iterator
     */
    public Iterator iterator(){
        
        return new MyIterator();
    
    }
    
   /**
    * MyIterator is our implementation of inbuilt Iterator. We override the remove(),hasNext(),next() methods.
    *
    * @author Shweta Yakkali
    * @author Aishwarya Desai
    */
    class MyIterator implements Iterator{
        
        int itervar=0;
       /**
        * The remove() is the overridden method.
        * 
        */
        public void remove()
        {}
        
       /**
        * The hasNext() is the overridden method wherein we check if the list is still traversable.
        * @return boolean
        */
        public boolean hasNext(){
            
            boolean flag=false;
            if(itervar<hashsetarray.length){
                flag=true;
            }
            else{
                flag=false;
            }
            return flag;
            
        }
        
       /**
        * The next() is the overridden method wherein we return the next item by incrementing the counter.
        * @return boolean
        */
        public Object next(){
            
            int i=itervar;
            itervar++;
            return hashsetarray[i];
        
        }
        
        
    
    }
    
    
    
    
    
        

}
