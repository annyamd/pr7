package com.company.server.db;

import com.company.server.exceptions.NoAccessException;
import com.company.server.model.MusicBand;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MusicBandsCRUD {
    private static final String MB_COLUMNS = "id, coordinates_x, coordinates_y, creationDate, genre, numberOfParticipants, studio_address, studio_name";

    //Main methods to work with Music Bands
    // TODO: 17.06.2021 finally block

    public static void add(MusicBand mb, String login) throws NoAccessException {
        Session session = openSession();
        MusicBandEntity musicBandEntity = getNewMBEntity(mb, login);
        session.persist(musicBandEntity);
        session.flush();
        musicBandEntity.getMusicBand().setId(musicBandEntity.getId());
        session.merge(musicBandEntity);
        session.save(musicBandEntity);
        closeSession(session);
    }

    public static void delete(long id, String login) throws NoAccessException { //need exception to throw if isn't allowed
        if (isAllowedForChange(id, login)) {
            Session session = openSession();
            session.delete(getById(id));
            closeSession(session);
        } else {
            throw new NoAccessException();
        }
    }

    public static List<MusicBand> getAll() {
        Session session = openSession();
        List<MusicBandEntity> musicBandEnts = session.createQuery("FROM MusicBandEntity")
                .list();
        closeSession(session);
        List<MusicBand> musicBands = new ArrayList<>();
        musicBandEnts.forEach(p -> musicBands.add(p.getMusicBand()));
        return musicBands;
    }

    public static void update(MusicBand musicBand, long id, String login) throws NoAccessException {
        if (isAllowedForChange(id, login)) {
            Session session = openSession();
            MusicBandEntity mbs = getById(id);
            if (mbs != null) {
                Query query = session.createQuery("UPDATE MusicBandEntity SET name =: name, numberOfParticipants =: num, " +
                        " creationDate =: date, coordinates_x =: coord_x, coordinates_y =: coord_y, genre =: genre, studio_name =: studio_name, " +
                        "studio_address =: studio_address WHERE id =: id");
                query.setParameter("name", musicBand.getName())
                        .setParameter("num", musicBand.getNumberOfParticipants())
                        .setParameter("date", musicBand.getCreationDate())
                        .setParameter("coord_x", musicBand.getCoordinates().getX())
                        .setParameter("coord_y", musicBand.getCoordinates().getY())
                        .setParameter("genre", musicBand.getGenre().name())
                        .setParameter("studio_name", musicBand.getStudio().getName())
                        .setParameter("studio_address", musicBand.getStudio().getAddress())
                        .setParameter("id", id)
                        .executeUpdate();
            }
            closeSession(session);
        } else {
            throw new NoAccessException();
        }
    }

    public static void deleteAll(String login){
        Session session = openSession();
        Query query = session.createQuery("DELETE MusicBandEntity WHERE login =: login");
        query.setParameter("login", login)
                .executeUpdate();
        closeSession(session);
    }

    //User Auth

    public static boolean signInUser(String login, String password) {
        User user = getUser(login);
        return user != null && user.getPassword().equals(DigestUtils.sha256Hex(password));
    }

    public static boolean signUpUser(String login, String password) {
        User user = getUser(login);
        Session session = openSession();
        if (user == null) {
            session.save(new User(login, DigestUtils.sha256Hex(password)));
            closeSession(session);
            return true;
        }
        closeSession(session);
        return false;
    }

    //Private Methods

    private static MusicBandEntity getNewMBEntity(MusicBand musicBand, String login) throws NoAccessException {
        User user = getUser(login);
        System.out.println(user.getLogin() + "    " + user.getPassword());
        if (user == null) throw new NoAccessException();
        MusicBandEntity musicBandEntity = new MusicBandEntity(musicBand, user);
        System.out.println(musicBandEntity);
        return musicBandEntity;
    }

    private static boolean isAllowedForChange(long id, String login) {
        Session session = openSession();
        List resultList = session
                .createQuery("SELECT creator.login FROM MusicBandEntity WHERE id =: update_id")
                .setParameter("update_id", id)
                .getResultList();
        closeSession(session);
        System.out.println("-----------" + id + "------------" + login);
        return !resultList.isEmpty() && login.equals(resultList.get(0));
    }

    private static User getUser(String login) {
        Session session = openSession();
        List users = session
                .createQuery("FROM User WHERE login =: u_login")
                .setParameter("u_login", login)
                .getResultList();
        closeSession(session);

        if (users.isEmpty()) return null;
        return ((List<User>) users).get(0);
    }

    private static MusicBandEntity getById(long id) {
        Session session = openSession();
        List mbs = session
                .createQuery("FROM MusicBandEntity WHERE id =: update_id")
                .setParameter("update_id", id)
                .getResultList();
        closeSession(session);
        if (mbs.isEmpty()) return null;
        return ((List<MusicBandEntity>) mbs).get(0);
    }

    private static Session openSession() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    private static void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
