package com.procure.service;

import com.procure.domain.Bom;
import com.procure.domain.BomSkus;
import com.procure.domain.Sku;
import com.procure.repository.BomRepository;
import com.procure.repository.BomSkusRepository;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BomService {

    private final BomRepository bomRepository;

    private final BomSkusRepository bomSkusRepository;

    public BomService(BomRepository bomRepository, BomSkusRepository bomSkusRepository) {
        this.bomRepository = bomRepository;
        this.bomSkusRepository = bomSkusRepository;
    }

    public List<Bom> fetchAllRecords() {
        return bomRepository.findAll();
    }

    public Bom save(Bom bom) {
        String bomCode = this.getBomCode();
        bom.setProductCode(bomCode);

        Bom savedBom = bomRepository.save(bom);

        Set<Sku> skuSet = bom.getSkus();
        List<BomSkus> bomSkuses = new ArrayList<>();

        bomSkusRepository.deleteByBomId(savedBom.getId());

        for (Sku sku : skuSet) {
            BomSkus bomSkus = new BomSkus();
            bomSkus.setBomId(savedBom.getId());
            bomSkus.setSkuId(sku.getId());
            bomSkus.setQuantity(sku.getQuantity());
            bomSkuses.add(bomSkus);
        }

        bomSkusRepository.saveAll(bomSkuses);

        return savedBom;
    }

    private String getBomCode() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        return ft.format(dNow);
    }

    public void delete(Long id) {
        bomRepository.deleteById(id);
    }

    public Bom update(Bom bom) {
        Bom existingBom = bomRepository.findById(bom.getId()).orElseThrow(() -> new EntityNotFoundException("Bom not found"));
        existingBom.setBomLevel(bom.getBomLevel());
        existingBom.setQuantity(bom.getQuantity());
        existingBom.setProductName(bom.getProductName());
        existingBom.setUnitOfMeasure(bom.getUnitOfMeasure());
        existingBom.setProductType(bom.getProductType());

        return bomRepository.save(existingBom);
    }

    public List<Map<String, String>> getData(long bomId) {
        return bomRepository.getDetails(bomId);
    }
}
