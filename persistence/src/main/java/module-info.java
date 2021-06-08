module persistence {
    requires jakarta.persistence;

    opens persistpkg to com.google.gson, jakarta.persistence, eclipselink;

    exports persistpkg;
}