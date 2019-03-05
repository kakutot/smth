package com.example.restaurantsapp.data.mapper;

import java.util.List;
import java.util.Map;

public interface EntityMapper<From, To> {

    To map(From from, Map<Class<?>, Object> params);

    List<To> mapMany(List<From> froms, List<Map<Class<?>, Object>> params);

    From mapBack(To entity);

    List<From> mapBackMany(List<To> tos);
}