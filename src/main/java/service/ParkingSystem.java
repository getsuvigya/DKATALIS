package service;

import exception.ParkLotFullException;
import exception.VehicleNotFoundException;
import model.ParkingLot;
import model.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParkingSystem {
	
    private ParkingLot parkingLot;
	
    private void readParkingSystemFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String commandLine = scanner.nextLine();
                String[] commandLineArray = commandLine.split(" ");
                if(commandLineArray.length>0){
                    try {
                        executeParkingSystem(commandLineArray);
                    }catch (VehicleNotFoundException e){
                        System.out.println(e.getUserMsg());
                    }catch (ParkLotFullException e) {
                        System.out.println(e.getUserMsg());
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new ParkingSystem().readParkingSystemFile(args[0]);
    }

    private void executeParkingSystem(String[] parkingSystemInput) throws VehicleNotFoundException, ParkLotFullException {
        switch (parkingSystemInput[0]) {
            case "create_parking_lot":
                parkingLot = ParkingLot.getParkingLotInstance(Integer.parseInt(parkingSystemInput[1]));
                break;
            case "park":
                Ticket ticket = null;
                    ticket = parkingLot.allocateSlot(parkingSystemInput[1]);
                    System.out.println("Allocated slot number: "+ticket.getParkingSlotNumber());

                break;
            case "status":
                parkingLot.parkingStatus(parkingLot.getTickets());
                break;
            case "leave":
               Ticket leaveTicket =  parkingLot.vacateSlot(parkingSystemInput[1],Integer.parseInt(parkingSystemInput[2]));
                System.out.println("Registration number "+leaveTicket.getRegistrationNumber()+" with Slot Number "+leaveTicket.getParkingSlotNumber()+" is free with Charge "+leaveTicket.getParkingCharge());
                break;
            default:
                System.out.println("File is not in the proper format.");
        }

    }
}
