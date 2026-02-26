package api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitRequest {
    private String name;
    private String description;
    @JsonProperty("goal_days")
    private Integer goalDays;

    public HabitRequest() {}

    public HabitRequest(String name) {
        this.name = name;
    }

    public HabitRequest(String name, String description, Integer goalDays) {
        this.name = name;
        this.description = description;
        this.goalDays = goalDays;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getGoalDays() { return goalDays; }
}
