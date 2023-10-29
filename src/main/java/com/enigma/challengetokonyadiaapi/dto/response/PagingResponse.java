package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PagingResponse <T>{
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
    private T data;
}
