package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.shortcut.model.Page;

import javax.persistence.LockModeType;

public interface PageRepository extends CrudRepository<Page, Integer> {
    Page save(Page page);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Page findByCode(String code);
}
