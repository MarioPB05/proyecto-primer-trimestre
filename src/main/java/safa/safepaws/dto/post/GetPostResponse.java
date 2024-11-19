package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
    private Integer id;
    private String name;
    private String photo;
    private String description;
    private Integer statusId;
    private Integer typeId;
    private Boolean deleted;
    private String addressVillage;
}
