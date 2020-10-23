package com.apress.prospring5.ch8.entities;

import com.apress.prospring5.ch8.view.SingerDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "singer")
@NamedQueries({
        @NamedQuery(name = Singer.FIND_ALL, query = "select s from Singer s"),
        @NamedQuery(name = Singer.FIND_SINGER_BY_ID,
                query = "select distinct s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i " +
                        "where s.id = :id"),
        @NamedQuery(name = Singer.FIND_ALL_WITH_ALBUM,
                query = "select distinct s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i")
})
@SqlResultSetMapping(
        name = "singerResult",
        entities = @EntityResult(entityClass = Singer.class,
                fields = {
                        @FieldResult(name = "id", column = "uid"),
                        @FieldResult(name = "firstName", column = "nom"),
                        @FieldResult(name = "birthDate", column = "datenaiss"),
                        @FieldResult(name = "lastName", column = "prenom"),
                        @FieldResult(name = "version", column = "version")})
)
@SqlResultSetMapping(
        name="SingerDetailsResult",
        classes={
                @ConstructorResult(
                        targetClass= SingerDetails.class,
                        columns={
                                @ColumnResult(name="id"),
                                @ColumnResult(name="name"),
                                @ColumnResult(name="nbrAlbum"),
                                @ColumnResult(name="nbrInstru")
                        }
                )
        }
)
public class Singer implements Serializable {
    public static final String FIND_ALL = "Singer.findAll";
    public static final String FIND_SINGER_BY_ID = "Singer.findById";
    public static final String FIND_ALL_WITH_ALBUM = "Singer.findAllWithAlbum";

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Version
    @Column(name = "VERSION")
    private int version;

    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name = "SINGER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID"))
    private Set<Instrument> instruments = new HashSet<>();

    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void removeAlbum(Album album) {
        getAlbums().remove(album);
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<com.apress.prospring5.ch8.entities.Instrument> instruments) {
        this.instruments = instruments;
    }

    public boolean addInstrument(Instrument instruments) {
        return getInstruments().add(instruments);
    }

    public String toString() {
        return "Singer - Id: " + id + ", First name: " + firstName
                + ", Last name: " + lastName + ", Birthday: " + birthDate;
    }


}
