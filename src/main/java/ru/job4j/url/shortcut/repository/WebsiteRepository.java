package ru.job4j.url.shortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.url.shortcut.model.Page;
import ru.job4j.url.shortcut.model.Website;

import java.util.Collection;
import java.util.Optional;

public interface WebsiteRepository extends CrudRepository<Website, Integer> {
    Optional<Website> findByLogin(String login);

    @Query("from Website w left join fetch w.pages where w.domain = :domain")
    Optional<Website> findByDomainWithPages(String domain);

    Optional<Website> findByDomain(String domain);

    @Query("select pages from Website where login = :login")
    Collection<Page> findByWebsiteLogin(String login);
}
