package com.procure.service;

import com.procure.domain.ProducerDetails;
import com.procure.repository.ProducerDetailsRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class ProducerDetailsService {

    private final ProducerDetailsRepository producerRepository;

    public ProducerDetailsService(ProducerDetailsRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public List<ProducerDetails> fetchRecords() {
        return this.producerRepository.findAll();
    }

    public ProducerDetails save(ProducerDetails producerDetails) {
        log.debug("Request to save ProducerDetails :: {} ", producerDetails);
        return producerRepository.save(producerDetails);
    }

    public void deleteRecord(Long id) {
        this.producerRepository.deleteById(id);
    }
}
