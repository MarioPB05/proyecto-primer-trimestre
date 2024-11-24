package safa.safepaws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum AnimalType {
    PERRO (0),
    GATO (1),
    CONEJO(2),
    HAMSTER(3),
    TORTUGA(4),
    PECES(5),
    PAJARO(6),
    COBAYA(7),
    HURON(8),
    POLLITO(9),
    CABALLO(10),
    CHINCHILLA(11),
    RATA(12),
    SERPIENTE(13),
    LAGARTO(14),
    ERIZO(15),
    PATO(16),
    CAMALEON(17),
    IGUANA(18),
    OVEJA(19);

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
