package DataStructures;


import ADTs.ListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidArgumentException;
import java.util.Arrays;
import Apps.ConcertTicket;

/**
 *
 * @author ITSC 2214
 */
public class MyArrayList <T> implements ListADT<T>{

    final static int INIT_CAPACITY = 10;
    T items[];
    int size;
    
    /**
     * Constructor
     */
    public MyArrayList(){
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
    }
    
    /**
     * Constructor
     * @param size
     */
    public MyArrayList(int size){
        items = (T[]) new Object[size];
        size = 0;
        
    }

    /**
     * Obtain the element at the given index of the array
     * @param index
     * @return element
     */
    @Override
    public T get(int index) {
        if (index < 0 || index > items.length) return null;
        
        return items[index];
    }
    
    /**
     * Place a given new item (parameter variable called newitem) at 
     * the given index of the array
     * @param index, newitem
     * @return void
     */
    @Override
    public void set(int index, T newitem){
        if (index < 0 || index > items.length) return;
        if (isEmpty()){
            return;
        }
        items[index] = newitem;
    }

    /**
     * Insert a given new item (parameter variable called value) at the given index
     * of the array
     * @param index, value
     * @return void
     */
    public void add(int index, T value){
        if (value == null) return;

        if (size == items.length)
            expandAndCopyArray();
        
        for (int i = size-1; i >= index; i--)
            items[i+1] = items[i];
        
        items[index] = value;
        size++;
    }
    
    /**
     * Append a given new item (parameter variable called value) to the array
     * @param value
     * @return void
     */
    public void add(T value){
        add(size, value);
    }
    /**
     * A Java built-in approach to expand the existing array with a new array 
     * of double size.
     */
    private void resize() {
        this.items = Arrays.copyOf(this.items, 2 * this.items.length);
    }
    
    /**
     * Expand the existing array with a new array of double size.
     * It is implemented by making a new array of doubling the size of the old array
     * and copy every element in the old array to the new array.
     */
    private void expandAndCopyArray(){
        if (items == null) return ;
        int resize = (int)(items.length * 2);
        T[] list = (T[]) new Object[resize];
        for (int index = 0; index < resize; index++)
            if (index < items.length)
                list[index] = items[index];
            else
                list[index] = (T) new Object();
        
        this.items = list;
    }

    /**
     * Remove element at the given index of the array
     * @param the index of the to-be-removed element 
     * @return T
     */
    public T remove(int index){
         if (index < 0 || index >= size)
             return null;

         //move removed index to last element of array
            for (int i = index; i < this.size - 1; i++) {
                T temp = items[i];
                T swap = items[i + 1];
                items[i] = swap;
                items[i + 1] = temp;
            }
            T removedItem = items[this.size - 1];
            //create a copy of items array which does not include last element
            T[] newArray = (T[]) new Object[this.size - 1];
            for(int i = 0; i < this.size - 1; i++) {
                newArray[i] = items[i];
            }
            this.items = newArray;
            this.size = newArray.length;
            return removedItem;
       }

    /**
     * Obtain the number of elements in the array
     * @return 
     */
    public int size(){
        return size;
    }

    /**
     * Append the given element to the first of the array
     * @param element
     * @return void
     */
    @Override
    public void addFirst(T element){
        try{
            this.add(0, element);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Append the given element to the end of the array
     * @param element
     * @return void
     */
    @Override
    public void addLast(T element) {
        try{
            this.add(size, element);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove the element at the first index of the array
     * @return T
     * @throws EmptyCollectionException 
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty collection error when invoking removeFirst()");
        try{
            return this.remove(0);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new EmptyCollectionException("Empty Collection");
    }

    /**
     * Remove the element at the last index of the array
     * @return T
     * @throws EmptyCollectionException 
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty collection error when invoking removeLast()");
        try{
            return this.remove(size-1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        throw new EmptyCollectionException("Empty Collection");
    }

    /**
     * Retrieve the element at the first index of the array
     * @return T
     * @throws EmptyCollectionException 
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty collection error when invoking first()");
        return this.items[0];
    }

    /**
     * Retrieve the element at the last index of the array
     * @return T
     * @throws EmptyCollectionException 
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty collection error when invoking last()");
        return this.items[size-1];
    }

    /**
     * Evaluate whether the given element is in the array
     * @param element
     * @return true or false
     * @throws EmptyCollectionException 
     */
    @Override
    public boolean contains(T element) throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("Empty collection error when invoking contains()");

        for (int i = 0; i < size; i++) {
            if (items[i] == null && element == null) {
                return true;
            } else if (items[i] != null && items[i].equals(element)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Search for the given element in the array
     * @param element
     * @return 
     * @throws EmptyCollectionException, ElementNotFoundException, InvalidArgumentException
     */
    public int find(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) 
            throw new EmptyCollectionException("Empty collection error when invoking find()");
        
        if (element == null) 
            return -1;
        
        for (int i = 0; i < size; i++)
            if (element == this.items[i])
                return i;
        
        throw new ElementNotFoundException("Element not found when invoking find()");
    }
    
    /**
     * Evaluate whether the array is empty or not
     * @return true or false
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }
    
    /**
     * Search for the  in the array
     * @param element
     * @return true or false
     * @throws ElementNotFoundException, EmptyCollectionException 
     */
    @Override
    public void addAfter(T existing, T element) throws ElementNotFoundException, EmptyCollectionException{
        if (existing == null) return;
        
        int index = find(existing);
        try{
            add(index+1, element);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (element == null) return null;
        
        int index = find(element);
        try{
            return remove(index);   
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        throw new ElementNotFoundException("Element not found");
    }
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyArrayList list = new MyArrayList<ConcertTicket>();
        try{
            ConcertTicket ticket = new ConcertTicket();
            list.add(ticket);

            for (int i = 0; i < list.size(); i++)
                System.out.println(list.get(i));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
