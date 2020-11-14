package com.football.unitedapp.team;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    int playerId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String playerName;
}
