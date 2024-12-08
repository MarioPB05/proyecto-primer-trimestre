package safa.safepaws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestStatus {
    PENDING(0),
    REJECTED(1),
    ACCEPTED(2),
    ADOPTED(3), // Estado que se le asigna a la solicitud cuando el post ha sido adoptado por otra request
    PENDING_SIGNATURE(4), // Esperando que el adoptante firme el contrato
    FINISHED(5); // Estado que se le asigna a la solicitud cuando el adoptante firma el contrato
    private final int id;

    @JsonValue
    public int getId(){return id;}

    @JsonCreator
    public static RequestStatus from(int id){
        for (RequestStatus type: RequestStatus.values()){
            if (type.getId() == id){
                return type;
            }
        }
        return null;
    }
}
