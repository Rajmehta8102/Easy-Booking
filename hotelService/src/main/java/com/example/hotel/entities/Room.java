package com.example.hotel.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class  Room {
    @Id
    private String id;
    private int roomType;
    @NotNull(message = "Please Enter Valid room number")
    private String roomNumber;
    private int price;
    @ManyToOne
    @JoinColumn(name = "hotelId")
    @JsonBackReference
    private Hotel hotel;
}
