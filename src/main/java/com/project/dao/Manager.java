package com.project.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.query.Query;

import com.project.domain.*;
import java.util.ArrayList;
import java.util.HashSet;
import org.hibernate.query.NativeQuery;

public class Manager {
    private static SessionFactory factory;

    // Método para crear la SessionFactory
    public static void createSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Biblioteca.class);
            configuration.addAnnotatedClass(Llibre.class);
            configuration.addAnnotatedClass(Exemplar.class);
            configuration.addAnnotatedClass(Prestec.class);
            configuration.addAnnotatedClass(Persona.class);
            configuration.addAnnotatedClass(Autor.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("No s'ha pogut crear la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Crea la SessionFactory con un archivo de propiedades específico
    public static void createSessionFactory(String propertiesFileName) {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Biblioteca.class);
            configuration.addAnnotatedClass(Llibre.class);
            configuration.addAnnotatedClass(Exemplar.class);
            configuration.addAnnotatedClass(Prestec.class);
            configuration.addAnnotatedClass(Persona.class);
            configuration.addAnnotatedClass(Autor.class);

            Properties properties = new Properties();
            try (InputStream input = Manager.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
                if (input == null) {
                    throw new IOException("No s'ha trobat " + propertiesFileName);
                }
                properties.load(input);
            }

            configuration.addProperties(properties);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

            factory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Error creant la SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Cierra la SessionFactory
    public static void close() {
        if (factory != null) {
            factory.close();
        }
    }

    // Método para obtener una biblioteca con sus ejemplares de manera anticipada
    // Método para obtener una biblioteca con sus ejemplares de manera anticipada
public static Biblioteca getBibliotecaConExemplars(long bibliotecaId) {
    Biblioteca biblioteca = null;
    try (Session session = factory.openSession()) {
        Transaction tx = session.beginTransaction();
        try {
            // Usamos JOIN FETCH para cargar los ejemplares junto con la biblioteca
            String hql = "FROM Biblioteca b LEFT JOIN FETCH b.exemplars WHERE b.id = :id";
            Query<Biblioteca> query = session.createQuery(hql, Biblioteca.class);
            query.setParameter("id", bibliotecaId);
            biblioteca = query.uniqueResult();  // Nos aseguramos de que sea solo una biblioteca
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
    return biblioteca;
}


    // Método para agregar un nuevo autor
    public static Autor addAutor(String nom) {
        Autor result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Autor(nom);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para agregar un nuevo libro
    public static Llibre addLlibre(String isbn, String titol, String editorial, int anyPublicacio) {
        Llibre result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Llibre(isbn, titol, editorial, anyPublicacio);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para agregar una nueva biblioteca
    public static Biblioteca addBiblioteca(String nom, String ciutat, String adreca, String telefon, String email) {
        Biblioteca result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Biblioteca(nom, ciutat, adreca, telefon, email);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para agregar un nuevo ejemplar
    public static Exemplar addExemplar(String codiBarres, Llibre llibre, Biblioteca biblioteca) {
        Exemplar result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Exemplar(codiBarres, llibre, biblioteca);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para agregar una nueva persona
    public static Persona addPersona(String dni, String nom, String telefon, String email) {
        Persona result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Persona(dni, nom, telefon, email);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para agregar un nuevo préstamo
    public static Prestec addPrestec(Exemplar exemplar, Persona persona, LocalDate dataPrestec, LocalDate dataRetornPrevista) {
        Prestec result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                result = new Prestec(exemplar, persona, dataPrestec, dataRetornPrevista);
                session.persist(result);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // Método para registrar el retorno de un préstamo
    public static void registrarRetornPrestec(long prestecId, LocalDate dataRetornReal) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Prestec prestec = session.get(Prestec.class, prestecId);
                if (prestec != null) {
                    prestec.setDataRetornReal(dataRetornReal);
                    session.merge(prestec);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }

    // Método para obtener los libros con sus autores
    public static List<Llibre> findLlibresAmbAutors() {
        List<Llibre> result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                String hql = "FROM Llibre l LEFT JOIN FETCH l.autors";
                Query<Llibre> query = session.createQuery(hql, Llibre.class);
                result = query.list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }
    
    // Método para obtener libros y bibliotecas
    public static List<Object[]> findLlibresAmbBiblioteques() {
        List<Object[]> result = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                String hql = "SELECT l, b FROM Llibre l "
                           + "JOIN l.exemplars e "
                           + "JOIN e.biblioteca b";
                Query<Object[]> query = session.createQuery(hql);
                result = query.list();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return result;
    }

    // En Manager.java
public static <T> List<T> listCollection(Class<T> clazz) {
    List<T> result = null;
    try (Session session = factory.openSession()) {
        Transaction tx = session.beginTransaction();
        try {
            String hql = "FROM " + clazz.getName();
            Query<T> query = session.createQuery(hql, clazz);
            result = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
    return result;
}


    // Método para obtener un objeto por su ID
    public static <T> T getById(Class<? extends T> clazz, long id) {
        T obj = null;
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                obj = session.get(clazz, id);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
        return obj;
    }

    // En Manager.java
// Método para convertir una colección a String (asegurando que las colecciones se inicialicen)
public static <T> String collectionToString(Class<T> clazz, Collection<T> collection) {
    StringBuilder sb = new StringBuilder();
    
    // Aseguramos que las colecciones estén inicializadas
    if (collection != null) {
        for (T item : collection) {
            if (item instanceof HibernateProxy) {
                // Inicializamos la colección si es un proxy de Hibernate
                Hibernate.initialize(item);
            }
            sb.append(item.toString()).append("\n");
        }
    }
    return sb.toString();
}


// En Manager.java
public static List<Object[]> findLlibresEnPrestec() {
    List<Object[]> result = null;
    try (Session session = factory.openSession()) {
        Transaction tx = session.beginTransaction();
        try {
            String hql = "SELECT l, p FROM Prestec pr " +
                         "JOIN pr.exemplar e " +
                         "JOIN e.llibre l " +
                         "JOIN pr.persona p " +
                         "WHERE pr.dataRetornReal IS NULL";
            Query<Object[]> query = session.createQuery(hql);
            result = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
    return result;
}

// En Manager.java
public static String formatMultipleResult(List<Object[]> result) {
    StringBuilder sb = new StringBuilder();
    for (Object[] row : result) {
        for (Object obj : row) {
            sb.append(obj).append(" | ");
        }
        sb.append("\n");
    }
    return sb.toString();
}

// En Manager.java
public static void updateAutor(Long autorId, String autorNom, Set<Llibre> llibres) {
    try (Session session = factory.openSession()) {
        Transaction tx = session.beginTransaction();
        try {
            // Buscamos al autor por su ID
            Autor autor = session.get(Autor.class, autorId);
            
            if (autor != null) {
                // Actualizamos el nombre del autor
                autor.setNom(autorNom);

                // Asignamos la nueva colección de libros al autor
                autor.setLlibres(llibres);

                // Guardamos los cambios en la base de datos
                session.update(autor);
                tx.commit();
                System.out.println("Autor actualizado correctamente: " + autor.getNom());
            } else {
                System.out.println("No s'ha trobat l'autor amb ID: " + autorId);
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}



    // Método para eliminar una entidad por su ID
    public static <T> void delete(Class<? extends T> clazz, Serializable id) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                T obj = session.get(clazz, id);
                if (obj != null) {
                    session.remove(obj);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }
}
