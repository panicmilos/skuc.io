package skuc.io.skuciocore.persistence;

import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;

public class DocumentStoreHolder {

  private static class DocumentStoreContainer {
    public static final IDocumentStore store = new DocumentStore("http://bjelicaluka.com:8888", "obezbedio_db");

    static {
      store.initialize();
    }
  }

  public static IDocumentStore getStore() {
    return DocumentStoreContainer.store;
  }
}
