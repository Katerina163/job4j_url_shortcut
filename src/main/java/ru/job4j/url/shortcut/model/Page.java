package ru.job4j.url.shortcut.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @EqualsAndHashCode.Include
    private String path;

    @EqualsAndHashCode.Include
    private String code;

    private Integer count = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "website_id")
    private Website website;
}
