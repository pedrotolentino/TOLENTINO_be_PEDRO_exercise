package com.ecore.roles.model.dto;

import com.ecore.roles.model.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class TeamDto {

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID teamLeadId;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UUID> teamMemberIds;
}
