package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.reports.PredefinedReport;

@Repository
public class PredefinedReportsRepository extends CrudRepository<PredefinedReport>  {
  
  public PredefinedReportsRepository() {
    super(PredefinedReport.class);
  }

  public Collection<PredefinedReport> getByGroup(String groupId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("groupId", groupId).toList();
    }
  }

  public PredefinedReport getByLocationAndName(String locationId, String name) {
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("locationId", locationId)
        .whereEquals("name", name)
        .firstOrDefault();
    }
  }

  @Override
  public void update(PredefinedReport predefinedReport) {
    try (var session = getSession()) {
      var existingPredefinedReport = session.load(concreteClass, predefinedReport.getId());

      existingPredefinedReport.setName(predefinedReport.getName());
      existingPredefinedReport.setResolution(predefinedReport.getResolution());
      existingPredefinedReport.setParamFilters(predefinedReport.getParamFilters());

      session.saveChanges();
    }
  }

}
