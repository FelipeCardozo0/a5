/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Philip Cardozo
*/

import java.io.*;
import java.util.*;

public class TripPlanner {
    private final ArrayList<BusRoute> routes;
    private final String filePath;
    private String start;
    private String end;
    private ArrayList<BusRoute> routesStoppingAtStart;
    private ArrayList<BusRoute> routesStoppingAtEnd;
    private Trip trip;

    public TripPlanner(String filePath){
        this.filePath = filePath;
        routes = new ArrayList<BusRoute>();
        routes.add(buildRoute("Loop"));
        routes.add(buildRoute("C"));
        routes.add(buildRoute("A"));
        routes.add(buildRoute("M"));
        routes.add(buildRoute("E"));
        routes.add(buildRoute("D"));
    }

    public BusRoute buildRoute(String name){
        BusRoute r = new BusRoute(name);
        Scanner scanner;
        try{
            scanner = new Scanner(new FileInputStream(filePath+name + ".txt"));
        } catch(IOException ex){
            System.err.println("*** Invalid filename: " + filePath + name + ".txt");
            return null;
        }
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            r.addStop(line);
        }
        return r;
    }

    public void setStart(String start){ this.start = start; }
    public void setEnd(String end){ this.end = end; }
    public ArrayList<BusRoute> getRoutesStoppingAtStart(){ return routesStoppingAtStart; }
    public ArrayList<BusRoute> getRoutesStoppingAtEnd(){ return routesStoppingAtEnd; }
    public Trip getTrip(){ return trip; }

    public boolean verifyInput(){
        routesStoppingAtStart = getRoutesStoppingAt(start);
        routesStoppingAtEnd = getRoutesStoppingAt(end);
        if(routesStoppingAtStart.isEmpty()){
            System.out.println("No routes found stopping at start location!");
            return false;
        }
        if(routesStoppingAtEnd.isEmpty()){
            System.out.println("No routes found stopping at end location!");
            return false;
        }
        return true;
    }

    public void displayTrip(){ trip.displayTrip(); }

    public ArrayList<BusRoute> getRoutesStoppingAt(String stopName) {
        ArrayList<BusRoute> result = new ArrayList<>();
        for (BusRoute route : routes) {
            if (route.stopsAt(stopName)) result.add(route);
        }
        return result;
    }

    public void buildTrip() {
        trip = new Trip();
        for (BusRoute route : routesStoppingAtStart) {
            if (route.stopsAt(end)) {
                BusStop current = route.getStart();
                boolean started = false;
                do {
                    if (current.getName().equals(start)) started = true;
                    if (started) trip.addStop(current.getName(), route.getName());
                    if (started && current.getName().equals(end)) return;
                    current = current.getNext();
                } while (current != route.getStart());
            }
        }
        for (BusRoute r1 : routesStoppingAtStart) {
            for (BusRoute r2 : routesStoppingAtEnd) {
                ArrayList<String> transfers = r1.getTransferPoints(r2);
                if (!transfers.isEmpty()) {
                    String transfer = transfers.get(0);
                    BusStop current = r1.getStart();
                    while (!current.getName().equals(transfer)) {
                        if (current.getName().equals(start) || trip.getStart() != null)
                            trip.addStop(current.getName(), r1.getName());
                        current = current.getNext();
                    }
                    trip.addStop(transfer, r1.getName());
                    trip.addStop(transfer, r2.getName());
                    current = r2.getStart();
                    boolean found = false;
                    while (!current.getName().equals(end)) {
                        if (found) trip.addStop(current.getName(), r2.getName());
                        if (current.getName().equals(transfer)) found = true;
                        current = current.getNext();
                    }
                    trip.addStop(end, r2.getName());
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        TripPlanner tp = new TripPlanner("src/");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Emory Trip Planner!");
        System.out.print("Enter your starting location: ");
        tp.setStart(scanner.nextLine());
        System.out.print("Enter your ending location: ");
        tp.setEnd(scanner.nextLine());
        if (!tp.verifyInput()) return;
        System.out.println("Routes stopping at start location: " + tp.getRoutesStoppingAtStart());
        System.out.println("Routes stopping at end location: " + tp.getRoutesStoppingAtEnd());
        tp.buildTrip();
        System.out.println("Your Trip:");
        tp.displayTrip();
    }
}
