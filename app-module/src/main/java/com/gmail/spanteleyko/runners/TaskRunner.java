package com.gmail.spanteleyko.runners;

import com.gmail.spanteleyko.service.*;
import com.gmail.spanteleyko.service.model.ItemDTO;
import com.gmail.spanteleyko.service.model.ShopDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class TaskRunner implements ApplicationRunner {

    private final ShopService shopService;
    private final ItemService itemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ItemDTO> items = itemService.generateItems();
        ShopDTO shop = shopService.create("New shop", "London");
        shopService.add(shop, items);

        shopService.clear();
    }
}
