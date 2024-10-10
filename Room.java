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
    private ArrayList<Objeto> objetos;
    private ArrayList<Objeto> paraPasar;
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
        paraPasar = new ArrayList();
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
    
    public void addObjeto(Objeto objeto){
        objetos.add(objeto);
    }
    
    public void eliminarObjeto(Objeto objeto){
        objetos.remove(objeto);
    }
    
    public ArrayList<Objeto> clonarListaObjetos(){
        ArrayList<Objeto> objetosDuplicado = (ArrayList<Objeto>) objetos.clone();
        return objetosDuplicado;
    }
    
    public String getNameObjetos(){
        String objetosString = "Hay " + objetos.size() + " objetos: ";
        for (Objeto objeto : objetos){
            objetosString += objeto.getName() + " ";
        }
        return objetosString;
    }
    
    public ArrayList<Objeto> getObjetos(){
        return objetos;
    }
    
    public void addParaPasar(Objeto objeto){
        paraPasar.add(objeto);
    }
    
    public ArrayList<Objeto> getParaPasar(){
        return paraPasar;
    }
    
    public boolean pertenece(ArrayList<Objeto> lista, Objeto objeto){
        for (int i = 0; i < lista.size(); i++){
            if (objeto.getName().equals(lista.get(i).getName())){
                return true;
            }
        }
        return false;
    }
    
    private boolean contienenLoMismo(ArrayList<Objeto> lista, ArrayList<Objeto> lista2){
        if (lista.size() != lista2.size()) return false;
        boolean contienenLoMismo = false;
        boolean buscando = true;
        int i = 0;
        int ii = 0;
        while (i < lista.size()){
            while (ii < lista.size()){
                if (lista.get(i).getName().equals(lista2.get(ii).getName())){
                    contienenLoMismo = true;
                }
                else {
                    contienenLoMismo = false;
                }    
                ii++;
            }
            i++;
        }
        return contienenLoMismo;    
    }
    
    public boolean puedePasar(){
        if (paraPasar.size() == 0) {
            return true;
        } else{
            boolean pasar = false;
            for (Objeto objeto : objetos){
                if (pertenece(paraPasar, objeto)){
                    pasar = true;
                }
            }
            return pasar;
        }
    }
}
