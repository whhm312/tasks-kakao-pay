package me.kakao.pay.luck.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import me.kakao.pay.common.domain.Luck;
import me.kakao.pay.luck.vo.BlessLuckRequest;
import me.kakao.pay.luck.vo.GrabLuckRequest;

@Mapper(componentModel = "spring")
public interface LuckObjectMapper {
	@Mappings({ 
		@Mapping(target = "blesserId", source = "userId", defaultValue = ""), 
		@Mapping(target = "roomId", source = "roomId", defaultValue = "") 
	})
	Luck requestToBlessing(BlessLuckRequest request, String userId, String roomId);

	@Mappings({ 
		@Mapping(target = "roomId", source = "roomId", defaultValue = "") 
	})
	Luck requestToGrab(GrabLuckRequest request, String roomId);

}
