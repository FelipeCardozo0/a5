/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Philip Cardozo
*/

// class representing a user trip as a doubly-linked list
public class Trip {
    private BusStop start; // starting stop of the trip

    public Trip(){
        this.start = null;
    }

    // returns the start of the trip
    public BusStop getStart(){ return start; }

    // adds the stop with given name on the given route to the end of the existing trip
    public void addStop(String stopName, String routeName) {
        BusStop newStop = new BusStop(stopName, routeName, null, null);
        if (start == null) {
            start = newStop;
            return;
        }
        BusStop curr = start;
        while (curr.getNext() != null) curr = curr.getNext();
        curr.setNext(newStop);
        newStop.setPrevious(curr);
    }

    // removes from the trip the stop with given name on the given route;  
    // returns null if the route is empty or the given stop on the given route does not exist in the trip
    public BusStop removeStop(String stopName, String routeName) {
        if (start == null) return null;
        BusStop curr = start;
        while (curr != null) {
            if (curr.getName().equals(stopName) && curr.getRoute().equals(routeName)) {
                if (curr == start) start = curr.getNext();
                if (curr.getPrevious() != null) curr.getPrevious().setNext(curr.getNext());
                if (curr.getNext() != null) curr.getNext().setPrevious(curr.getPrevious());
                return curr;
            }
            curr = curr.getNext();
        }
        return null;
    }

    // prints to System.out the sequence of stops in the trip, beginning at start
    public void displayTrip(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        } else {
            res += start.toString();
            BusStop c = start.getNext();
            while (c != null) {
                res += "\n " + c.toString();
                c = c.getNext();
            }
        }
        System.out.println(res);
    }

    // prints the sequence of stops in the trip but in reverse order, beginning at the last stop and ending at start
    public void displayTripBackwards(){
        String res = "";
        if(start == null){
            res += "[NULL]";
        } else {
            BusStop c = start;
            while(c.getNext() != null){
                c = c.getNext();
            }
            while (c != null) {
                res += "\n " + c.toString();
                c = c.getPrevious();
            }
        }
        System.out.println(res);
    }
}
