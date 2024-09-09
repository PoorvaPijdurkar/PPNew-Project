package com.procure.service;

import com.procure.domain.State;
import com.procure.repository.StateRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> fetchAllStateRecords() {
        return stateRepository.findAll();
    }

    public void save(State state) {
        stateRepository.save(state);
    }

    public void deleteByStateId(Long id) {
        stateRepository.deleteById(id);
    }

    public List<State> fetchStateRecordsByCountryCode(Long countryCode) {
        Optional<List<State>> stateList = stateRepository.findByCountryCode(countryCode);
        if (!stateList.isEmpty()) {
            return stateList.get();
        }
        return new ArrayList<>();
    }
}
