package safa.safepaws.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import safa.safepaws.dto.user.GetUserResponse;
import safa.safepaws.model.User;

@Mapper
public interface UserMapper {


    GetUserResponse toDTO(User user);

}
