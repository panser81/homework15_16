package com.gmail.spanteleyko.service.converter.impl;

import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.service.converter.ItemConverter;
import com.gmail.spanteleyko.service.model.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public class ItemConverterImpl implements ItemConverter {
    @Override
    public Item convert(ItemDTO itemDTO) {
        Item item = new Item();
        item.setDescription(itemDTO.getDescription());
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        return item;
    }

    @Override
    public ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setDescription(item.getDescription());
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}
