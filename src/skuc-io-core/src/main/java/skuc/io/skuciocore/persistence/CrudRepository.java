package skuc.io.skuciocore.persistence;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

import net.ravendb.client.documents.session.IDocumentSession;
import skuc.io.skuciocore.models.csm.BaseCsm;

public class CrudRepository<T extends BaseCsm> implements Closeable {

  private IDocumentSession session;
  protected Class<T> concreteClass;

  public CrudRepository(Class<T> concreClass) {
    this.concreteClass = concreClass;
  }

  public Collection<T> get() {
    return getSession().query(concreteClass).toList();
  }

  public T get(String id) {
    return getSession().load(concreteClass, id);
  }

  public void store(T entity) {
    getSession().store(entity, entity.getId());
    save();
  }

  public void delete(String id) {
    getSession().delete(id);
    save();
  }

  public void save() {
    getSession().saveChanges();
  }

  protected IDocumentSession getSession() {
    if (session == null) {
      session = DocumentStoreHolder.getStore().openSession();
    }

    return session;
  }

  @Override
  public void close() throws IOException {
    if (session != null) {
      session.close();
      session = null;
    }
  }
}
