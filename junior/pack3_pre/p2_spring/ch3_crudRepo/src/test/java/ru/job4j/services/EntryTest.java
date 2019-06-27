package ru.job4j.services;

import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Класс EntryTest тестирует класс Entry.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-06-27
 * @since 2018-06-27
 */
public class EntryTest {
    /**
     * Вхождение сущностей.
     */
    private Entry entry;
    /**
     * Итератор по типам сущностей.
     */
    private Iterator<EntityType<?>> iter;
    /**
     * entityManagerFactory.
     */
    private static EntityManagerFactory emf;
    /**
     * Действия перед тестом.
     * @throws Exception исключение.
     */
    @Before
    public void beforeTest() throws Exception {
        EntityManager entityManager = emf.createEntityManager();
        Set<EntityType<?>> eTypes = entityManager.getMetamodel().getEntities();
        this.iter = eTypes.iterator();
        this.entry = new Entry(this.iter.next(), this.iter.next(), "FakeAttrName");
    }
    /**
     * Действия перед всеми тестами.
     * @throws Exception исключение.
     */
    @BeforeClass
    public static void beforeAllTests() throws Exception {
        emf = Persistence.createEntityManagerFactory("persistenceUnitNameHibernate");
    }
    /**
     * Тестирует public String getAttrName().
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetAttrName() throws Exception {
        assertEquals(this.entry.getAttrName(), "FakeAttrName");
    }
    /**
     * Тестирует public EntityType<?> getChild().
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetChild() throws Exception {
        assertNotNull(this.entry.getChild());
    }
    /**
     * Тестирует public EntityType<?> getParent().
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testGetParent() throws Exception {
        assertNotNull(this.entry.getParent());
    }
    /**
     * Тестирует public void setAttrName(String attrName).
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testSetAttrName() throws Exception {
        this.entry.setAttrName("AnotherAttrName");
        assertEquals(this.entry.getAttrName(), "AnotherAttrName");
    }
    /**
     * Тестирует public void setChild(EntityType<?> child).
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testSetChild() throws Exception {
        int hashBefore = this.entry.hashCode();
        this.entry.setChild(this.iter.next());
        int hashAfter = this.entry.hashCode();
        assertNotEquals(hashBefore, hashAfter);
    }
    /**
     * Тестирует public void setParent(EntityType<?> parent).
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testSetParent() throws Exception {
        int hashBefore = this.entry.hashCode();
        this.entry.setParent(this.iter.next());
        int hashAfter = this.entry.hashCode();
        assertNotEquals(hashBefore, hashAfter);
    }
    /**
     * Тестирует public String toString().
     * @throws java.lang.Exception исключение.
     */
    @Test
    public void testToString() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(this.entry.getParent().getJavaType().getName());
        sb.append("{");
        sb.append(this.entry.getChild().getJavaType().getName());
        sb.append(" ");
        sb.append(this.entry.getAttrName());
        sb.append("}");
        assertEquals(sb.toString(), this.entry.toString());
    }
}
