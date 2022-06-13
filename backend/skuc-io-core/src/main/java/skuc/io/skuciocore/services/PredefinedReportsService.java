package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.reports.PredefinedReport;
import skuc.io.skuciocore.persistence.PredefinedReportsRepository;

@Service
public class PredefinedReportsService extends CrudService<PredefinedReport> {
  
  private final LocationService _locationService;
  private final PredefinedReportsRepository _predefinedReportsRepository;

  @Autowired
  public PredefinedReportsService(LocationService locationService, PredefinedReportsRepository predefinedReportsRepository) {
    super(predefinedReportsRepository);
    _locationService = locationService;
    _predefinedReportsRepository = predefinedReportsRepository;
  }

  public Collection<PredefinedReport> getByGroup(String groupId) {
    return _predefinedReportsRepository.getByGroup(groupId);
  }

  @Override
  public PredefinedReport create(PredefinedReport predefinedReport) {
    _locationService.getOrThrow(predefinedReport.getLocationId());

    var predefinedReportdeviceWithSameName = _predefinedReportsRepository.getByGroupAndName(predefinedReport.getGroupId(), predefinedReport.getName());
    if (predefinedReportdeviceWithSameName != null) {
      throw new BadLogicException("Predefined report with the same name already exists in the group.");
    }

    return super.create(predefinedReport);
  }

  @Override
  public PredefinedReport update(PredefinedReport predefinedReport) {
    var existingPredefinedReport = getOrThrow(predefinedReport.getId());

    var predefinedReportdeviceWithSameName = _predefinedReportsRepository.getByGroupAndName(predefinedReport.getGroupId(), predefinedReport.getName());
    if (predefinedReportdeviceWithSameName != null && !predefinedReportdeviceWithSameName.getId().equals(existingPredefinedReport.getId())) {
      throw new BadLogicException("Predefined report with the same name already exists in the group.");
    }

    return super.update(predefinedReport);
  }


}
