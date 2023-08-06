package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.shortcut.model.Page;

public interface PageRepository extends CrudRepository<Page, Integer> {
    Page save(Page page);

    @Modifying
    @Query(value = "update page set count = :count where id = :id", nativeQuery = true)
    void incrementCount(int count, int id);

    Page findByCode(String code);
}
