/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Philip Cardozo
*/

import java.util.ArrayList;

public class BusRoute {
    private final String name;
    private BusStop start;

    public BusRoute(String name){
        this.name = name;
        start = null;
    }

    public String getName(){ return name; }
    public BusStop getStart(){ return start; }


    //adds a new BusStop with the given name to the end of the route (that is, just before the route circles back to start)

    public void addStop(String name) {
        BusStop newStop = new BusStop(name, this.name, null, null);
        if (start == null) {
            start = newStop;
            start.setNext(start);
            start.setPrevious(start);
            return;
        }
        BusStop last = start.getPrevious();
        last.setNext(newStop);
        newStop.setPrevious(last);
        newStop.setNext(start);
        start.setPrevious(newStop);
    }

    
    //adds a new BusStop with the given name to the route just after the stop with the given name prevStop;
    // if the given previous stop is not in the route the new stop is not added
    public void addStop(String name, String prevStop) {
        if (start == null) return;
        BusStop current = start;
        do {
            if (current.getName().equals(prevStop)) {
                BusStop next = current.getNext();
                BusStop newStop = new BusStop(name, this.name, current, next);
                current.setNext(newStop);
                next.setPrevious(newStop);
                return;
            }
            current = current.getNext();
        } while (current != start);
    }
    
//removes from the route the BusStop with the given name;
    public BusStop removeStop(String name) {
        if (start == null) return null;
        BusStop current = start;
        do {
            if (current.getName().equals(name)) {
                if (current == start && current.getNext() == start) {
                    start = null;
                    return current;
                }
                if (current == start) start = current.getNext();
                current.getPrevious().setNext(current.getNext());
                current.getNext().setPrevious(current.getPrevious());
                return current;
            }
            current = current.getNext();
        } while (current != start);
        return null; //  returns null if the route is empty or the given stop does not exist in the route
    }
// returns the BusStop immediately following the BusStop with the given name;
    public BusStop getNextStop(String stopName) {
        if (start == null) return null;
        BusStop current = start;
        do {
            if (current.getName().equals(stopName)) return current.getNext();
            current = current.getNext();
        } while (current != start);
        return null;  // returns null if the route is empty or the given stop does not exist in the route
    }

    
    public boolean stopsAt(String stopName) {
        if (start == null) return false; // returns false if the route is empty or the route does not stop there
        BusStop current = start;
        do {
            if (current.getName().equals(stopName)) return true; // returns true if the route stops at the BusStop with the given name; 
            current = current.getNext();
        } while (current != start);
        return false; 
    }

    //returns an ArrayList of the names of the stops that the calling object and given otherRoute have in common,
//i.e. stops where someone could transfer routes; returns an empty ArrayList if either route is
//empty or the routes have no stops in common.

    public ArrayList<String> getTransferPoints(BusRoute otherRoute) {
        ArrayList<String> shared = new ArrayList<>();
        if (this.start == null || otherRoute.start == null) return shared;
        BusStop current = this.start;
        do {
            if (otherRoute.stopsAt(current.getName()) && !shared.contains(current.getName())) {
                shared.add(current.getName());
            }
            current = current.getNext();
        } while (current != this.start);
        return shared;
    }

    @Override
    public String toString() {
        return "Bus Route " + name;
    }

    public void displayRoute(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        } else {
            res += start.toString();
            BusStop c = start.getNext();
            while (c != start) {
                res += " -> " + c.toString();
                c = c.getNext();
            }
        }
        System.out.println(res);
    }
// Displays the route backwards from the stop *before start* back to start ... → 3rd → 2nd → 1st → start
    public void displayRouteBackwards(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        } else {
            res += start.toString();
            BusStop c = start.getPrevious();
            while (c != start) {
                res += " -> " + c.toString();
                c = c.getPrevious();
            }
        }
        System.out.println(res);
    }
}
