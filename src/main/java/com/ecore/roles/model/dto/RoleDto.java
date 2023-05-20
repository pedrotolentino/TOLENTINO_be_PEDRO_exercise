package com.ecore.roles.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RoleDto {

    @JsonProperty
    private UUID id;

    @JsonProperty
    @NotBlank
    private String name;
}
