package com.ealam.store.persistence.mapper;

import com.ealam.store.domain.Category;
import com.ealam.store.persistence.entity.Categoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mappings({
            @Mapping(source="idCategoria",target="categoryId"),
            @Mapping(source = "descripcion",target = "category"),
            @Mapping(source = "estado",target = "active")
    })
    Category toCategory(Categoria categoria);

    @InheritInverseConfiguration
    @Mapping(target = "productos",ignore = true)
    Categoria toCategoria(Category category);
}
