package com.gmail.spanteleyko.service.converter;

import com.gmail.spanteleyko.repository.model.Shop;
import com.gmail.spanteleyko.service.model.ShopDTO;

public interface ShopConverter {
    Shop convert(ShopDTO shopDTO);

    ShopDTO convertToDTO(Shop createdShop);
}
