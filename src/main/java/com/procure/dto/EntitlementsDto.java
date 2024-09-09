package com.procure.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntitlementsDto {

    public Long id;
    public String roleId;
    public String resourceId;
    public String resourceName;
    public String actionId;
    public String actionName;
}
