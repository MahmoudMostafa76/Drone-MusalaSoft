package com.musalasoft.drone.drone.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.musalasoft.drone.drone.enumerations.DroneState;
import com.musalasoft.drone.drone.enumerations.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "DRONE")
@Data
@Schema(description = "Details about the Drone")
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JoinColumn(name = "drone_id")
  @Schema(hidden = true)
  private Long id;


  @Column(length = 100, unique = true)
  @Size(max = 100)
  @Schema(description = "Serial number of the drone", example = "Abc123")
  private String serialNumber;

  @Enumerated(EnumType.STRING)
  @Schema(
      description = "Model of the drone Like LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, HEAVYWEIGHT",
      example = "LIGHTWEIGHT")
  private Model model;

  @Max(500) // Weight limit constraint (max 500g)
  @Schema(description = "Weight limit of the drone in grams", example = "500")
  private int weightLimit;

  @Min(0)
  @Max(100)
  @Schema(description = "Battery capacity of the drone", example = "100")
  private int batteryCapacity;

  @Enumerated(EnumType.STRING)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Schema(description = "State of the drone Like IDLE, LOADING, DELIVERING, RETURNING",
      example = "IDLE")
  private DroneState state;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "drone", orphanRemoval = true)
  @JsonManagedReference
  @Schema(description = "List of payloads the drone is carrying")
  private List<PayloadBase> payloads = new ArrayList<>();


}
