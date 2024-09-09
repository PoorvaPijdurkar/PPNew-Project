package com.procure.service;

import com.procure.domain.Entitlements;
import com.procure.repository.EntitlementsRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class EntitlementsService {

    private final EntitlementsRepository entitlementsRepository;

    private Entitlements entitlements;

    public EntitlementsService(EntitlementsRepository entitlementsRepository) {
        this.entitlementsRepository = entitlementsRepository;
    }

    public List<Entitlements> fetchRecords() {
        return this.entitlementsRepository.findAll();
    }

    public void deleteRecord(Long entitlementId) {
        this.entitlementsRepository.deleteById(entitlementId);
    }

    public Entitlements save(Entitlements entitlements) {
        log.debug("Request to save Department :: {} ", entitlements);
        return entitlementsRepository.save(entitlements);
    }
}
