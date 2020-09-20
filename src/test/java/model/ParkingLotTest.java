package model;

import exception.ParkLotFullException;
import exception.VehicleNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParkingLotTest {
    ParkingLot parkingLot;
    Ticket ticket;
	
    @Before
    public void setUp() throws Exception {
        parkingLot = ParkingLot.getParkingLotInstance(3);
        ticket = parkingLot.allocateSlot("ABCD");
    }

    @After
    public void tearDown() throws Exception {
        ticket = parkingLot.vacateSlot("ABCD", 2);
    }

    @Test
    public void getParkingLotInstance() {
        ParkingLot newParkingLot = ParkingLot.getParkingLotInstance(3);
        assertTrue(parkingLot==newParkingLot);
    }

    @Test
    public void allocateSlot() throws ParkLotFullException {
        assertTrue(ticket.getParkingSlotNumber()==1);
        assertTrue(parkingLot.getCurrentSize()==parkingLot.getTickets().size());

    }

    @Test
    public void allocateSlotParkLotFullException() throws ParkLotFullException, VehicleNotFoundException {
        parkingLot.allocateSlot("PQR");
        parkingLot.allocateSlot("XYZ");
        assertTrue(parkingLot.getCurrentSize()==parkingLot.getTickets().size());
        try {
            parkingLot.allocateSlot("lmn");
        }catch (ParkLotFullException e){
            assertTrue(e.getUserMsg().contains("Sorry, parking lot is full"));
        }
        parkingLot.vacateSlot("PQR",1);
        parkingLot.vacateSlot("XYZ", 2);
    }

    @Test
    public void vacateSlot() throws VehicleNotFoundException, ParkLotFullException {
        parkingLot.allocateSlot("XYZ");
        Ticket ticket = parkingLot.vacateSlot("XYZ",3);
        assertTrue(ticket.getParkingSlotNumber()==2);
        assertTrue(ticket.getRegistrationNumber().equals("XYZ"));
        assertTrue(ticket.getTotalHours()==3);
        assertTrue(ticket.getParkingCharge()==20);
    }

    @Test(expected = VehicleNotFoundException.class)
    public void vacateSlotException() throws VehicleNotFoundException, ParkLotFullException {
        Ticket ticket = parkingLot.vacateSlot("XYZ",2);
    }
    @Test
    public void getTickets() {
        List<Ticket> tickets = parkingLot.getTickets();
        assertTrue(tickets.size()==1);
    }
}