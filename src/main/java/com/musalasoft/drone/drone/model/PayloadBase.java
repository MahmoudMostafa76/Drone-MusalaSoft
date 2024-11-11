package com.musalasoft.drone.drone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.musalasoft.drone.drone.enumerations.PayloadType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Medication.class, name = "MEDICATION")})
public abstract class PayloadBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(hidden = true)
  private Long id;

  @Schema(description = "Weight of the payload", example = "60")
  private int weight;

  @ManyToOne
  @JoinColumn(name = "drone_id")
  @JsonBackReference
  @Schema(hidden = true)
  private Drone drone;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Type of the payload Like MEDICATION, CAMERA, FOOD, CLOTHES",
      example = "MEDICATION")
  @JsonProperty("type")
  private PayloadType payloadType;

  public abstract PayloadType getPayloadType();
}
