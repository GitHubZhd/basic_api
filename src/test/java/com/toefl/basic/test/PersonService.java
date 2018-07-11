package com.toefl.basic.test;

/**
 * @author hai
 * @description
 * @date 16:52 2018/6/28
 */
public class PersonService {
    private final PersonDao personDao;
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }
    public boolean update(int id, String name) {
        Person person = personDao.getPerson(id);
        if (person == null)
        { return false; }
        Person personUpdate = new Person(person.getId(), name);
        return personDao.update(personUpdate);
    }
}
