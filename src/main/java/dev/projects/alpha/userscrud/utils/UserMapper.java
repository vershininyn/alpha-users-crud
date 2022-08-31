package dev.projects.alpha.userscrud.utils;

import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.repository.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO mapEntityToDto(UserEntity entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .firstname(entity.getFirstname())
                .secondname(entity.getSecondname())
                .thirdname(entity.getThirdname())
                .isBanned(entity.getIsBanned())
                .build();
    }

    public static List<UserDTO> mapEntityListToDtoList(List<UserEntity> list){
        return list.stream()
                .map(UserMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public static UserEntity mapDtoToEntity(UserRequestDTO dto) {
        return UserEntity.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .firstname(dto.getFirstname())
                .secondname(dto.getSecondname())
                .thirdname(dto.getThirdname())
                .isBanned(dto.isBanned())
                .build();
    }
    
    public static UserDTO mapRequestDtoToUserDto(Integer id, UserRequestDTO dto) {
        return UserDTO.builder()
                .id(id)
                .login(dto.getLogin())
                .password(dto.getPassword())
                .firstname(dto.getFirstname())
                .secondname(dto.getSecondname())
                .thirdname(dto.getThirdname())
                .isBanned(false)
                .build();
    }
}
