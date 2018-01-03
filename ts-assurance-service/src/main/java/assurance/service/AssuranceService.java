package assurance.service;

import assurance.domain.*;

import java.util.List;
import java.util.UUID;

public interface AssuranceService {

//    Assurance createAssurance(Assurance assurance);

    Assurance findAssuranceById(UUID id);

    Assurance findAssuranceByOrderId(UUID orderId);

    AddAssuranceResult create(AddAssuranceInfo aai);

    DeleteAssuranceResult deleteById(UUID assuranceId);

    DeleteAssuranceResult deleteByOrderId(UUID orderId);

    ModifyAssuranceResult modify(ModifyAssuranceInfo info);

    GetAllAssuranceResult getAllAssurances();

    List<AssuranceTypeBean> getAllAssuranceTypes();
}
