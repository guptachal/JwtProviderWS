package com.microservice.loginWS.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommonMapper {
    private final ModelMapper mapper;
    <T,S> S convertToEntity(T data, Class<S> type){return mapper.map(data,type);}
    <T,S> S covertToResponse(T data, Class<S> type){return mapper.map(data,type);}
    <T,S> S entityToEntity(T data, Class<S> type){return mapper.map(data,type);}
    <T,S>List<S> covertToResponseList(List<T> lists, Class<S> type){
        return lists.stream()
            .map(list->covertToResponse(list,type))
                .collect(Collectors.toList());}
}
