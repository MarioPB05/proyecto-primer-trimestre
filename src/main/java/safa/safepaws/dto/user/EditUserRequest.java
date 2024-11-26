package safa.safepaws.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.safepaws.dto.client.EditClientRequest;
import safa.safepaws.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {

    private String username;
    private String email;
    private EditClientRequest client;

}
