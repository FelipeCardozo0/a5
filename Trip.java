/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Philip Cardozo
*/

public class Trip {
    private BusStop start;

    public Trip(){
        this.start = null;
    }

    public BusStop getStart(){ return start; }

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
