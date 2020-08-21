package me.kakao.pay.luck.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import me.kakao.pay.common.domain.Blessing;
import me.kakao.pay.luck.vo.BlessRequest;

@Mapper(componentModel = "spring")
public interface LuckObjectMapper {
	@Mappings({ 
		@Mapping(target = "blesserId", source = "userId", defaultValue = ""), 
		@Mapping(target = "roomId", source = "roomId", defaultValue = "") 
	})
	Blessing requestToBlessing(BlessRequest request, String userId, String roomId);

}
