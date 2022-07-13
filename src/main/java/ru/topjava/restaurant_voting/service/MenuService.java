package ru.topjava.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;

import javax.validation.Valid;

@Service
@AllArgsConstructor
@Validated
public class MenuService {
    MenuRepository menuRepository;

    public Menu validateAndCreate(@Valid Menu menu) {
        if(!menu.isNew()) menu.setId(null);
        return menuRepository.save(menu);
    }

    public Menu validateAndUpdate(@Valid Menu menu) {
        return menuRepository.save(menu);
    }
}
