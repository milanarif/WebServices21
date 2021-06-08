module persistence {
    requires jakarta.persistence;

    opens persistence to core;

    exports persistence;
}