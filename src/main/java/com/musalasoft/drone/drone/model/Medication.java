package com.musalasoft.drone.drone.model;

import com.musalasoft.drone.drone.enumerations.PayloadType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "MEDICATION")
public class Medication extends PayloadBase {

  Medication() {
    setPayloadType(PayloadType.MEDICATION);

  }

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
      message = "Name can only contain letters, numbers, '-', and '_'")
  @Schema(description = "Name of the payload",
      example = "fh5Ee8PxpEqOu9tVJaF5KcGh-s0hwmKPxLY7tLrTb5KX9hVj83E1dPWJ0axQS5q8DwaEp6gZI2Mm4pKnWU4")
  private String name;


  @NotBlank
  @Pattern(regexp = "^[A-Z0-9_]+$",
      message = "Code can only contain uppercase letters, numbers, and '_'")
  @Schema(description = "Unique code of the payload",
      example = "XR1036YZMYW1ZMWUQ55V4V_EP5LUQJQA5U7")
  private String code;

  @Schema(description = "Image URL or base64 representation of the payload", example = "string")
  private String image;


  @Override
  public PayloadType getPayloadType() {
    return PayloadType.MEDICATION;
  }


}
