package model;

import exception.ParkLotFullException;
import exception.VehicleNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParkingLot {
    private final int totalParkingSlots;
    private int currentSize=0;
    private PriorityQueue<Integer> vaccantParkingSlotQueue;
    private static volatile ParkingLot parkingLot;
    private List<Ticket> tickets;
    private ParkingLot(int totalParkingSlots){
        this.totalParkingSlots = totalParkingSlots;
        vaccantParkingSlotQueue = new PriorityQueue<>(totalParkingSlots);
        tickets = new ArrayList<>(totalParkingSlots);
        for(int i=1; i<=totalParkingSlots; i++){
            vaccantParkingSlotQueue.add(i);
        }
    }

    public static ParkingLot getParkingLotInstance(int totalParkingSlots){
        if(parkingLot==null){
            synchronized (ParkingLot.class){
                if(parkingLot==null){
                    parkingLot = new ParkingLot(totalParkingSlots);
                }
            }
        }
        return parkingLot;
    }
    public Ticket allocateSlot(String vehicleRegNumber) throws ParkLotFullException {
        if(currentSize>=totalParkingSlots){
            throw new ParkLotFullException();
        }
        int slot = vaccantParkingSlotQueue.remove();
        Ticket ticket = new Ticket();
        ticket.setParkingSlotNumber(slot);
        ticket.setRegistrationNumber(vehicleRegNumber);
        tickets.add(ticket);
        currentSize++;
        return ticket;
    }

    public Ticket vacateSlot(String vehicleRegNumber, int parkingHours) throws VehicleNotFoundException {
        Ticket ticket = new Ticket();
        ticket.setRegistrationNumber(vehicleRegNumber);
        Ticket originalTicket = null;
        int index = tickets.indexOf(ticket);
        if(index==-1){
            throw new VehicleNotFoundException("Registration number "+vehicleRegNumber +" not found ");
        }
        originalTicket = tickets.get(index);
        originalTicket.setTotalHours(parkingHours);
        originalTicket.setParkingCharge(calculateCharge(originalTicket));
        tickets.remove(originalTicket);
        vaccantParkingSlotQueue.add(originalTicket.getParkingSlotNumber());
        currentSize=currentSize-1;
        return originalTicket;
    }

    public void parkingStatus(List<Ticket>tickets){
        System.out.println("Slot No.     "+"    Registration No.");
        if(tickets!=null && !tickets.isEmpty()){
           for(Ticket ticket : tickets){
               System.out.println(ticket.getParkingSlotNumber()+"    "+ticket.getRegistrationNumber());
           }
        }
    }
    private int calculateCharge(Ticket ticket){
        int fixedHours = 2;
        int charge = 10 + (10 * ((ticket.getTotalHours()>fixedHours?ticket.getTotalHours(): fixedHours)-fixedHours));
        return charge;
    }

    public List<Ticket> getTickets() {
        Comparator<Ticket> comp = (t1, t2)-> t1.getParkingSlotNumber()-(t2.getParkingSlotNumber());
        Stream<Ticket> stream = this.tickets.stream();
        return stream.sorted(comp).collect(Collectors.toList());
    }

    public int getCurrentSize() {
        return currentSize;
    }
}