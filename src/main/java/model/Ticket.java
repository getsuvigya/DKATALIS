package model;

import java.util.Objects;

public class Ticket{
    private String registrationNumber;
    private int totalHours;
    private int parkingSlotNumber;
    private int parkingCharge;
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getParkingSlotNumber() {
        return parkingSlotNumber;
    }

    public void setParkingSlotNumber(int parkingSlotNumber) {
        this.parkingSlotNumber = parkingSlotNumber;
    }

    public int getParkingCharge() {
        return parkingCharge;
    }

    public void setParkingCharge(int parkingCharge) {
        this.parkingCharge = parkingCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
			return true;
        if (o == null || getClass() != o.getClass()) 
			return false;
        Ticket ticket = (Ticket) o;
        return registrationNumber.equals(ticket.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
