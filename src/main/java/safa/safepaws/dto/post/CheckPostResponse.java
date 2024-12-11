package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckPostResponse {
    private boolean available;
    private String requestCode;
}
