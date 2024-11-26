package safa.safepaws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RequestStatus {
    PENDING(0),
    REJECTED(1),
    ACCEPTED(2);
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
