package skuc.io.skuciocore.persistence;

import java.util.Collection;
import java.util.UUID;

import net.ravendb.client.documents.session.IDocumentSession;
import skuc.io.skuciocore.models.csm.BaseCsm;

public class CrudRepository<T extends BaseCsm> {

  private IDocumentSession session;
  private Class<T> concreteClass;

  public CrudRepository(Class<T> concreClass) {
    this.concreteClass = concreClass;
  }

  public Collection<T> get() {
    return session.query(concreteClass).toList();
  }

  public T get(UUID id) {
    return session.load(concreteClass, id.toString());
  }

  public void save(T entity) {
    session.store(entity);
    session.saveChanges();
  }

  public void delete(T entity) {
    session.delete(entity.getId());
    session.saveChanges();
  }

  public void openSession() {
    if (session == null) {
      session = DocumentStoreHolder.getStore().openSession();
    }
  }

  public void closeSession() {
    session.close();
    session = null;
  }

}
