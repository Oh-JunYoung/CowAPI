package toyspringboot.server.Domain.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import toyspringboot.server.Domain.Dto.NoticeDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeListResponseDto {
    private List<NoticeDto> noticeDtoList;
}
