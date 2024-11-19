package safa.safepaws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum AnimalType {
    PERRO (0),
    GATO (1);
    private final int id;

    @JsonValue
    public int getId(){
        return id;
    }

    @JsonCreator
    public static AnimalType fromId(int id){
        for (AnimalType type : AnimalType.values()){
            if (type.getId() == id){
                return type;
            }
        }
        return null;
    }
}
