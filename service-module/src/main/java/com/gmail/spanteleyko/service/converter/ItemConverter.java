package com.gmail.spanteleyko.service.converter;

import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.service.model.ItemDTO;

public interface ItemConverter {
    Item convert(ItemDTO itemDTO);

    ItemDTO convertToDTO(Item createdItem);
}
