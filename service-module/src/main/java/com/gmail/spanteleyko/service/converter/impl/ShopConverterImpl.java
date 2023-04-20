package com.gmail.spanteleyko.service.converter.impl;

import com.gmail.spanteleyko.repository.model.Shop;
import com.gmail.spanteleyko.service.converter.ShopConverter;
import com.gmail.spanteleyko.service.model.ShopDTO;
import org.springframework.stereotype.Service;

@Service
public class ShopConverterImpl implements ShopConverter {
    @Override
    public Shop convert(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setId(shopDTO.getId());
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }

    @Override
    public ShopDTO convertToDTO(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setLocation(shop.getLocation());
        return shopDTO;
    }
}
