package safa.safepaws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GetAdoptionsResponse {
    private Integer requestId;
    private Date requestDate;
    private Integer requestStatus;
    private String requestCode;
    private String chatRoomCode;
    private String ownerName;
    private String applierName;
    private String postName;
    private String postPhoto;
    private String addressProvince;
    private Boolean deleted;
}