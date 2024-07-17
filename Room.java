import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<String> objetos;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        objetos = new ArrayList();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Retorna la salida correspondiente a la direccion especificada.
     */
    public Room getExit(String direccion){
        return exits.get(direccion);
    }
    
    public String getExitString(){
        String salidas = "Salidas: ";
        Set<String> claves = exits.keySet();
        for (String exit : claves){
            salidas = salidas + exit + " ";    
        }
        return salidas;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    public String getLongDescription(){
        return "Te encuentras en " + description + ".\n" + getExitString();
    }
    
    public void addObjeto(String objeto){
        objetos.add(objeto);
    }
    
    public void eliminarObjetos(){
        objetos.clear();
    }
    
    public ArrayList<String> clonarListaObjetos(){
        ArrayList<String> objetosDuplicado = (ArrayList<String>) objetos.clone();
        return objetosDuplicado;
    }
    
    public String getObjetos(){
        String objetosString = "Hay " + objetos.size() + " objetos: ";
        for (String objeto : objetos){
            objetosString += objeto + " ";
        }
        return objetosString;
    }

}
