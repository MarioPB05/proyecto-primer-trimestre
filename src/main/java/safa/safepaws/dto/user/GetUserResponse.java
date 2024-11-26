package safa.safepaws.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.dto.client.GetClientResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private String username;
    private String email;
    private GetClientResponse client;
}
