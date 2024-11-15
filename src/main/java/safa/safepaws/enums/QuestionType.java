package safa.safepaws.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QuestionType {
    INPUT (1),
    TEXTAREA (2),
    CHECKBOX (3);
    private final int id;

    @JsonValue
    public int getId(){
        return id;
    }

    @JsonCreator
    public static QuestionType from(int id){
        for (QuestionType type : QuestionType.values()){
            if (type.getId() == id){
                return type;
            }
        }
        return null;
    }
}
