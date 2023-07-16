package ru.job4j.url.shortcut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String password;

    private String domain;

    @OneToMany(mappedBy = "website", cascade = CascadeType.ALL)
    private Set<Page> pages = new HashSet<>();

    public void addPage(Page page) {
        page.setWebsite(this);
        pages.add(page);
    }
}
