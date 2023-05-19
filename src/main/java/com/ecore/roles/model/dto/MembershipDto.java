package com.ecore.roles.model.dto;

import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MembershipDto {

    @JsonProperty
    private UUID id;

    @JsonProperty
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private UUID roleId;

    @JsonProperty(value = "teamMemberId")
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private UUID userId;

    @JsonProperty
    @Valid
    @NotNull
    @EqualsAndHashCode.Include
    private UUID teamId;

}
