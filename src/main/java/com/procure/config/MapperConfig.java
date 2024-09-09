package com.procure.config;

import com.procure.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public SkuMapper skuMapper() {
        return Mappers.getMapper(SkuMapper.class);
    }

    @Bean
    public ProducerDetailsMapper producerDetailsMapper() {
        return Mappers.getMapper(ProducerDetailsMapper.class);
    }

    @Bean
    public OrdersMapper ordersMapper() {
        return Mappers.getMapper(OrdersMapper.class);
    }

    @Bean
    public SupplierDetailsMapper supplierDetailsMapper() {
        return Mappers.getMapper(SupplierDetailsMapper.class);
    }

    @Bean
    public DepartmentMapper departmentMapper() {
        return Mappers.getMapper(DepartmentMapper.class);
    }

    @Bean
    public EntitlementsMapper entitlementsMapper() {
        return Mappers.getMapper(EntitlementsMapper.class);
    }

    @Bean
    public OrderAssignmentMapper orderAssignmentMapper() {
        return Mappers.getMapper(OrderAssignmentMapper.class);
    }

    @Bean
    public SupplierPurchaseMapper supplierPurchaseMapper() {
        return Mappers.getMapper(SupplierPurchaseMapper.class);
    }

    @Bean
    public OrdersSummaryMapper ordersSummaryMapper() {
        return Mappers.getMapper(OrdersSummaryMapper.class);
    }

    @Bean
    public BomMapper bomMapper() {
        return Mappers.getMapper(BomMapper.class);
    }

    @Bean
    public BatchMapper batchMapper() {
        return Mappers.getMapper(BatchMapper.class);
    }

    @Bean
    public CustomerDetailsMapper customerDetailsMapper() {
        return Mappers.getMapper(CustomerDetailsMapper.class);
    }

    @Bean
    public ProductionMapper productionMapper() {
        return Mappers.getMapper(ProductionMapper.class);
    }

    @Bean
    public CityMapper cityMapper() {
        return Mappers.getMapper(CityMapper.class);
    }

    @Bean
    public StateMapper stateMapper() {
        return Mappers.getMapper(StateMapper.class);
    }

    @Bean
    public CountryMapper countryMapper() {
        return Mappers.getMapper(CountryMapper.class);
    }

    @Bean
    public LeadSourceTypeMapper leadSourceTypeMapper() {
        return Mappers.getMapper(LeadSourceTypeMapper.class);
    }

    @Bean
    public SalesOrderMapper salesOrderMapper() {
        return Mappers.getMapper(SalesOrderMapper.class);
    }
}
