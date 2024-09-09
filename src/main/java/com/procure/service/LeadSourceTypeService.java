package com.procure.service;

import com.procure.domain.LeadSourceType;
import com.procure.repository.LeadSourceTypeRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LeadSourceTypeService {

    private final LeadSourceTypeRepository leadSourceTypeRepository;

    public LeadSourceTypeService(LeadSourceTypeRepository leadSourceTypeRepository) {
        this.leadSourceTypeRepository = leadSourceTypeRepository;
    }

    public void save(LeadSourceType leadSourceType) {
        LocalDate localDate = LocalDate.now();
        leadSourceType.setCreatedDate(localDate);
        leadSourceTypeRepository.save(leadSourceType);
    }

    public List<LeadSourceType> fetchLeadSourceTypeRecords() {
        return leadSourceTypeRepository.findAll();
    }

    public void delete(Long id) {
        leadSourceTypeRepository.deleteById(id);
    }
}
