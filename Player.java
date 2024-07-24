import java.util.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private ArrayList<Objeto> inventario;
    private Room currentRoom;
    private Stack<Room> recorrido;
    
    public Player(Room currentRoom){
        inventario = new ArrayList();
        this.currentRoom = currentRoom;
        recorrido = new Stack();
    }
    
    public void setCurrentRoom(Room room){
        currentRoom = room;    
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
    
    public void takeItem(Objeto objeto){
        inventario.add(objeto);
    }
    
    public void dropItem(Objeto objeto){
        inventario.remove(objeto);
    }
    
    public void agregarAlRecorrido(Room room){
        recorrido.push(room);
    }
    
    public void eliminarDelRecorrido(){
        recorrido.pop();
    }
    
    public int sizeRecorrido(){
        return recorrido.size();
    }
    
    public Room peekRecorrido(){
        return recorrido.peek();
    }
    
    public String getInventory(){
        if (inventario.size() == 0) return "No tienes objetos";
        String inventoryString = "Tus objetos son: ";
        for (Objeto objeto : inventario){
            inventoryString += objeto.getName() + ", ";
        }
        return inventoryString;
    }
}
