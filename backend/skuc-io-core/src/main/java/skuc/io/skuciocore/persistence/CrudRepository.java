package skuc.io.skuciocore.persistence;

import java.util.Collection;

import net.ravendb.client.documents.session.IDocumentSession;
import skuc.io.skuciocore.models.csm.BaseCsm;

public abstract class CrudRepository<T extends BaseCsm> {

  protected Class<T> concreteClass;

  public CrudRepository(Class<T> concreClass) {
    this.concreteClass = concreClass;
  }

  public Collection<T> get() {
    try (var session = getSession()) {
      return session.query(concreteClass).toList();
    }
  }

  public T get(String id) {
    try (var session = getSession()) {
      return session.load(concreteClass, id);
    }
  }

  public void store(T entity) {
    try (var session = getSession()) {
      session.store(entity, entity.getId());
      session.saveChanges();
    }
  }

  public abstract void update(T entity);

  public void delete(String id) {
    try (var session = getSession()) {
      session.delete(id);
      session.saveChanges();
    }
  }

  protected IDocumentSession getSession() {
    return DocumentStoreHolder.getStore().openSession();
  }
}
